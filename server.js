const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = 3000;
const DB_PATH = path.join(__dirname, 'db.json');

app.use(cors());
app.use(bodyParser.json());
app.use(express.static(path.join(__dirname, 'src', 'main', 'app')));

function readDB() {
  const raw = fs.readFileSync(DB_PATH, 'utf-8');
  return JSON.parse(raw);
}

function writeDB(data) {
  fs.writeFileSync(DB_PATH, JSON.stringify(data, null, 2), 'utf-8');
}

function addAuditEntry(action, user, details, severity = 'info') {
  const db = readDB();
  const entry = {
    id: `AUD${String(db.auditLog.length + 1).padStart(3, '0')}`,
    timestamp: new Date().toISOString(),
    action,
    user,
    details,
    severity
  };
  db.auditLog.unshift(entry);
  writeDB(db);
}

app.post('/api/login', (req, res) => {
  const { username, password } = req.body;
  const db = readDB();
  const user = db.users.find(u => u.username === username && u.password === password);

  if (user) {
    addAuditEntry('LOGIN', username, `Successful login from ${req.ip}`, 'info');
    const account = db.accounts.find(a => a.userId === user.id);
    return res.json({
      success: true,
      user: {
        id: user.id,
        username: user.username,
        fullName: user.fullName,
        email: user.email,
        role: user.role,
        accountId: user.accountId
      },
      account
    });
  }

  addAuditEntry('LOGIN_FAILED', username || 'unknown', `Failed login attempt from ${req.ip}`, 'error');
  return res.status(401).json({ success: false, message: 'Invalid username or password' });
});

app.get('/api/transactions', (req, res) => {
  const db = readDB();
  const { accountId, sort, order, type } = req.query;
  let transactions = db.transactions;

  if (accountId) {
    transactions = transactions.filter(t => t.accountId === accountId);
  }

  if (type && type !== 'All') {
    transactions = transactions.filter(t => t.type === type);
  }

  if (sort === 'date') {
    const dir = order === 'asc' ? 1 : -1;
    transactions.sort((a, b) => dir * (new Date(a.date) - new Date(b.date)));
  }

  return res.json({ success: true, transactions });
});

app.get('/api/accounts/:id', (req, res) => {
  const db = readDB();
  const account = db.accounts.find(a => a.id === req.params.id);
  if (account) return res.json({ success: true, account });
  return res.status(404).json({ success: false, message: 'Account not found' });
});

app.post('/api/transfer', (req, res) => {
  const { fromAccountId, recipientAccount, amount, memo, username } = req.body;

  if (!recipientAccount || !amount || amount <= 0) {
    return res.status(400).json({ success: false, message: 'Invalid transfer details' });
  }

  setTimeout(() => {
    const db = readDB();
    const account = db.accounts.find(a => a.id === fromAccountId);

    if (!account) {
      return res.status(404).json({ success: false, message: 'Source account not found' });
    }

    if (account.balance < amount) {
      return res.status(400).json({ success: false, message: 'Insufficient balance' });
    }

    account.balance -= amount;

    const txn = {
      id: `TXN${String(db.transactions.length + 1).padStart(3, '0')}`,
      accountId: fromAccountId,
      date: new Date().toISOString().split('T')[0],
      description: `Transfer to ${recipientAccount}${memo ? ' — ' + memo : ''}`,
      type: 'Transfer',
      amount: parseFloat(amount),
      status: 'Completed'
    };
    db.transactions.unshift(txn);

    writeDB(db);
    addAuditEntry('TRANSFER', username || 'unknown', `Transferred $${amount} to ${recipientAccount}`, 'warning');

    return res.json({ success: true, message: 'Transfer completed successfully', transaction: txn, newBalance: account.balance });
  }, 2000);
});

app.post('/api/loans/apply', (req, res) => {
  const { userId, username, amount, repaymentDate, fileName } = req.body;

  if (!amount || !repaymentDate) {
    return res.status(400).json({ success: false, message: 'Amount and repayment date are required' });
  }

  const db = readDB();
  const loan = {
    id: `LOAN${String(db.loans.length + 1).padStart(3, '0')}`,
    userId,
    amount: parseFloat(amount),
    repaymentDate,
    fileName: fileName || null,
    status: 'Under Review',
    appliedAt: new Date().toISOString()
  };
  db.loans.push(loan);
  writeDB(db);

  addAuditEntry('LOAN_APPLICATION', username || 'unknown', `Submitted loan application for $${amount}`, 'info');

  return res.json({ success: true, message: 'Loan application submitted successfully', loan });
});

app.get('/api/audit', (req, res) => {
  const db = readDB();
  return res.json({ success: true, auditLog: db.auditLog });
});

app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'src', 'main', 'app', 'login.html'));
});

app.listen(PORT, () => {
  console.log(`AuraBank server running at http://localhost:${PORT}`);
});
