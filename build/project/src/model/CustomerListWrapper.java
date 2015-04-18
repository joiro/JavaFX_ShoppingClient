package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customer")
public class CustomerListWrapper {
	
	private List<Customer> customer;
	
	@XmlElement(name = "customer")
	public List<Customer> getCustomer() {
		return customer;
	}
	
    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }
}