package tableView;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import model.Customer;

public class LoginViewController {

    @FXML private Button loginButton;
    @FXML private PasswordField passwordNode;
    @FXML private Label information;
    @FXML private ComboBox<Customer> customerComboBox;
    
    private MainApp MainApp;
    private Stage primaryStage;
    
    @FXML
    public void initialize() {
    }
    
    public void loginAction (ActionEvent event) throws Exception {
    	String password = passwordNode.getText();
    	
    	Customer customer = (Customer) customerComboBox.getSelectionModel().getSelectedItem();
    	if (customer.getPassword().equals(password)) {
    		MainApp.callMainView(customer);
    	}
        else {
            information.setText("Incorrect password!");
        }
        
    }
    
    public void handleCancel() {
    	primaryStage.close();
    }
    
    @FXML public void createNewAccount() {
    	MainApp.addCustomer();
    }
    
    public void setPrimaryStage(Stage primaryStage) {
    	this.primaryStage = primaryStage;
    }
    
    public void setApp(MainApp mainApp) {
    	this.MainApp = mainApp;
    	ObservableList<Customer> p = MainApp.getCustomerList();
    	customerComboBox.setItems(p);
    }
}
