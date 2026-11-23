package bank.model;

import java.time.LocalDateTime;

public class Transaction {
public static final String DEPOSIT = "DEPOSIT";
public static final String WITHDRAWAL = "WITHDRAWAL";
public static final String INTEREST = "INTEREST";

private static int counter = 0;

private final String transactionId;
private final String type;
private final double amount;
private final String description;
private final LocalDateTime date;

public Transaction(String type, double amount, String description) {
counter++;
this.transactionId = String.format("T%05d", counter);
this.type = type;
this.amount = amount;
this.description = description;
this.date = LocalDateTime.now();
}

public String getTransactionId() { return transactionId; }
public String getType() { return type; }
public double getAmount() { return amount; }
public String getDescription() { return description; }
public LocalDateTime getDate() { return date; }

@Override
public String toString() {
return String.format("%s %-10s BWP%10.2f  %s", transactionId, type, amount, description);
}
}