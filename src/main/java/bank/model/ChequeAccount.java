package bank.model;

public class ChequeAccount extends Account {
public ChequeAccount(String accountNumber, String branch) {
super(accountNumber, branch);
}

@Override
public void withdraw(double amt) {
debit(amt);
}

@Override
public String getAccountType() { return "CHEQUE"; }
}