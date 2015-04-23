package model;

import java.math.BigDecimal;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {

	private final StringProperty name;
	private final StringProperty category;
	private double price;
	private int rating;
/*	
	public Product() {
		
		this(null, price, null);
	}
*/	
	public Product(String name, double price, String category) {
		this.name = new SimpleStringProperty(name);
		this.price = price;
		this.category = new SimpleStringProperty(category);	
		
	}
	// getter
	
	public StringProperty nameProperty() { return name;}
	
	public String getName() { return name.get(); }
	public String getCategory() { return category.get(); }
	public double getPrice() { return price; }
	public int getRating() { return rating; }
	
	// setter
	public void setName(String name) { this.name.set(name); }
	public void setPrice(double price) { this.price = price; }
	public void setCategory(String category) { this.category.set(category);}
	public void setRating(int rating) { this.rating = rating; }
 }
