package bank;

import bank.model.*;

public class Main {
    public static void main(String[] args) {
        BankStaff staff = new BankStaff("S001", "Thato Moeng", "tmoeng", "staff123");
        IndividualCustomer lesedi = new IndividualCustomer("C001", "Lesedi", "Kgosi", "Plot 12, Gaborone",
                "123456789", "lkgosi", "pass123");
        lesedi.setEmployer("Choppies", "Broadhurst, Gaborone");
        CompanyCustomer firm = new CompanyCustomer("C002", "Kalahari Traders", "BW00012345", "Francistown",
                "Neo Phiri", "kalahari", "corp123");

        staff.registerCustomer(lesedi);
        staff.registerCustomer(firm);

        staff.openAccount(lesedi, new SavingsAccount("SAV-001", "Main Mall"));
        staff.openAccount(lesedi, new InvestmentAccount("INV-001", "Main Mall", 1000.00));
        staff.openAccount(lesedi, new ChequeAccount("CHQ-001", "Main Mall"));

        for (Account a : lesedi.getAccounts()) {
            a.deposit(1000.00);
        }
        lesedi.getAccounts().get(1).withdraw(200.00);
        try {
            lesedi.getAccounts().get(0).withdraw(50.00);
        } catch (UnsupportedOperationException e) {
            System.out.println("Refused: " + e.getMessage());
        }

        try {
            staff.openAccount(firm, new ChequeAccount("CHQ-002", "Francistown"));
        } catch (IllegalStateException e) {
            System.out.println("Refused: " + e.getMessage());
        }

        for (Account a : lesedi.getAccounts()) {
            if (a instanceof InterestBearing) {
                ((InterestBearing) a).payInterest();
            }
        }

        System.out.println("\nAccounts for " + lesedi + ":");
        for (Account a : lesedi.getAccounts()) {
            System.out.println("  " + a);
            for (Transaction t : a.getTransactions()) {
                System.out.println("      " + t);
            }
        }
    }
}