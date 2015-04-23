package tableView;

import model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CustomerController {
	
	@FXML private Label firstName, emailAddress, address;

	private Customer loggedInCustomer;
	private MainApp mainApp;
	private Stage primaryStage;
	
	public CustomerController() { }
	
	@FXML public void initialize() { }
	
    public void setMainApp(MainApp mainApp, Customer customer) {
        this.mainApp = mainApp;
        loggedInCustomer = customer;
    }
    
	public void setPrimaryStage(Stage primaryStage){
		 this.primaryStage = primaryStage;
	}
	
	public void showCustomerDetails(Customer customer) {
		firstName.setText(customer.getFirstName()+" "+customer.getLastName());
		emailAddress.setText(customer.getEmailAddress());
		address.setText(customer.getStreet()+" "+customer.getCity());
	}
	
	 @FXML private void handleOk() {
		 primaryStage.close();
	 }
	
	 @FXML private void handleCancel(){
		 primaryStage.close();
	 }
	 
	 @FXML private void handleEditDetails() {
		 System.out.println("edit Details");
		 mainApp.addCustomer(loggedInCustomer);
	 }
	 
	 @FXML private void handleEditPassword() {
		 mainApp.showPassword(loggedInCustomer);
	 }
	 
	 @FXML private void handleViewOrders() {
		 System.out.println("view Orders");
	 }
	 

	
}
