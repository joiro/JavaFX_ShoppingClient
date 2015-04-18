package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {

	private final StringProperty firstName, lastName, password, street, city, emailAddress;
	
	public Customer() {
		this(null, null, null, null, null, null);
	}
	
	public Customer(String firstName, String lastName, String password, String street, String city, String emailAddress) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.password = new SimpleStringProperty(password);
		this.street = new SimpleStringProperty(street);
		this.city = new SimpleStringProperty(city);
		this.emailAddress = new SimpleStringProperty(emailAddress);		
	}
	
    @Override
    public String toString() {
        return firstName.get();
    }
	
	// getter
	public String getFirstName() { return firstName.get(); }
	public String getLastName() { return lastName.get(); }
	public String getPassword() { return password.get(); }
	public String getStreet() { return street.get(); }
	public String getCity() { return city.get(); }
	public String getEmailAddress() { return emailAddress.get(); }
	
	// setter
	public void setFirstName(String name) { this.firstName.set(name); }
	public void setLastName(String name) { this.lastName.set(name); }
	public void setPassword(String password) { this.password.set(password); }
	public void setStreet(String street) { this.street.set(street); }
	public void setCity(String city) { this.city.set(city);}
	public void setEmailAddress(String emailAddress) { this.emailAddress.set(emailAddress);}
	
	
	
}
