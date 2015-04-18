package tableView;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

public class PasswordController {
	
	@FXML TextField reenterNewPw, newPw, currentPw;
	
	private MainApp mainApp;
	private Stage customerStage;
	private Customer loggedInCustomer;
	
	public PasswordController() { }
	
    
	@FXML public void initialize() {
    }
	
    public void setMainApp(MainApp mainApp, Customer customer) {
        this.mainApp = mainApp;
        loggedInCustomer = customer;
    }
    
	public void setPasswordStage(Stage customerStage){
		 this.customerStage = customerStage;
	}
	
	 @FXML private void handleSaveChanges() {
		 if (currentPw.getText().equals(loggedInCustomer.getPassword())) {
			 if (reenterNewPw.getText().equals(newPw.getText())) {
				 loggedInCustomer.setPassword(newPw.getText());
				 customerStage.close();
			 } else {
				 System.out.println("Passwords don't match!");
			 }
		 } else {
			 System.out.println("Current password is incorrect!");
		 }
	 }
	
	 @FXML private void handleCancel(){
		 customerStage.close();
	 }
	 

}
