package model;

import java.math.BigDecimal;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Product {

	private final StringProperty name;
	private final StringProperty category;
	private StringProperty description;
	private double price;
	private List<Integer> rating;
	private String image;
	private int number;
	
	// Initialisation
	public Product(String name, double price, String category, String description, String image, int number) {
		this.name = new SimpleStringProperty(name);
		this.price = price;
		this.category = new SimpleStringProperty(category);	
		this.description = new SimpleStringProperty(description);
		this.image = image;
		this.number = number;
		
	}
	// getter
	public StringProperty nameProperty() { return name;}
	
	public String getName() { return name.get(); }
	public String getCategory() { return category.get(); }
	public String getDescription() { return description.get(); }
	public double getPrice() { return price; }
	public List<Integer> getRating() { return rating; }
	public String getImage() { return image; }
	public int getNumber() { return number; }
	
	// setter
	public void setName(String name) { this.name.set(name); }
	public void setPrice(double price) { this.price = price; }
	public void setCategory(String category) { this.category.set(category);}
	public void setDescription(String description) { this.description.set(description);}
	public void setRating(List<Integer> rating) { this.rating = rating; }
	public void setImage(String image) { this.image = image; }
	public void setNumber(int number) { this.number = number; }
 }