const API_BASE = '';

function getSession() {
  const raw = sessionStorage.getItem('aurabank_session');
  return raw ? JSON.parse(raw) : null;
}

function setSession(data) {
  sessionStorage.setItem('aurabank_session', JSON.stringify(data));
}

function clearSession() {
  sessionStorage.removeItem('aurabank_session');
}

function requireAuth() {
  const session = getSession();
  if (!session) {
    window.location.href = '/login.html';
    return null;
  }
  return session;
}

function requireAdmin() {
  const session = requireAuth();
  if (session && session.user.role !== 'admin') {
    window.location.href = '/dashboard.html';
    return null;
  }
  return session;
}

async function apiFetch(endpoint, options = {}) {
  const config = {
    headers: { 'Content-Type': 'application/json' },
    ...options
  };
  const res = await fetch(`${API_BASE}${endpoint}`, config);
  const data = await res.json();
  if (!res.ok) throw { status: res.status, ...data };
  return data;
}

function showToast(message, type = 'info') {
  let container = document.getElementById('toast-container');
  if (!container) {
    container = document.createElement('div');
    container.id = 'toast-container';
    container.className = 'toast-container';
    document.body.appendChild(container);
  }

  const toast = document.createElement('div');
  toast.className = `toast-custom ${type}`;
  const icon = type === 'success' ? 'bi-check-circle-fill' : type === 'error' ? 'bi-exclamation-circle-fill' : 'bi-info-circle-fill';
  toast.innerHTML = `<i class="bi ${icon}"></i><span>${message}</span><button class="toast-close" onclick="this.parentElement.remove()">&times;</button>`;
  container.appendChild(toast);

  setTimeout(() => {
    if (toast.parentElement) toast.remove();
  }, 4000);
}

function showSuccess(msg) { showToast(msg, 'success'); }
function showError(msg) { showToast(msg, 'error'); }

function formatCurrency(amount) {
  return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(amount);
}

function formatDate(dateStr) {
  return new Date(dateStr).toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' });
}

function renderSidebar(activePage) {
  const session = getSession();
  if (!session) return;
  const isAdmin = session.user.role === 'admin';
  const initials = session.user.fullName.split(' ').map(n => n[0]).join('');

  const sidebar = document.getElementById('app-sidebar');
  if (!sidebar) return;

  sidebar.innerHTML = `
    <div class="sidebar-logo">
      <div class="logo-icon"><i class="bi bi-bank"></i></div>
      <h2>AuraBank</h2>
    </div>
    <nav class="sidebar-nav">
      <a href="/dashboard.html" id="nav-dashboard" class="${activePage === 'dashboard' ? 'active' : ''}"><i class="bi bi-grid-1x2-fill"></i> Dashboard</a>
      <a href="/loan.html" id="nav-loans" class="${activePage === 'loans' ? 'active' : ''}"><i class="bi bi-cash-stack"></i> Loans</a>
      ${isAdmin ? `<a href="/admin.html" id="nav-admin" class="${activePage === 'admin' ? 'active' : ''}"><i class="bi bi-shield-lock-fill"></i> Admin</a>` : ''}
    </nav>
    <div class="sidebar-user">
      <div class="avatar">${initials}</div>
      <div class="user-info">
        <div class="name">${session.user.fullName}</div>
        <div class="role">${session.user.role === 'admin' ? 'Administrator' : 'Customer'}</div>
      </div>
      <button class="btn-icon" id="btn-logout" onclick="clearSession(); window.location.href='/login.html';" title="Logout"><i class="bi bi-box-arrow-right"></i></button>
    </div>
  `;
}
