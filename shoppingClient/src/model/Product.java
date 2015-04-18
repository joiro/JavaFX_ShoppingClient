package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {

	private StringProperty name;
	private StringProperty category;
	private int price;
	private int rating;
	
	public Product() {}
	
	public Product(String name, int price, String category) {
		this.name = new SimpleStringProperty(name);
		this.price = price;
		this.category = new SimpleStringProperty(category);
		
		
	}
	// getter
	public String getName() { return name.get(); }
	public String getCategory() { return category.get(); }
	public int getPrice() { return price; }
	public int getRating() { return rating; }
	
	// setter
	public void setName(String name) { this.name.set(name); }
	public void setPrice(int price) { this.price = price; }
	public void setCategory(String category) { this.category.set(category);}
	public void setRating(int rating) { this.rating = rating; }
 }
