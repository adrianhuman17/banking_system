package bank.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Account {
private final String accountNumber;
private double balance;
private final String branch;
private final List<Transaction> transactions = new ArrayList<>();

protected Account(String accountNumber, String branch) {
if (accountNumber == null || accountNumber.isBlank()) {
throw new IllegalArgumentException("Account number is required");
}
if (branch == null || branch.isBlank()) {
throw new IllegalArgumentException("Branch is required");
}
this.accountNumber = accountNumber;
this.branch = branch;
}

public void deposit(double amt) {
deposit(amt, "Deposit");
}

public void deposit(double amt, String ref) {
requirePositive(amt);
credit(amt, Transaction.DEPOSIT, ref);
}

public abstract void withdraw(double amt);

public abstract String getAccountType();

public double getBalance() { return balance; }
public String getAccountNumber() { return accountNumber; }
public String getBranch() { return branch; }

public List<Transaction> getTransactions() {
return Collections.unmodifiableList(transactions);
}

protected void credit(double amt, String type, String description) {
balance += amt;
transactions.add(new Transaction(type, amt, description));
}

protected void debit(double amt) {
requirePositive(amt);
if (amt > balance) {
throw new IllegalArgumentException("Insufficient funds: balance is BWP" + String.format("%.2f", balance));
}
balance -= amt;
transactions.add(new Transaction(Transaction.WITHDRAWAL, amt, "Withdrawal"));
}

protected static void requirePositive(double amt) {
if (amt <= 0) {
throw new IllegalArgumentException("Amount must be greater than 0");
}
}

@Override
public String toString() {
return String.format("%s %s (%s) balance BWP%.2f", getAccountType(), accountNumber, branch, balance);
}
}