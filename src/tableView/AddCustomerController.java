package tableView;

import java.util.Optional;

import model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddCustomerController {
	
	@FXML private TextField firstNameField, lastNameField, emailAddressField, passwordField, streetField, cityField;
	@FXML private Label passwordLabel;
	@FXML private Button deleteProfile;
	@FXML private Label errorMessage;
	
	private MainApp mainApp;
	private Customer customer;
	private Customer loggedInCustomer;
	
	public AddCustomerController() { }

    public void setMainApp(MainApp mainApp, Customer customer) {
        this.mainApp = mainApp;
        loggedInCustomer = customer;
        // show / hide nodes if a customer is logged in
        if (loggedInCustomer != null) {
        	deleteProfile.setVisible(true);
        	passwordField.setVisible(false);
        	passwordLabel.setVisible(false);
        	// fill the textFields
        	this.fillCustomerDetails();
        }
    }
    // fill the textField with customer information if a customer was logged in
    public void fillCustomerDetails() {
    	firstNameField.setText(loggedInCustomer.getFirstName());
    	lastNameField.setText(loggedInCustomer.getLastName());
    	emailAddressField.setText(loggedInCustomer.getEmailAddress());
    	streetField.setText(loggedInCustomer.getStreet());
    	cityField.setText(loggedInCustomer.getCity());
    }
	
    // called when Ok button is clicked
	@FXML public void handleOk() {
		// checks if firstname or password fields are empty
		if (!firstNameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
			// checks if password fiel is at least 4 characters
			if (!(passwordField.getText().length() < 4)) {
				// checks if it is an existing customer...
				if (loggedInCustomer != null) {
					loggedInCustomer.setFirstName(firstNameField.getText());
					loggedInCustomer.setLastName(lastNameField.getText());
					loggedInCustomer.setEmailAddress(emailAddressField.getText());
					loggedInCustomer.setStreet(streetField.getText());
					loggedInCustomer.setCity(cityField.getText());
					mainApp.saveCustomerToFile(mainApp.customerFile);
					mainApp.showCustomer(loggedInCustomer);
				// ... or a new customer
				} else {
					Customer customer = new Customer();
					customer.setFirstName(firstNameField.getText());
					customer.setLastName(lastNameField.getText());
					customer.setEmailAddress(emailAddressField.getText());
					customer.setPassword(passwordField.getText());
					customer.setStreet(streetField.getText());
					customer.setCity(cityField.getText());
					mainApp.getCustomerList().add(customer);
					mainApp.saveCustomerToFile(mainApp.customerFile);
					mainApp.updateLoginUI();	 
					mainApp.showLoginView();
				}
			} else {
				errorMessage.setText("Password must be at least 4 characters");
			}
		} else {
			errorMessage.setText("FirstName or Password cannot be empty!");
		}
	}
	
    public void handleCancel() {
    	if (loggedInCustomer != null) {
    		mainApp.showCustomer(loggedInCustomer);
    	} else {
    		mainApp.showLoginView();
    	}
    }
    
    public void handleDeleteProfile() {
    	System.out.println("handleDeleteProfile");
    	deleteConfirmationAlert();
    }
    
	 private void deleteConfirmationAlert() {
		 Alert alert = new Alert(AlertType.CONFIRMATION);
		 alert.setTitle("delete Profile");
		 alert.setHeaderText(null);
		 alert.setContentText("Are you sure you want to delete this profile?\nThis choice is irreversible!");
		 Optional<ButtonType> result = alert.showAndWait();
		 if (result.get() == ButtonType.OK) {
			 mainApp.removeCustomer(loggedInCustomer);
		 } else {
			 alert.close();
		 }
	 }
}
