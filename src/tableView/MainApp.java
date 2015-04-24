package tableView;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import model.Customer;
import model.CustomerListWrapper;
import model.Product;


public class MainApp extends Application {
	
	private Stage primaryStage, mainStage, customerStage, passwordStage;
	private Scene loginScene, rootScene, orderScene, customerScene, passwordScene, addCustomerScene;
	private BorderPane rootLayout;
	private AnchorPane mainView, viewOrder, customerPane, addCustomer, loginView, passwordPane;
	
	private LoginViewController loginViewController;
	private mainViewController mainViewController;
	private CartController cartController;
	private CustomerController customerController;
	private AddCustomerController addCustomerController;
	private RootLayoutController rootLayoutController;
	private PasswordController passwordController;
	
	File customerFile = new File("/Users/agentjs/Documents/Untitled.xml");
	File orderFile = new File("/Users/agentjs/Documents/orders");
	
	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setResizable(false);
		this.primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
		this.loadCustomerFromFile(customerFile);
		
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
            
            // Give the controller access to MainApp
            loginViewController = loader.getController();
            loginViewController.setApp(this);
            loginViewController.setPrimaryStage(primaryStage);
            
            // Set the scene
            Scene loginScene = new Scene(loginView);
            loginScene.setFill(Color.TRANSPARENT);
            primaryStage.setTitle("Customer Login");
            primaryStage.setScene(loginScene);
            primaryStage.show();
                    
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    public void callMainView(Customer customer) throws Exception{
        this.showRootLayout(customer);
		System.out.println("callMainView");
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
			mainStage.setMinWidth(1000.00);
			mainStage.setMinHeight(600.00);
			Scene rootScene = new Scene(rootLayout);
			mainStage.setScene(rootScene);
			mainStage.show();
			
            // Give the controller access to MainApp
            rootLayoutController = loader.getController();
            rootLayoutController.setMainApp(this);
            rootLayoutController.setMainStage(mainStage);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showProductOverview(customer);
	}

	// shows the main window with product catalogue
	public void showProductOverview(Customer customer) {
		try {
			// Load MainView.fxml from 'view' package
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/MainView.fxml"));
			mainView = (AnchorPane) loader.load();
			rootLayout.setCenter(mainView);
			
			// Give the controller access to the main app
	        mainViewController = loader.getController();
	        mainViewController.setMainApp(this, customer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	// shows the actual order view
	public void showViewOrder() {
		try{
	        // Load CartView.fxml from 'view' package
	        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/CartView.fxml"));
	        viewOrder = (AnchorPane) loader.load();
	        
	        // Set the scene
	        Stage orderStage = new Stage();
	        orderStage.setTitle("Your Order");
	        orderStage.initModality(Modality.WINDOW_MODAL);
	        orderStage.initOwner(mainStage);
	        Scene orderScene = new Scene(viewOrder);
	        orderStage.setScene(orderScene);
	        
	        // Give the input Person to the controller
	        cartController = loader.getController();
	        cartController.setEditStage(orderStage);
	        cartController.setMainApp(this);
	        cartController.getPrice();
	        
	        // Display the CartView view and wait for user to close it
	        orderStage.showAndWait();
	    } catch (IOException e){
	        e.printStackTrace();
	    }
	}
	
	// empty order list
	private ObservableList<Product> orderList = FXCollections.observableArrayList();
	
	// adds a product to the order list
	public void addOrder(Product product, int quantity) {
		orderList.add(new Product(product.getName(), product.getPrice(), product.getCategory(), product.getImage()));
	}
	
	public void removeCustomer(Customer customer) {
		customerList.remove(customer);
		this.saveCustomerToFile(customerFile);
		mainStage.close();
		this.showLoginView();
	}

	public ObservableList<Product> getOrderList(){ return orderList; }
	
	// calculates the total price of the order
    public double getTotalPrice() {
    	double price = 0;
    	for (int i=0; i<getOrderList().size();i++) {
    		price = price + getOrderList().get(i).getPrice();
    	}
    	price = Math.round(price * 100);
    	price = price / 100;
    	return price;
    }

	public void updateUIMainView() {
		mainViewController.setLabelText();
	}
	
	public void showCustomer(Customer customer) {
		try{
	        // Load CustomerView.fxml from 'view' package
	        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/CustomerView.fxml"));
	        customerPane = (AnchorPane) loader.load();
	        
	        Scene customerScene = new Scene(customerPane);
	        //primaryStage.initOwner(mainStage);
	        //primaryStage.initModality(Modality.WINDOW_MODAL);
	        primaryStage.setTitle("Your Profile");
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
	
	public void showPassword(Customer customer) {
		try{
	        // Load Password.fxml from 'view' package
	        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/Password.fxml"));
	        passwordPane = (AnchorPane) loader.load();
	        
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
	
	public File getCustomerFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}
	
	public void setCustomerFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());
	    } else {
	        prefs.remove("filePath");
	    }
	}
	
	
	public void loadCustomerFromFile(File file) {
	    try {
	        JAXBContext context = JAXBContext.newInstance(CustomerListWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // Reading XML from the file and unmarshalling.
	        CustomerListWrapper wrapper = (CustomerListWrapper) um.unmarshal(file);

	        customerList.clear();
	        customerList.addAll(wrapper.getCustomer());

	        // Save the file path to the registry.
	        //setPersonFilePath(file);

	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load data");
	        alert.setContentText("Could not load data from file:\n" + file.getPath());

	        alert.showAndWait();
	    }
	}
	
	public void saveCustomerToFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(CustomerListWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our person data.
	        CustomerListWrapper wrapper = new CustomerListWrapper();
	        wrapper.setCustomer(customerList);

	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, file);

	        // Save the file path to the registry.
	        //setPersonFilePath(file);
	    } catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + file.getPath());

	        alert.showAndWait();
	    }
	}
	
	private ObservableList<Customer> customerList = FXCollections.observableArrayList();
	
	public ObservableList<Customer> getCustomerList(){ return customerList; }
	
	public void addCustomer(Customer customer) {
		try{
	        // Load AddCustomer.fxml from 'view' package
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/AddCustomer.fxml"));
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
}