package tableView;

import model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCustomerController {
	
	@FXML private TextField firstNameField, lastNameField, emailAddressField, passwordField, streetField, cityField;
	
	private MainApp mainApp;
	private Customer customer;
	
	public AddCustomerController() { }
	
	@FXML public void initialize() {
    }
	
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	@FXML public void handleOk() {
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

	
    public void handleCancel() {
    	mainApp.showLoginView();
    }
}
