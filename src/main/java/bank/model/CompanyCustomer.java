package bank.model;

public class CompanyCustomer extends Customer {
private final String companyName;
private final String registrationNumber;
private final String contactPerson;

public CompanyCustomer(String customerId, String companyName, String registrationNumber, String address,
String contactPerson, String username, String password) {
super(customerId, address, username, password);
requireText(companyName, "Company name");
requireText(registrationNumber, "Registration number");
requireText(contactPerson, "Contact person");
this.companyName = companyName;
this.registrationNumber = registrationNumber;
this.contactPerson = contactPerson;
}

public String getCompanyName() { return companyName; }
public String getRegistrationNumber() { return registrationNumber; }
public String getContactPerson() { return contactPerson; }

@Override
public String toString() { return companyName + " (" + getCustomerId() + ")"; }
}