package model;

public class Order {
	
	Customer customer;
	String totalSum;
	String date;
	
	// Initialisation
	public Order(Customer customer, String totalSum, String date) {
		this.customer = customer;
		this.totalSum = totalSum;
		this.date = date;
	}

	// getter
	public Customer getCustomer() { return customer;}
	public String getTotalSum() { return totalSum; }
	public String getDate() { return date; }
	
	// setter
	public void setCustomer(Customer customer) { this.customer = customer; }
	public void setTotalSum(String totalSum) { this.totalSum = totalSum; }
	public void setDate(String date) { this.date = date; }
}