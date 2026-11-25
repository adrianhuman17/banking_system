package bank.model;

public class IndividualCustomer extends Customer {
private final String firstName;
private final String surname;
private final String omangNumber;
private String employerName;
private String employerAddress;

public IndividualCustomer(String customerId, String firstName, String surname, String address,
String omangNumber, String username, String password) {
super(customerId, address, username, password);
requireText(firstName, "First name");
requireText(surname, "Surname");
requireText(omangNumber, "Omang number");
this.firstName = firstName;
this.surname = surname;
this.omangNumber = omangNumber;
}

public void setEmployer(String employerName, String employerAddress) {
requireText(employerName, "Employer name");
requireText(employerAddress, "Employer address");
this.employerName = employerName;
this.employerAddress = employerAddress;
}

public boolean hasSourceOfIncome() {
return employerName != null && employerAddress != null;
}

public String getFirstName() { return firstName; }
public String getSurname() { return surname; }
public String getOmangNumber() { return omangNumber; }
public String getEmployerName() { return employerName; }
public String getEmployerAddress() { return employerAddress; }

@Override
public String toString() { return firstName + " " + surname + " (" + getCustomerId() + ")"; }
}