package tableView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Product;

public class mainViewController {
	@FXML private TableView<Product> tableView;
	@FXML private TableView<Product> shoppingCart;
	@FXML private TableColumn<Product, String> nameColumn;
	@FXML private TableColumn<Product, String> priceColumn;
	@FXML private TableColumn<Product, String> categoryColumn;
	@FXML private TableColumn<Product, String> cartProductColumn;
	@FXML private TableColumn<Product, String> cartPriceColumn;
	@FXML private Label customerName, totalSum, items, name, price, category, rating, description;
	@FXML private TextField search;
	
	private MainApp mainApp;
	private CartController controller;
	
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
                    	showPersonDetails(newValue);
                    }
                });
        
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<Product> filteredData = new FilteredList<>(productData, p -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		search.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(product -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (product.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (Integer.toString(product.getPrice()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (product.getCategory().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches Category.
				}
				return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Product> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tableView.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tableView.setItems(sortedData);
    }
    
    public void setMainApp(MainApp mainApp, Customer customer) {
        this.mainApp = mainApp;
        this.addToList();
        customerName.setText(customer.getFirstName()+" "+customer.getLastName());
        loggedInCustomer = customer;
    }
    
	public mainViewController() {
		
	}
	
	public void addToList() {
		productData.add(new Product("Banana", 2, "Food"));
		productData.add(new Product("Apple", 1, "Food"));
		productData.add(new Product("IPhone", 1000, "Electronics"));
		productData.add(new Product("Bicycle", 700, "Sports"));
		productData.add(new Product("IPad", 500, "Electronics"));
	}
    
    private void showPersonDetails(Product Product){
        if (Product != null){
            // Fill labels with details from selected Person
            name.setText(Product.getName());
            int priceInt = Product.getPrice();
            String priceString = Integer.toString(priceInt);
            price.setText(priceString+".00€");
            category.setText(Product.getCategory());
        } else{
            // Person is null so set labels to be blank
            name.setText("");
            price.setText("no price");
            category.setText("");
        }
    }
    
    @FXML public void setLabelText() {
    	items.setText(Integer.toString(mainApp.getOrderList().size()));
    	totalSum.setText(Integer.toString(mainApp.getTotalSum())+".00€");
    }
    
    @FXML private void handleViewOrder() {
    	mainApp.showViewOrder();
    }
    
    
    @FXML private void handleViewCustomer() {
    	mainApp.showCustomer(loggedInCustomer);
    }
    
	@FXML private void handleAddToCart() {
		Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
		if (selectedProduct != null) {
			mainApp.addOrder(selectedProduct);
			setLabelText();
		} else {
			System.out.println("No item selected!");
		}

	 }
    
	private ObservableList<Product> productData = FXCollections.observableArrayList();
	
	public ObservableList<Product> getProductData() {
		return productData;
	}
}
