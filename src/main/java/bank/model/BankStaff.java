package bank.model;

public class BankStaff {
private final String staffId;
private final String name;
private final String username;
private final String passwordHash;

public BankStaff(String staffId, String name, String username, String password) {
if (staffId == null || staffId.isBlank() || name == null || name.isBlank()
|| username == null || username.isBlank() || password == null || password.isBlank()) {
throw new IllegalArgumentException("Staff id, name, username and password are required");
}
this.staffId = staffId;
this.name = name;
this.username = username;
this.passwordHash = PasswordHasher.hash(password);
}

public boolean login(String pwd) {
return pwd != null && passwordHash.equals(PasswordHasher.hash(pwd));
}

public void registerCustomer(Customer c) {
if (c == null) {
throw new IllegalArgumentException("Customer is required");
}
}

public void openAccount(Customer c, Account a) {
if (c == null || a == null) {
throw new IllegalArgumentException("Customer and account are required");
}
if (c.hasAccountOfType(a.getAccountType())) {
throw new IllegalStateException("Customer already holds a " + a.getAccountType() + " account");
}
if (a instanceof ChequeAccount) {
if (!(c instanceof IndividualCustomer)) {
throw new IllegalStateException("Cheque accounts are for individuals only");
}
IndividualCustomer person = (IndividualCustomer) c;
if (!person.hasSourceOfIncome()) {
throw new IllegalStateException("A Cheque account needs a valid Omang and a source of income");
}
}
c.addAccount(a);
}

public String getStaffId() { return staffId; }
public String getName() { return name; }
public String getUsername() { return username; }
}