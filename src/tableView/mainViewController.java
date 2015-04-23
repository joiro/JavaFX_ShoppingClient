package tableView;


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
	@FXML private ImageView imageView;
    @FXML private ComboBox comboQuantity;
	
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
                    	showProductDetails(newValue);
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
				} else if (Double.toString(product.getPrice()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
        this.populateCombo();
        tableView.getSelectionModel().select(0);

    }
    
	public mainViewController() { }
	
	public void addToList() {
		productData.add(new Product("Banana", 0.39, "Food", "http://assets.eatingwell.com/sites/default/files/imagecache/310_square/bananas_1.jpg"));
		productData.add(new Product("Apple", 0.19, "Food", "https://www.handytarifevergleich.de/media/uploads/sites/5/2014/07/apple.jpg"));
		productData.add(new Product("Milk", 1.69, "Food", "http://www.odec.ca/projects/2010/giesxb2/images/j0441751.png"));
		productData.add(new Product("Cheddar Cheese", 2.79, "Food", "http://ecx.images-amazon.com/images/I/51A-P9ReGSL._SX466_.jpg"));
		productData.add(new Product("Butter", 1.09, "Food", "http://www.allmystery.de/i/t74d7de_fette_oele_butter_2.jpg"));
		productData.add(new Product("IPhone 6 16GB grey", 549.99, "Electronics", "http://ecx.images-amazon.com/images/I/51eclIdmTuL.jpg"));
		productData.add(new Product("Panasonic 42' LCD Tv", 499.99, "Electronics", "http://ecx.images-amazon.com/images/I/71o-i0JC1GL._SL1500_.jpg"));
		productData.add(new Product("Apple IPad Air 16GB white", 399.99, "Electronics", "http://ecx.images-amazon.com/images/I/51b-LjnkFJL._SY355_.jpg"));
		productData.add(new Product("Mountainbike 'Trackmaster'", 699.99, "Sports", "http://ecx.images-amazon.com/images/I/61PdltjOCfL._SX522_.jpg"));
	}
    
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
        } else{
            // Product is null so set labels to be blank
            name.setText("");
            price.setText("no price");
            category.setText("");
        }
    }
    
    @FXML public void setLabelText() {
    	items.setText(Integer.toString(mainApp.getOrderList().size()));
    	if (mainApp.getOrderList().size() != 0) {
    		totalSum.setText("£"+Double.toString(mainApp.getTotalPrice()));
    	} else {
    		totalSum.setText("£0.00");
    	}
    }
    
    @FXML private void handleViewOrder() { mainApp.showViewOrder(); }
    
    @FXML private void handleViewCustomer() { mainApp.showCustomer(loggedInCustomer); }
    
    @FXML private void handleViewPassword() {
    	System.out.println("viewPassword");
    }
    
	@FXML private void handleAddToCart() {
        //Image img = new Image("https://www.handytarifevergleich.de/media/uploads/sites/5/2014/07/apple.jpg");
        //System.out.println("dir: "+System.getProperty("user.dir"));
        //imageView.setImage(img);
		Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
		if (selectedProduct != null) {
			mainApp.addOrder(selectedProduct);
			setLabelText();
		} else {
			System.out.println("No item selected!");
		}
	 }
	
	private void populateCombo() {
		comboQuantity.getItems().addAll("0","1","2","3","4","5");
		
	}
	
	private void Array() {
		
	}
    
	private ObservableList<Product> productData = FXCollections.observableArrayList();
	
	public ObservableList<Product> getProductData() { return productData; }
}
