package tableView;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

public class PasswordController {
	
	@FXML private TextField reenterNewPw, newPw, currentPw;
	@FXML private Label passwordError;
	
	private MainApp mainApp;
	private Stage customerStage;
	private Customer loggedInCustomer;
	
	
	public PasswordController() { }
	
    public void setMainApp(MainApp mainApp, Customer customer) {
        this.mainApp = mainApp;
        loggedInCustomer = customer;
    }
	
	 @FXML private void handleSaveChanges() {
		 if (currentPw.getText().equals(loggedInCustomer.getPassword()) && !currentPw.getText().isEmpty()) {
			 if (reenterNewPw.getText().equals(newPw.getText()) && !newPw.getText().isEmpty()) {
				 System.out.println("new pw: "+newPw.getText());
				 loggedInCustomer.setPassword(newPw.getText());
				 mainApp.saveCustomerToFile(mainApp.customerFile);
				 mainApp.showCustomer(loggedInCustomer);
			 } else {
				 passwordError.setText("Passwords don't match!");
			 }
		 } else {
			 passwordError.setText("Current password is incorrect!");
		 }
	 }
	
	 @FXML private void handleCancel(){
		 mainApp.showCustomer(loggedInCustomer);
	 }
}
