package model;

public class Order {
	
	Customer customer;
	double totalSum;
	String date;
	
	public Order(Customer customer, double totalSum, String date) {
		this.customer = customer;
		this.totalSum = totalSum;
		this.date = date;
	}
	
	// getter
	public Customer getCustomer() { return customer;}
	public double getTotalSum() { return totalSum; }
	public String getDate() { return date; }
	
	// setter
	public void setCustomer(Customer customer) { this.customer = customer; }
	public void setTotalSum(double totalSum) { this.totalSum = totalSum; }
	public void setDate(String date) { this.date = date; }
}