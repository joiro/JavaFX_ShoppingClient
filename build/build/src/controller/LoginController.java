package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import model.Customer;

public class LoginController {

    @FXML private Button loginButton;
    @FXML private PasswordField passwordNode;
    @FXML private Label information;
    @FXML private ComboBox<Customer> customerComboBox;
    
    private MainApp MainApp;
    private Stage primaryStage;
    
    @FXML
    public void initialize() {
    }
    
    @FXML public void loginAction (ActionEvent event) throws Exception {
    	
    	// Stores the entered text from the passwordField
    	String password = passwordNode.getText();
    	
    	// Stores the selected customer from the comboBox
    	Customer customer = (Customer) customerComboBox.getSelectionModel().getSelectedItem();
    	
    	// If no customer is selected, a notification message appears
    	if (customer != null) {
    		
    		// If no password is entered or if it is incorret, a notification message appears
        	if (customer.getPassword().equals(password)) {
        		MainApp.callMainView(customer);
        	}
            else {
                information.setText("Incorrect password!");
            }
    	} else {
    		information.setText("No customer selected!");
    	}

    }
    // Called when the Cancel button is pressed. It closes the stage and the program
    @FXML public void handleCancel() {
    	primaryStage.close();
    }
 // Called when the 'Create New Account' label is pressed. 'Add Customer' view gets opened on the same stage.
    @FXML public void createNewAccount() {
    	MainApp.addCustomer(null);
    }
    // Creates a reference to the primaryStage
    public void setPrimaryStage(Stage primaryStage) {
    	this.primaryStage = primaryStage;
    }
    // Creates a reference to the MainApp, receives the customerList and populates the comboBox with the customers
    public void setApp(MainApp mainApp) {
    	this.MainApp = mainApp;
    	ObservableList<Customer> p = MainApp.getCustomerList();
    	customerComboBox.setItems(p);
    }
}
