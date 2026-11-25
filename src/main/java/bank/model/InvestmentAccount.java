package bank.model;

public class InvestmentAccount extends Account implements InterestBearing {
public static final double MIN_OPENING_DEPOSIT = 500.00;

private final double monthlyRatePercent = 5.0;
private final double minDeposit = MIN_OPENING_DEPOSIT;

public InvestmentAccount(String accountNumber, String branch, double initialDeposit) {
super(accountNumber, branch);
if (initialDeposit < minDeposit) {
throw new IllegalArgumentException("An Investment account needs an initial deposit of at least BWP"
+ String.format("%.2f", minDeposit));
}
deposit(initialDeposit, "Opening deposit");
}

@Override
public void withdraw(double amt) {
debit(amt);
}

@Override
public String getAccountType() { return "INVESTMENT"; }

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