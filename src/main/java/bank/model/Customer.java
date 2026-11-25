package bank.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Customer {
private final String customerId;
private final String address;
private final String username;
private final String passwordHash;
private final List<Account> accounts = new ArrayList<>();

protected Customer(String customerId, String address, String username, String password) {
requireText(customerId, "Customer id");
requireText(address, "Address");
requireText(username, "Username");
requireText(password, "Password");
this.customerId = customerId;
this.address = address;
this.username = username;
this.passwordHash = PasswordHasher.hash(password);
}

public boolean login(String pwd) {
return pwd != null && passwordHash.equals(PasswordHasher.hash(pwd));
}

public void addAccount(Account acc) {
if (acc == null) {
throw new IllegalArgumentException("Account is required");
}
if (hasAccountOfType(acc.getAccountType())) {
throw new IllegalStateException("Customer already holds a " + acc.getAccountType() + " account");
}
accounts.add(acc);
}

public List<Account> getAccounts() {
return Collections.unmodifiableList(accounts);
}

public boolean hasAccountOfType(String type) {
for (Account a : accounts) {
if (a.getAccountType().equals(type)) {
return true;
}
}
return false;
}

public String getCustomerId() { return customerId; }
public String getAddress() { return address; }
public String getUsername() { return username; }

protected static void requireText(String value, String name) {
if (value == null || value.isBlank()) {
throw new IllegalArgumentException(name + " is required");
}
}
}