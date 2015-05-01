package controller;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import model.Customer;
import model.CustomerListWrapper;
import model.Order;
import model.Product;

public class MainApp extends Application {
	
	private Stage primaryStage, mainStage;
	private BorderPane rootLayout;
	private AnchorPane mainView, viewBasket, customerPane, addCustomer, loginView, passwordPane, orderPane;
	
	private LoginController loginViewController;
	private MainViewController mainViewController;
	private BasketController cartController;
	private CustomerController customerController;
	private AddCustomerController addCustomerController;
	private RootLayoutController rootLayoutController;
	private PasswordController passwordController;
	private OrderController orderController;
	
	File customerFile = new File("resources/customers.xml");
	
	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setResizable(false);
		this.primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
		this.loadFile(customerFile);
		
		// display the login window
		showLoginView();		
	}
	
	public Stage getPrimaryStage() { return primaryStage; }
	
	public MainApp() {}
	
	// loads stage and scene for the customer login
	public void showLoginView() {
        try {
        	// Load LoginView.fxml from 'view' package
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/LoginView.fxml"));
            loginView = (AnchorPane) loader.load();
            
            // Give the controller access to MainApp and primaryStage
            loginViewController = loader.getController();
            loginViewController.setApp(this);
            loginViewController.setPrimaryStage(primaryStage);
            
            // Set the scene
            Scene loginScene = new Scene(loginView);
            primaryStage.setTitle("Customer Login");
            primaryStage.setScene(loginScene);
            primaryStage.show();
                    
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	// handles change from loginView to mainView
    public void callMainView(Customer customer) throws Exception{
        this.showRootLayout(customer);
        this.primaryStage.close();
    }
    
    // adds a menubar to the main view
	public void showRootLayout(Customer customer) {
		try {
			// Load RootLayout.fxml from 'view' package
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/RootLayout.fxml"));
			rootLayout = loader.load();
			mainStage = new Stage();
			mainStage.setTitle("Shopping Client");
			mainStage.getIcons().add(new Image("file:resources/images/icon.png"));
			mainStage.setMinWidth(1000.00);
			mainStage.setMinHeight(600.00);
			
			// Set the scene
			Scene rootScene = new Scene(rootLayout);
			mainStage.setScene(rootScene);
			mainStage.show();
			
            // Give the controller access to MainApp and mainStage
            rootLayoutController = loader.getController();
            rootLayoutController.setMainApp(this);
            rootLayoutController.setMainStage(mainStage);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// calls the MainView to be loaded in the mainStage
		showMainView(customer);
	}

	// shows the main window with product catalogue
	public void showMainView(Customer customer) {
		try {
			// Load MainView.fxml from 'view' package
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/MainView.fxml"));
			mainView = (AnchorPane) loader.load();
			rootLayout.setCenter(mainView);
			
			// Give the controller access to the main app and 
	        // sends information about the logged-in customer
	        mainViewController = loader.getController();
	        mainViewController.setMainApp(this, customer);
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

// BASKET HANDLING
	
	// empty basket list
	private ObservableList<Product> basketList = FXCollections.observableArrayList();
	
	// adds a product to the basket list, including it's selected quantity
	public void addBasket(Product product, int quantity) {
		basketList.add(new Product(product.getName(), product.getPrice()*quantity, null, 
				product.getCategory(), product.getImage(), quantity));
	}

	// gives access to the basketList
	public ObservableList<Product> getBasketList(){ return basketList; }
	
	// shows the actual basket view
	public void showViewBasket(Customer customer) {
		try{
	        // Load CartView.fxml from 'view' package
	        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/BasketView.fxml"));
	        viewBasket = (AnchorPane) loader.load();
	        
	        // Set the scene
	        Stage basketStage = new Stage();
	        basketStage.setTitle("Your Basket");
	        basketStage.initOwner(mainStage);
	        Scene orderScene = new Scene(viewBasket);
	        basketStage.setScene(orderScene);
	        
	        // Give the controller access to the MainApp and basketStage and 
	        // sends information about the logged-in customer
	        cartController = loader.getController();
	        cartController.setEditStage(basketStage);
	        cartController.setMainApp(this, customer);
	        cartController.getPrice();
	        
	        // Display the CartView view and wait for user to close it
	        basketStage.showAndWait();
	    } catch (IOException e){
	        e.printStackTrace();
	    }
	}
	
	// calculates the total price of the order and rounds the double to 2 decimals
    public double getTotalPrice() {
    	double price = 0;
    	for (int i=0; i<getBasketList().size();i++) {
    		price = price + getBasketList().get(i).getPrice();
    	}
    	price = Math.round(price * 100);
    	price = price / 100;
    	return price;
    }

    // updates the totalPrice label in Mainview / BasketView
	public void updateUIMainView() {
		mainViewController.setCustomerLabel();
		mainViewController.setLabelText();
	}
	

	
// ORDER HANDLING
	
	// empty basket list
	private ObservableList<Order> orderList = FXCollections.observableArrayList();
		
	// creates a order for the customer and saves it to the orderList
	public void saveOrder(Customer customer, double totalSum, String date) {
		String sum = Double.toString(totalSum);
		orderList.add(new Order(customer, sum, date));
	}
	
	public ObservableList<Order> getOrderList(){ return orderList; }
	
	public void showOrder(Customer customer) {
		try{
	        // Load CustomerView.fxml from 'view' package
	        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/OrderView.fxml"));
	        orderPane = (AnchorPane) loader.load();
	        
	        // create and new scene and set it to the primaryStage
	        Scene orderScene = new Scene(orderPane);
	        primaryStage.setTitle("My Orders");
	        primaryStage.setScene(orderScene);
	        
	        // Give the input Person to the controller
	        orderController = loader.getController();
	        orderController.setMainApp(this, customer);
	        
	        // Display the CustomerProfile view and wait for user to close it
	        primaryStage.show();
	    } catch (IOException e){
	        e.printStackTrace();
	    }
	}
	
	// shows the password edit window
	public void showPassword(Customer customer) {
		try{
	        // Load Password.fxml from 'view' package
	        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/PasswordView.fxml"));
	        passwordPane = (AnchorPane) loader.load();
	        
	        // create and new scene and set it to the primaryStage
	        Scene passwordScene = new Scene(passwordPane);
	        primaryStage.setTitle("Change Password");
	        primaryStage.setScene(passwordScene);
	        
	        // Give the input Person to the controller
	        passwordController = loader.getController();
	        passwordController.setMainApp(this, customer);
	        
	        // Display the Password view and wait for user to close it
	        primaryStage.show();
	    } catch (IOException e){
	        e.printStackTrace();
	    }
	}
	
	public void updateLoginUI() {
		this.loginViewController.setApp(this);
	}

	
// CUSTOMER HANDLING
	
	private ObservableList<Customer> customerList = FXCollections.observableArrayList();
	
	public ObservableList<Customer> getCustomerList(){ return customerList; }
	
	// shows the customer profile
	public void showCustomer(Customer customer) {
		try{
	        // Load CustomerView.fxml from 'view' package
	        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/CustomerView.fxml"));
	        customerPane = (AnchorPane) loader.load();
	        
	        Scene customerScene = new Scene(customerPane);
	        //primaryStage.initOwner(mainStage);
	        //primaryStage.initModality(Modality.WINDOW_MODAL);
	        primaryStage.setTitle("My Profile");
	        primaryStage.setScene(customerScene);
	        
	        // Give the input Person to the controller
	        customerController = loader.getController();
	        customerController.setPrimaryStage(primaryStage);
	        customerController.showCustomerDetails(customer);
	        customerController.setMainApp(this, customer);
	        
	        // Display the CustomerProfile view and wait for user to close it
	        primaryStage.show();
	    } catch (IOException e){
	        e.printStackTrace();
	    }
	}
	
	public void addCustomer(Customer customer) {
		try{
	        // Load AddCustomer.fxml from 'view' package
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/AddCustomerView.fxml"));
	        addCustomer = (AnchorPane) loader.load();
	        
	        // Create new scene and set to primaryStage
	        Scene addCustomerScene = new Scene(addCustomer);
	        primaryStage.setTitle("New Customer");
	        primaryStage.setScene(addCustomerScene);
	        
	        // Give the input Person to the controller
	        addCustomerController = loader.getController();
	        addCustomerController.setMainApp(this, customer);
	        
	        // Display the CustomerProfile view and wait for user to close it
	        primaryStage.show();
	    } catch (IOException e){
	        e.printStackTrace();
	    }
	}	
	
	// called when 'delete profile' in addCustomerView is called
	// removes the customer entry from the customerList, saves the file, closes the mainStage and returns to loginView
	public void removeCustomer(Customer customer) {
		customerList.remove(customer);
		this.saveToFile(customerFile);
		mainStage.close();
		this.showLoginView();
	}

	
// FILE HANDLING
	
	// the file handling logic is inspired by the code used in the tutorial on the 
	// following page: http://code.makery.ch/library/javafx-8-tutorial/part5/
	
	public void loadFile(File file) {
	    try {
	        JAXBContext context = JAXBContext.newInstance(CustomerListWrapper.class);
	        Unmarshaller unMarshall = context.createUnmarshaller();

	        // load xml from file
	        CustomerListWrapper wrapper = (CustomerListWrapper) unMarshall.unmarshal(file);

	        // clear the actual list and add all entries from the file
	        customerList.clear();
	        customerList.addAll(wrapper.getCustomer());

	    } catch (Exception e) {
	    	// display an alert when the file load is corrupt
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("File not loaded");
	        alert.setContentText("Could not load file:\n" + file.getPath());
	        alert.showAndWait();
	    }
	}
	
	public void saveToFile(File file) {
	    try {
	        // Wrapping up the product data
    		JAXBContext context = JAXBContext.newInstance(CustomerListWrapper.class);
    		Marshaller marshall = context.createMarshaller();
    		marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    		CustomerListWrapper wrapper = new CustomerListWrapper();
    		wrapper.setCustomer(customerList);
    		
    		// save the xml in the customer file
    		marshall.marshal(wrapper, file);

	    } catch (Exception e) {
	    	// display an alert when the file save is corrupt
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("File not saved");
	        alert.setContentText("Could not save file:\n" + file.getPath());
	        alert.showAndWait();
	    }
	}
}