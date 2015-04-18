package tableView;

import java.io.File;
import java.io.IOException;
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
	
	private Stage primaryStage, mainStage, customerStage;
	private BorderPane rootLayout;
	private AnchorPane mainView, viewOrder, customerPane, addCustomer, passwordPane;
	
	private LoginViewController loginViewController;
	private mainViewController mainViewController;
	private CartController cartController;
	private CustomerController customerController;
	private AddCustomerController addCustomerController;
	private RootLayoutController rootLayoutController;
	private PasswordController passwordController;
	
	File customerFile = new File("/Users/agentjs/Documents/Untitled.xml");

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Customer Login");
		this.primaryStage.setResizable(false);
		this.primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
		this.primaryStage.initStyle(StageStyle.TRANSPARENT);
		this.initialCustomer();
		this.loadCustomerFromFile(customerFile);
		//this.primaryStage.setFullScreen(true);
		
		// display the login window
		showLoginView();		
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public MainApp() {}
	
	public void showLoginView() {
        try {
        	// Load LoginView.fxml from 'view' package
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/LoginView.fxml"));
            AnchorPane loginView = (AnchorPane) loader.load();
            
            // Give the controller access to MainApp
            loginViewController = loader.getController();
            loginViewController.setApp(this);
            loginViewController.setPrimaryStage(primaryStage);
            
            // Set the scene
            Scene scene = new Scene(loginView);
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();
                    
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    public void callMainView(Customer customer) throws Exception{
        this.showRootLayout(customer);
        this.primaryStage.close();
    }
    
	public void showRootLayout(Customer customer) {
		try {
			// Load RootLayout.fxml from 'view' package
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/RootLayout.fxml"));
			rootLayout = loader.load();
			mainStage = new Stage();
			mainStage.setTitle("Shopping Client");
			mainStage.setMinWidth(800.00);
			mainStage.setMinHeight(500.00);
			Scene scene = new Scene(rootLayout);
			mainStage.setScene(scene);
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
	
	public void showViewOrder() {
		try{
	        // Load CartView.fxml from 'view' package
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("/view/CartView.fxml"));
	        viewOrder = (AnchorPane) loader.load();
	        
	        // Set the scene
	        Stage orderStage = new Stage();
	        orderStage.setTitle("Your Order");
	        orderStage.initModality(Modality.WINDOW_MODAL);
	        orderStage.initOwner(mainStage);
	        Scene scene = new Scene(viewOrder);
	        orderStage.setScene(scene);
	        
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
	
	private ObservableList<Product> orderList = FXCollections.observableArrayList();
	
	public void addOrder(Product product) {
		orderList.add(new Product(product.getName(), product.getPrice(), product.getCategory()));
	}

	public ObservableList<Product> getOrderList(){ 
		return orderList; 
	}
	
    public int getTotalSum() {
    	int price = 0;
    	for (int i=0; i<getOrderList().size();i++) {
    		price = price + getOrderList().get(i).getPrice();
    	}
    	return price;
    }

	public void updateUIMainView() {
		mainViewController.setLabelText();
	}
	
	public void showCustomer(Customer customer) {
		try{
	        // Load CustomerView.fxml from 'view' package
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("/view/CustomerView.fxml"));
	        customerPane = (AnchorPane) loader.load();
	        
	        // Set the scene
	        Stage customerStage = new Stage();
	        customerStage.setTitle("Your Profile");
	        customerStage.initModality(Modality.WINDOW_MODAL);
	        customerStage.initOwner(mainStage);
	        Scene scene = new Scene(customerPane);
	        customerStage.setScene(scene);
	        
	        // Give the input Person to the controller
	        customerController = loader.getController();
	        customerController.setCustomerStage(customerStage);
	        customerController.showCustomerDetails(customer);
	        customerController.setMainApp(this, customer);
	        
	        // Display the CustomerProfile view and wait for user to close it
	        customerStage.showAndWait();
	    } catch (IOException e){
	        e.printStackTrace();
	    }
	}
	
	private ObservableList<Customer> customerList = FXCollections.observableArrayList();
	
	public ObservableList<Customer> getCustomerList(){ 
		return customerList; 
	}
	
	public void initialCustomer() {
		/*
		customerList.add(new Customer("Jonas", "Schindler", "password", "Neue Straße", "Hildesheim", "jonas.schindler@gmail.com"));
		customerList.add(new Customer("Yang", "Cao", "1234", "XinCun Lu", "Shanghai", "yang@gmail.com"));
		*/
	}
	
	public void addCustomer() {
		try{
	        // Load AddCustomer.fxml from 'view' package
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("/view/AddCustomer.fxml"));
	        addCustomer = (AnchorPane) loader.load();
	        
	        // Set the scene
	        Stage addCustomerStage = new Stage();
	        addCustomerStage.setTitle("New Customer");
	        addCustomerStage.initModality(Modality.WINDOW_MODAL);
	        addCustomerStage.initOwner(primaryStage);
	        
	        Scene scene = new Scene(addCustomer);
	        addCustomerStage.setScene(scene);
	        
	        // Give the input Person to the controller
	        addCustomerController = loader.getController();
	        addCustomerController.setAddCustomerStage(addCustomerStage);
	        addCustomerController.setMainApp(this);
	        
	        // Display the CustomerProfile view and wait for user to close it
	        addCustomerStage.showAndWait();
	    } catch (IOException e){
	        e.printStackTrace();
	    }
	}
	
	public void updateLoginUI() {
		this.loginViewController.setApp(this);
	}
	
	public static void main(String[] args) {
		launch(args);
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
	        JAXBContext context = JAXBContext
	                .newInstance(CustomerListWrapper.class);
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
	
	public void showPassword(Customer customer) {
		try{
	        // Load Password.fxml from 'view' package
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("/view/Password.fxml"));
	        passwordPane = (AnchorPane) loader.load();
	        
	        // Set the scene
	        //Stage passwordStage = new Stage();
	        //passwordStage.setTitle("Change Password");
	        //passwordStage.initModality(Modality.WINDOW_MODAL);
	        //passwordStage.initOwner(passwordStage);
	        Scene scene = new Scene(passwordPane);
	        this.customerStage.setScene(scene);
	        
	        // Give the input Person to the controller
	        passwordController = loader.getController();
	        passwordController.setPasswordStage(customerStage);
	        passwordController.setMainApp(this, customer);
	        
	        // Display the Password view and wait for user to close it
	        customerStage.showAndWait();
	    } catch (IOException e){
	        e.printStackTrace();
	    }
	}
	
}
