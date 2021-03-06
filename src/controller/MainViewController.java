package controller;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Customer;
import model.Product;
import model.ProductDescription;

public class MainViewController {
	@FXML private TableView<Product> tableView;
	@FXML private TableView<Product> shoppingCart;
	@FXML private TableColumn<Product, String> nameColumn;
	@FXML private TableColumn<Product, String> priceColumn;
	@FXML private TableColumn<Product, String> categoryColumn;
	@FXML private TableColumn<Product, String> cartProductColumn;
	@FXML private TableColumn<Product, String> cartPriceColumn;
	@FXML private Label customerName, totalSum, items, name, price, category, ratingLabel, description;
	@FXML private TextField search;
	@FXML private ImageView imageView;
	@FXML private ImageView ratingOne, ratingTwo, ratingThree, ratingFour, ratingFive;
    @FXML private ComboBox<Integer> comboQuantity;
    @FXML private ComboBox<Integer> comboRating;
	
	private MainApp mainApp;
	private ProductDescription productDescription = new ProductDescription();
	private BasketController controller;
	private int quant, rating;
	private List<Integer> ratingArray;
	
	private Customer loggedInCustomer;
	
    @FXML
    private void initialize() {
    // Initialize the person table
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));

        tableView.getSelectionModel().selectedItemProperty().addListener(
        		new ChangeListener<Product>() {
                    public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
                    	showProductDetails(newValue);
                    }
                });
        
		// Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<Product> filteredList = new FilteredList<>(productData, p -> true);
		
		// Set the filter Predicate whenever the filter changes.
		search.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredList.setPredicate(product -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (product.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (Double.toString(product.getPrice()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (product.getCategory().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches Category.
				}
				return false; // Does not match.
			});
		});
		
		
		// Wrap the FilteredList in a SortedList. 
		SortedList<Product> SortedList = new SortedList<>(filteredList);
		
		//  Bind the SortedList comparator to the TableView comparator.
		SortedList.comparatorProperty().bind(tableView.comparatorProperty());
		
		// Add sorted (and filtered) data to the table.
		tableView.setItems(SortedList);
    }
    
    // Reference to mainApp == receives the logged in customer from the loginView
    public void setMainApp(MainApp mainApp, Customer customer) {
        this.mainApp = mainApp;
        this.addToList();
        loggedInCustomer = customer;
        // displayes the customer name top left
        setCustomerLabel();
        this.populateCombo();
        // sets the default product quantity to 1
        tableView.getSelectionModel().select(0);
    }
    
	public MainViewController() { }
	
	// populates the product list
	public void addToList() {
		productData.add(new Product("Banana", 0.39, "Food", productDescription.getProductDescription("Banana"), "http://assets.eatingwell.com/sites/default/files/imagecache/310_square/bananas_1.jpg", 1));
		productData.add(new Product("Red Apple", 0.19, "Food", productDescription.getProductDescription("Apple"), "http://ecx.images-amazon.com/images/I/11KOCxUWAqL.jpg", 1));
		productData.add(new Product("1l Whole Milk", 1.39, "Food", productDescription.getProductDescription("Milk"), "http://ecx.images-amazon.com/images/I/41rNYPLQTlL._SL500_SS115_.jpg", 1));
		productData.add(new Product("Cheddar Cheese", 2.79, "Food", productDescription.getProductDescription("Cheddar"), "http://ecx.images-amazon.com/images/I/51A-P9ReGSL._SX466_.jpg", 1));
		productData.add(new Product("Butter", 1.09, "Food", productDescription.getProductDescription("Butter"), "http://www.allmystery.de/i/t74d7de_fette_oele_butter_2.jpg", 1));
		productData.add(new Product("Apple IPhone 6 16GB grey", 549.99, "Electronics", productDescription.getProductDescription("Iphone"), "http://ecx.images-amazon.com/images/I/51eclIdmTuL.jpg", 1));
		productData.add(new Product("Panasonic 42' LCD Tv", 499.99, "Electronics", productDescription.getProductDescription("Tv"), "http://ecx.images-amazon.com/images/I/71o-i0JC1GL._SL1500_.jpg", 1));
		productData.add(new Product("Apple IPad Air 16GB white", 399.99, "Electronics", productDescription.getProductDescription("Ipad"), "http://ecx.images-amazon.com/images/I/51b-LjnkFJL._SY355_.jpg", 1));
		productData.add(new Product("Mountainbike 'Trackmaster'", 699.99, "Sports", productDescription.getProductDescription("Bike"), "http://ecx.images-amazon.com/images/I/61PdltjOCfL._SX522_.jpg", 1));
	}
    
	// manages the product details on the right in the main View
    private void showProductDetails(Product Product){
        if (Product != null){
            // Fill labels with details from selected Product
            name.setText(Product.getName());
            double priceDouble = Product.getPrice();
            String priceString = Double.toString(priceDouble);
            price.setText("£"+priceString);
            category.setText(Product.getCategory());
            Image productImage = new Image(Product.getImage());
            imageView.setImage(productImage);
            description.setText(Product.getDescription());
            calculateRating();

        } else{
            // Product is null so set labels to be blank
            name.setText("");
            price.setText("no price");
            category.setText("");
        }
    }
    
    // receives the rating input
    @FXML public void handleRating() {
    	Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
    	int newRating = comboRating.getSelectionModel().getSelectedItem();
    	ratingArray = selectedProduct.getRating();
        if (ratingArray == null) {
        	ratingArray = new ArrayList<Integer>();
        }
    	ratingArray.add(newRating);
    	selectedProduct.setRating(ratingArray);
    	calculateRating();
    }
    
    // calculates average rating and manages visibility of the stars
    public void calculateRating() {
    	Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
    	ratingArray = selectedProduct.getRating();
        if (ratingArray == null) {
        	rating = 1;
        } else {
        	int sum = 0;
        	for (int i : ratingArray) {
        		sum += i;
        	}
        	rating = sum / ratingArray.size();
        }
        switch (rating) {
        	case 5 :
        		ratingOne.setVisible(true);
        		ratingTwo.setVisible(true);
        		ratingThree.setVisible(true);
        		ratingFour.setVisible(true);
        		ratingFive.setVisible(true);
        		break;
        	case 4 :
        		ratingOne.setVisible(true);
        		ratingTwo.setVisible(true);
        		ratingThree.setVisible(true);
        		ratingFour.setVisible(true);
        		ratingFive.setVisible(false);
        		break;
        	case 3 :
        		ratingOne.setVisible(true);
        		ratingTwo.setVisible(true);
        		ratingThree.setVisible(true);
        		ratingFour.setVisible(false);
        		ratingFive.setVisible(false);
        		break;
        	case 2 :
        		ratingOne.setVisible(true);
        		ratingTwo.setVisible(true);
        		ratingThree.setVisible(false);
        		ratingFour.setVisible(false);
        		ratingFive.setVisible(false);
        		break;
        	case 1 :
        		ratingOne.setVisible(true);
        		ratingTwo.setVisible(false);
        		ratingThree.setVisible(false);
        		ratingFour.setVisible(false);
        		ratingFive.setVisible(false);
        		break;
        }
    }
    
    // updates the total price and item number in the labels top right
    @FXML public void setLabelText() {
    	items.setText(Integer.toString(mainApp.getBasketList().size()));
    	if (mainApp.getBasketList().size() != 0) {
    		totalSum.setText("£"+Double.toString(mainApp.getTotalPrice()));
    	} else {
    		totalSum.setText("£0.00");
    	}
    }
    
    // opens the basket window
    @FXML private void handleViewOrder() { mainApp.showViewBasket(loggedInCustomer); }
    
    // opens the customerView
    @FXML private void handleViewCustomer() { mainApp.showCustomer(loggedInCustomer); }
    
    
    // adds items to the shopping cart
	@FXML private void handleAddToCart() {
		Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
		if (selectedProduct != null) {
			// checks selected quantity
			quant = (int) comboQuantity.getSelectionModel().getSelectedItem();
			mainApp.addBasket(selectedProduct, quant);
			// update the total price label
			setLabelText();
			// reset the combobox selection
			comboQuantity.getSelectionModel().select(0);
		} else {
			System.out.println("No item selected!");
		}
	 }
	
	
	private void populateCombo() {
		// populate comboBox for number of products and set the default to '1'
		comboQuantity.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
		comboQuantity.getSelectionModel().select(0);
		// populate comboBox for rating, sets default to null
		comboRating.getItems().addAll(1,2,3,4,5);
		comboRating.getSelectionModel().select(null);
	}
	
	@FXML public void setCustomerLabel() {
		customerName.setText(loggedInCustomer.getFirstName()+" "+loggedInCustomer.getLastName());
	}
    
	// contains the products displayed in mainView
	private ObservableList<Product> productData = FXCollections.observableArrayList();
	
	public ObservableList<Product> getProductData() { return productData; }
}