package bank;

import bank.model.*;

public class ModelTests {
    private static int passed = 0, failed = 0;

    private static void check(String name, boolean ok) {
        if (ok) { passed++; System.out.println("PASS  " + name); }
        else { failed++; System.out.println("FAIL  " + name); }
    }

    private static boolean throwsException(Class<? extends Exception> type, Runnable r) {
        try { r.run(); return false; }
        catch (Exception e) { return type.isInstance(e); }
    }

    private static IndividualCustomer person(boolean withIncome) {
        IndividualCustomer p = new IndividualCustomer("C1", "Lesedi", "Kgosi", "Gaborone", "123456789", "lk", "pw1");
        if (withIncome) p.setEmployer("Choppies", "Gaborone");
        return p;
    }

    public static void main(String[] args) {
        BankStaff staff = new BankStaff("S1", "Thato", "thato", "staffpw");

        SavingsAccount sav = new SavingsAccount("SAV-1", "Main Mall");
        sav.deposit(1000);
        check("Savings deposit adds to balance", sav.getBalance() == 1000);
        check("Savings withdrawal is refused", throwsException(UnsupportedOperationException.class, () -> sav.withdraw(10)));
        check("Savings interest is 0.05% of balance", sav.calculateInterest() == 0.50);
        sav.payInterest();
        check("Savings interest is added to the balance", sav.getBalance() == 1000.50);

        check("Investment needs at least BWP500 to open",
                throwsException(IllegalArgumentException.class, () -> new InvestmentAccount("INV-0", "Main Mall", 499.99)));
        InvestmentAccount inv = new InvestmentAccount("INV-1", "Main Mall", 1000);
        check("Investment opens with BWP1000", inv.getBalance() == 1000);
        check("Investment interest is 5% of balance", inv.calculateInterest() == 50.00);
        inv.withdraw(200);
        check("Investment withdrawal reduces balance", inv.getBalance() == 800);
        check("Withdrawal cannot take balance below zero", throwsException(IllegalArgumentException.class, () -> inv.withdraw(900)));
        check("Deposit of zero or less is refused", throwsException(IllegalArgumentException.class, () -> inv.deposit(0)));
        inv.payInterest();
        check("Interest is recorded as a transaction",
                inv.getTransactions().get(inv.getTransactions().size() - 1).getType().equals(Transaction.INTEREST));
        check("Every transaction is kept in the history", inv.getTransactions().size() == 3);

        ChequeAccount chq = new ChequeAccount("CHQ-1", "Main Mall");
        chq.deposit(300);
        chq.withdraw(100);
        check("Cheque deposit and withdrawal work", chq.getBalance() == 200);
        check("Cheque account does not earn interest", !(chq instanceof InterestBearing));

        IndividualCustomer lesedi = person(true);
        staff.openAccount(lesedi, new SavingsAccount("SAV-2", "Main Mall"));
        check("A customer cannot hold two accounts of the same type",
                throwsException(IllegalStateException.class, () -> staff.openAccount(lesedi, new SavingsAccount("SAV-3", "Main Mall"))));
        check("A customer can hold one of each type", lesedi.getAccounts().size() == 1 && lesedi.hasAccountOfType("SAVINGS"));
        CompanyCustomer firm = new CompanyCustomer("C2", "Kalahari Traders", "BW0001", "Francistown", "Neo", "kt", "pw2");
        check("A company cannot open a Cheque account",
                throwsException(IllegalStateException.class, () -> staff.openAccount(firm, new ChequeAccount("CHQ-2", "Main Mall"))));
        IndividualCustomer noIncome = person(false);
        check("A Cheque account needs a source of income",
                throwsException(IllegalStateException.class, () -> staff.openAccount(noIncome, new ChequeAccount("CHQ-3", "Main Mall"))));
        staff.openAccount(lesedi, new ChequeAccount("CHQ-4", "Main Mall"));
        check("An individual with income can open a Cheque account", lesedi.hasAccountOfType("CHEQUE"));
        staff.openAccount(firm, new SavingsAccount("SAV-4", "Main Mall"));
        check("A company can open a Savings account", firm.hasAccountOfType("SAVINGS"));

        check("Correct password logs in", lesedi.login("pw1"));
        check("Wrong password is refused", !lesedi.login("wrong"));
        check("Staff login works", staff.login("staffpw") && !staff.login("nope"));
        check("Password is stored as a 64-character hash", PasswordHasher.hash("pw1").length() == 64);

        System.out.println("\n" + passed + " passed, " + failed + " failed");
        if (failed > 0) System.exit(1);
    }
}