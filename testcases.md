# AuraBank — Test Scenarios

---

## 1. Login Page

### 1.1 UI Elements Verification
-  TC_LOGIN_001: Verify the login page loads at `/login.html`
- TC_LOGIN_002: Verify AuraBank logo and title are displayed
- TC_LOGIN_003: Verify username input field is present (`#input-username`)
- TC_LOGIN_004: Verify password input field is present (`#input-password`)
- TC_LOGIN_005: Verify "Test Mode" toggle is present (`#toggle-test-mode`)
- TC_LOGIN_006: Verify "Sign In" button is present (`#btn-login`)
- TC_LOGIN_007: Verify password visibility toggle button works (`#btn-toggle-password`)
- TC_LOGIN_008: Verify "Protected by 256-bit encryption" text is displayed
- TC_LOGIN_009: Verify placeholder text in username field says "Enter your username"
- TC_LOGIN_010: Verify placeholder text in password field says "Enter your password"

### 1.2 Test Mode
- TC_LOGIN_011: Enable Test Mode toggle — verify username auto-fills with `john.doe`
- TC_LOGIN_012: Enable Test Mode toggle — verify password auto-fills with `Pass@123`
- TC_LOGIN_013: Disable Test Mode toggle — verify both fields are cleared
- TC_LOGIN_014: Enable Test Mode and click Sign In — verify successful login

### 1.3 Valid Login
- TC_LOGIN_015: Login with valid customer credentials (`john.doe` / `Pass@123`) — verify redirect to dashboard
- TC_LOGIN_016: Login with valid admin credentials (`admin` / `Admin@123`) — verify redirect to dashboard
- TC_LOGIN_017: Login with valid customer (`jane.smith` / `Jane@456`) — verify redirect to dashboard
- TC_LOGIN_018: Verify success toast notification appears on valid login

### 1.4 Invalid Login
- TC_LOGIN_019: Login with invalid username — verify error message displayed (`#login-error`)
- TC_LOGIN_020: Login with invalid password — verify error message displayed
- TC_LOGIN_021: Login with empty username — verify form validation prevents submission
- TC_LOGIN_022: Login with empty password — verify form validation prevents submission
- TC_LOGIN_023: Login with both fields empty — verify form validation
- TC_LOGIN_024: Verify error toast notification appears on invalid login
- TC_LOGIN_025: Login with correct username but wrong password — verify error

### 1.5 Password Field
- TC_LOGIN_026: Verify password field masks input by default (type="password")
- TC_LOGIN_027: Click eye icon — verify password becomes visible (type="text")
- TC_LOGIN_028: Click eye icon again — verify password is masked again
- TC_LOGIN_029: Verify eye icon changes between `bi-eye` and `bi-eye-slash`

### 1.6 Session & Redirect
- TC_LOGIN_030: Verify logged-in user visiting `/login.html` is redirected to dashboard
- TC_LOGIN_031: Verify session is stored in `sessionStorage` after login
- TC_LOGIN_032: Verify loading spinner appears during login request

---

## 2. Dashboard Page

### 2.1 Page Load & Auth
- TC_DASH_001: Verify unauthenticated user is redirected to login page
- TC_DASH_002: Verify dashboard loads successfully for authenticated user
- TC_DASH_003: Verify welcome message shows user's full name (`#text-welcome`)
- TC_DASH_004: Verify page title is "AuraBank — Dashboard"

### 2.2 Sidebar Navigation
- TC_DASH_005: Verify sidebar displays AuraBank logo
- TC_DASH_006: Verify "Dashboard" nav link is active/highlighted (`#nav-dashboard`)
- TC_DASH_007: Verify "Loans" nav link is present (`#nav-loans`)
- TC_DASH_008: Verify "Admin" nav link is visible only for admin role (`#nav-admin`)
- TC_DASH_009: Verify "Admin" nav link is hidden for customer role
- TC_DASH_010: Verify user initials avatar is displayed in sidebar
- TC_DASH_011: Verify user full name is displayed in sidebar
- TC_DASH_012: Verify user role ("Customer" or "Administrator") is displayed
- TC_DASH_013: Click "Loans" link — verify navigation to `/loan.html`
- TC_DASH_014: Click "Admin" link (as admin) — verify navigation to `/admin.html`

### 2.3 Logout
- TC_DASH_015: Click logout button (`#btn-logout`) — verify redirect to login page
- TC_DASH_016: After logout, verify session is cleared from `sessionStorage`
- TC_DASH_017: After logout, verify back button does not return to dashboard

### 2.4 Stat Cards
- TC_DASH_018: Verify "Total Balance" card displays correct balance (`#text-balance`)
- TC_DASH_019: Verify "Income" card shows sum of all Credit transactions (`#text-income`)
- TC_DASH_020: Verify "Expenses" card shows sum of all Debit transactions (`#text-expenses`)
- TC_DASH_021: Verify "Transfers" card shows count of Transfer transactions (`#text-transfers`)
- TC_DASH_022: Verify stat cards have hover animation (translateY + shadow)
- TC_DASH_023: Verify balance matches the value from `/api/accounts/:id`

### 2.5 Transaction Table
- TC_DASH_024: Verify transaction table is present (`#table-transactions`)
- TC_DASH_025: Verify table has columns: ID, Date, Description, Type, Amount, Status
- TC_DASH_026: Verify at least 10 rows are displayed in the table
- TC_DASH_027: Verify all 12 transactions from db.json are displayed
- TC_DASH_028: Verify Credit amounts are shown in green with `+` prefix
- TC_DASH_029: Verify Debit amounts are shown in red with `-` prefix
- TC_DASH_030: Verify Transfer amounts are shown in blue with `-` prefix
- TC_DASH_031: Verify each row has a `data-testid="txn-row-TXNXXX"` attribute
- TC_DASH_032: Verify type badges have correct CSS classes (`.credit`, `.debit`, `.transfer`)
- TC_DASH_033: Verify status badge shows "Completed"
- TC_DASH_034: Verify date is formatted as "Mon DD, YYYY" (e.g., "Apr 27, 2026")
- TC_DASH_035: Verify amounts are formatted as currency (e.g., "$5,200.00")
- TC_DASH_036: Verify table rows have hover highlight effect

### 2.6 Sort by Date
- TC_DASH_037: Click "Sort by Date" (`#btn-sort-date`) — verify transactions sort descending
- TC_DASH_038: Click "Sort by Date" again — verify transactions sort ascending
- TC_DASH_039: Verify sort icon toggles between `bi-sort-down` and `bi-sort-up`
- TC_DASH_040: Verify first row is the latest transaction after descending sort
- TC_DASH_041: Verify first row is the oldest transaction after ascending sort
- TC_DASH_042: Sort by date while a filter is active — verify both work together

### 2.7 Filter by Type
- TC_DASH_043: Select "All Types" (`#select-filter-type`) — verify all transactions shown
- TC_DASH_044: Select "Credit" — verify only Credit transactions are shown
- TC_DASH_045: Select "Debit" — verify only Debit transactions are shown
- TC_DASH_046: Select "Transfer" — verify only Transfer transactions are shown
- TC_DASH_047: Filter by Credit — verify stat cards update accordingly
- TC_DASH_048: Filter by Credit — verify row count matches Credit entries (4 rows)
- TC_DASH_049: Filter by Debit — verify row count matches Debit entries (6 rows)
- TC_DASH_050: Filter by Transfer — verify row count matches Transfer entries (2 rows)
- TC_DASH_051: Switch filter back to "All Types" — verify all 12 rows return

---

## 3. Transfer Modal

### 3.1 Modal Open/Close
- TC_TFR_001: Click "New Transfer" (`#btn-open-transfer`) — verify modal opens
- TC_TFR_002: Verify modal has id `modal-transfer`
- TC_TFR_003: Verify modal title says "New Transfer"
- TC_TFR_004: Click close (X) button — verify modal closes
- TC_TFR_005: Press Escape key — verify modal closes
- TC_TFR_006: Verify modal resets to Step 1 when reopened after close

### 3.2 Step Indicator
- TC_TFR_007: Verify 4 step dots are displayed in the step indicator
- TC_TFR_008: Verify Step 1 dot is active on modal open
- TC_TFR_009: On Step 2, verify Step 1 dot changes to "done" state
- TC_TFR_010: On Step 3, verify Steps 1 & 2 are "done" and Step 3 is "active"

### 3.3 Step 1 — Details
- TC_TFR_011: Verify recipient input is present (`#input-recipient`)
- TC_TFR_012: Verify amount input is present (`#input-amount`)
- TC_TFR_013: Verify memo input is present (`#input-memo`)
- TC_TFR_014: Verify "Continue" button is present (`#btn-transfer-next`)
- TC_TFR_015: Click Continue with empty recipient — verify error toast
- TC_TFR_016: Click Continue with empty amount — verify error toast
- TC_TFR_017: Click Continue with amount = 0 — verify error toast
- TC_TFR_018: Click Continue with negative amount — verify error toast
- TC_TFR_019: Fill recipient and valid amount — click Continue — verify Step 2 loads

### 3.4 Step 2 — Confirm
- TC_TFR_020: Verify confirmation shows recipient account (`#confirm-recipient`)
- TC_TFR_021: Verify confirmation shows formatted amount (`#confirm-amount`)
- TC_TFR_022: Verify confirmation shows memo (or "—" if empty)
- TC_TFR_023: Verify "Back" button is visible (`#btn-transfer-back`)
- TC_TFR_024: Verify "Confirm Transfer" button is visible (`#btn-transfer-confirm`)
- TC_TFR_025: Click Back — verify return to Step 1 with data preserved

### 3.5 Step 3 — Processing
- TC_TFR_026: Click "Confirm Transfer" — verify Step 3 spinner appears (`#transfer-spinner`)
- TC_TFR_027: Verify "Transferring..." text is displayed (`#text-transferring`)
- TC_TFR_028: Verify footer buttons are hidden during processing
- TC_TFR_029: Verify the processing state lasts approximately 2 seconds (setTimeout)
- TC_TFR_030: Verify spinner has CSS rotation animation

### 3.6 Step 4 — Result (Success)
- TC_TFR_031: Verify "Transfer Successful" message appears (`#transfer-result`)
- TC_TFR_032: Verify green success icon is displayed
- TC_TFR_033: Verify new balance is shown after transfer
- TC_TFR_034: Verify "Done" button appears (`#btn-transfer-done`)
- TC_TFR_035: Verify success toast notification fires
- TC_TFR_036: Click Done — verify modal closes
- TC_TFR_037: Verify balance card on dashboard updates after transfer
- TC_TFR_038: Verify new transaction row appears in the table

### 3.7 Step 4 — Result (Failure)
- TC_TFR_039: Transfer amount exceeding balance — verify "Insufficient balance" error
- TC_TFR_040: Verify red error icon is displayed on failure
- TC_TFR_041: Verify error toast notification fires on failure
- TC_TFR_042: Verify balance does NOT change after failed transfer

---

## 4. Loan Page

### 4.1 Page Load & Auth
- TC_LOAN_001: Verify unauthenticated user is redirected to login
- TC_LOAN_002: Verify loan page loads at `/loan.html`
- TC_LOAN_003: Verify "Loans" nav link is active in sidebar
- TC_LOAN_004: Verify page title is "AuraBank — Loan Application"

### 4.2 Range Slider
- TC_LOAN_005: Verify range slider is present (`#range-loan-amount`)
- TC_LOAN_006: Verify slider min value is 10,000
- TC_LOAN_007: Verify slider max value is 1,000,000
- TC_LOAN_008: Verify slider step is 5,000
- TC_LOAN_009: Verify default value displays "$50,000" (`#text-loan-amount`)
- TC_LOAN_010: Move slider to minimum — verify display shows "$10,000"
- TC_LOAN_011: Move slider to maximum — verify display shows "$1,000,000"
- TC_LOAN_012: Move slider to midpoint — verify display updates in real-time
- TC_LOAN_013: Verify slider track fills with primary color as value increases
- TC_LOAN_014: Verify range labels "$10,000" and "$1,000,000" are displayed

### 4.3 EMI Calculator (Live Updates)
- TC_LOAN_015: Verify Monthly EMI is displayed (`#text-emi`)
- TC_LOAN_016: Verify Interest Rate is displayed (`#text-interest-rate`)
- TC_LOAN_017: Verify Tenure shows "60 mo"
- TC_LOAN_018: Verify Total Payable is displayed
- TC_LOAN_019: Change slider value — verify EMI updates in real-time
- TC_LOAN_020: Loan ≤ $50,000 — verify interest rate is 7.5%
- TC_LOAN_021: Loan $50,001–$200,000 — verify interest rate is 8.5%
- TC_LOAN_022: Loan $200,001–$500,000 — verify interest rate is 9.5%
- TC_LOAN_023: Loan > $500,000 — verify interest rate is 10.5%
- TC_LOAN_024: Verify Total Payable = EMI × 60

### 4.4 Date Picker
- TC_LOAN_025: Verify date picker is present (`#input-repayment-date`)
- TC_LOAN_026: Verify minimum selectable date is tomorrow
- TC_LOAN_027: Verify past dates are not selectable
- TC_LOAN_028: Verify today's date is not selectable
- TC_LOAN_029: Select a valid future date — verify it is accepted

### 4.5 File Upload
- TC_LOAN_030: Verify file upload zone is present (`#file-drop-zone`)
- TC_LOAN_031: Verify hidden file input exists (`#input-id-upload`)
- TC_LOAN_032: Click upload zone — verify file dialog opens
- TC_LOAN_033: Select a PDF file — verify file name appears (`#text-file-name`)
- TC_LOAN_034: Select a JPG file — verify file name appears
- TC_LOAN_035: Select a PNG file — verify file name appears
- TC_LOAN_036: Verify upload zone changes to "active" state after file selection
- TC_LOAN_037: Verify file input accepts only `.pdf,.jpg,.jpeg,.png`
- TC_LOAN_038: Drag and drop a file onto the zone — verify file is accepted
- TC_LOAN_039: Verify drag-over changes zone border color

### 4.6 Form Submission
- TC_LOAN_040: Submit with all fields valid — verify success toast
- TC_LOAN_041: Submit without selecting a date — verify error toast
- TC_LOAN_042: Submit without uploading a file — verify error toast
- TC_LOAN_043: Verify submit button is present (`#btn-submit-loan`)
- TC_LOAN_044: After successful submission, verify form resets
- TC_LOAN_045: After submission, verify file name display is hidden
- TC_LOAN_046: After submission, verify slider resets to default

### 4.7 Eligibility Section
- TC_LOAN_047: Verify eligibility criteria list is displayed (5 items)
- TC_LOAN_048: Verify criteria include: age, income, ID, approval time, prepayment

---

## 5. Admin Page

### 5.1 Access Control
- TC_ADMIN_001: Login as customer — navigate to `/admin.html` — verify redirect to dashboard
- TC_ADMIN_002: Login as admin — navigate to `/admin.html` — verify page loads
- TC_ADMIN_003: Verify unauthenticated user is redirected to login

### 5.2 Page Layout
- TC_ADMIN_004: Verify page title includes "Admin Panel"
- TC_ADMIN_005: Verify "Admin" nav link is active in sidebar
- TC_ADMIN_006: Verify "Refresh Logs" button is present (`#btn-refresh-audit`)
- TC_ADMIN_007: Verify admin panel container is present (`#admin-panel`)

### 5.3 Iframe
- TC_ADMIN_008: Verify iframe element exists (`#iframe-audit`)
- TC_ADMIN_009: Verify iframe `src` points to `/audit-log.html`
- TC_ADMIN_010: Verify iframe loads audit log content successfully
- TC_ADMIN_011: Verify iframe has title attribute "Audit Log"
- TC_ADMIN_012: Click "Refresh Logs" — verify iframe reloads (src changes)
- TC_ADMIN_013: Verify iframe height fills the available viewport

### 5.4 Switching to Iframe Context (Selenium-specific)
- TC_ADMIN_014: Switch to iframe context — verify audit list is accessible
- TC_ADMIN_015: Switch back to default content — verify admin header is accessible

---

## 6. Audit Log Page (inside iframe)

### 6.1 Data Loading
- TC_AUDIT_001: Verify audit log list is present (`#list-audit-log`)
- TC_AUDIT_002: Verify at least 10 audit entries are displayed
- TC_AUDIT_003: Verify entry count text is displayed (`#audit-count`)
- TC_AUDIT_004: Verify each entry has `data-testid="audit-item"`

### 6.2 Audit Entry Structure
- TC_AUDIT_005: Verify each entry shows action type (e.g., "LOGIN", "TRANSFER")
- TC_AUDIT_006: Verify each entry shows detail text
- TC_AUDIT_007: Verify each entry shows user name
- TC_AUDIT_008: Verify each entry shows formatted timestamp
- TC_AUDIT_009: Verify each entry has an icon matching the action type
- TC_AUDIT_010: Verify severity-based icon coloring (info=blue, warning=amber, error=red)

### 6.3 Search/Filter
- TC_AUDIT_011: Verify search input is present (`#input-audit-search`)
- TC_AUDIT_012: Type "LOGIN" in search — verify only login entries are visible
- TC_AUDIT_013: Type "TRANSFER" in search — verify only transfer entries are visible
- TC_AUDIT_014: Type "admin" in search — verify only admin user entries are visible
- TC_AUDIT_015: Type a non-matching string — verify zero entries visible
- TC_AUDIT_016: Clear search — verify all entries return
- TC_AUDIT_017: Verify entry count updates as search filters results

---

## 7. Toast Notifications

- TC_TOAST_001: Verify toast container is created dynamically (`#toast-container`)
- TC_TOAST_002: Verify success toast has green background
- TC_TOAST_003: Verify error toast has red background
- TC_TOAST_004: Verify toast auto-dismisses after ~4 seconds
- TC_TOAST_005: Click toast close button — verify toast is removed
- TC_TOAST_006: Verify toast has slide-in animation from the right
- TC_TOAST_007: Verify multiple toasts stack vertically
- TC_TOAST_008: Trigger success toast on: login, transfer complete, loan submitted
- TC_TOAST_009: Trigger error toast on: login fail, transfer fail, missing form fields

---

## 8. API Endpoint Testing

### 8.1 POST /api/login
- TC_API_001: Valid credentials — verify 200 with `success: true` and user data
- TC_API_002: Invalid credentials — verify 401 with `success: false`
- TC_API_003: Missing username — verify 401
- TC_API_004: Missing password — verify 401
- TC_API_005: Verify response includes `account` object for valid login
- TC_API_006: Verify audit log entry is created on login

### 8.2 GET /api/transactions
- TC_API_007: Fetch all transactions — verify 200 with array
- TC_API_008: Filter by `accountId=ACC001` — verify only ACC001 transactions
- TC_API_009: Sort by `sort=date&order=asc` — verify ascending order
- TC_API_010: Sort by `sort=date&order=desc` — verify descending order
- TC_API_011: Filter by `type=Credit` — verify only Credit transactions
- TC_API_012: Filter by `type=Debit` — verify only Debit transactions
- TC_API_013: Filter by `type=Transfer` — verify only Transfer transactions
- TC_API_014: Combine sort and filter — verify both applied

### 8.3 POST /api/transfer
- TC_API_015: Valid transfer — verify 200 with success after ~2s delay
- TC_API_016: Verify response includes `newBalance`
- TC_API_017: Verify response includes `transaction` object
- TC_API_018: Transfer with insufficient balance — verify 400 error
- TC_API_019: Transfer with missing recipient — verify 400 error
- TC_API_020: Transfer with amount = 0 — verify 400 error
- TC_API_021: Transfer with negative amount — verify 400 error
- TC_API_022: Verify account balance is deducted after transfer
- TC_API_023: Verify new transaction is added to db.json
- TC_API_024: Verify audit log entry is created for transfer
- TC_API_025: Verify response time is ≥ 2 seconds (setTimeout)

### 8.4 POST /api/loans/apply
- TC_API_026: Valid loan application — verify 200 with success
- TC_API_027: Missing amount — verify 400 error
- TC_API_028: Missing repayment date — verify 400 error
- TC_API_029: Verify loan entry is added to db.json
- TC_API_030: Verify audit log entry is created for loan application

### 8.5 GET /api/audit
- TC_API_031: Fetch audit log — verify 200 with array
- TC_API_032: Verify audit entries have: id, timestamp, action, user, details, severity

### 8.6 GET /api/accounts/:id
- TC_API_033: Valid account ID — verify 200 with account data
- TC_API_034: Invalid account ID — verify 404 error

---

## 9. Cross-Cutting / E2E Scenarios

- TC_E2E_001: Full flow — Login → View Dashboard → Make Transfer → Verify new transaction in table
- TC_E2E_002: Full flow — Login → Apply for Loan → Verify success toast
- TC_E2E_003: Full flow — Login as Admin → View Audit Log → Search for recent login entry
- TC_E2E_004: Transfer creates audit entry — Login as Admin → Verify transfer appears in audit log
- TC_E2E_005: Login as customer → Attempt to access `/admin.html` → Verify redirected to dashboard
- TC_E2E_006: Perform transfer → Logout → Login again → Verify updated balance persists
- TC_E2E_007: Sort table descending → Filter by Credit → Verify results are sorted Credit-only
- TC_E2E_008: Open transfer modal → Fill Step 1 → Go to Step 2 → Go Back → Verify data is preserved
- TC_E2E_009: Multiple failed login attempts → Login successfully → Verify audit log has all attempts

---

## 10. Responsive & Visual

- TC_VIS_001: Verify login page is centered on desktop (1920×1080)
- TC_VIS_002: Verify dashboard layout on tablet (768px width)
- TC_VIS_003: Verify sidebar collapses on mobile (< 768px)
- TC_VIS_004: Verify transaction table is horizontally scrollable on mobile
- TC_VIS_005: Verify stat cards stack vertically on mobile
- TC_VIS_006: Verify loan info grid switches to single column on mobile
- TC_VIS_007: Verify all pages use Inter font family
- TC_VIS_008: Verify hover effects on stat cards (lift + shadow)
- TC_VIS_009: Verify hover effects on table rows (background highlight)
- TC_VIS_010: Verify fade-in-up animation on page load

---

> **Total Test Scenarios: 175**
