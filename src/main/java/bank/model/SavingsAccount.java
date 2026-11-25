package bank.model;

public class SavingsAccount extends Account implements InterestBearing {
private final double monthlyRatePercent = 0.05;

public SavingsAccount(String accountNumber, String branch) {
super(accountNumber, branch);
}

@Override
public void withdraw(double amt) {
throw new UnsupportedOperationException("Savings accounts do not allow withdrawals");
}

@Override
public String getAccountType() { return "SAVINGS"; }

@Override
public double calculateInterest() {
return Math.round(getBalance() * monthlyRatePercent / 100 * 100) / 100.0;
}

@Override
public void payInterest() {
double interest = calculateInterest();
if (interest > 0) {
credit(interest, Transaction.INTEREST, "Monthly interest");
}
}
}
