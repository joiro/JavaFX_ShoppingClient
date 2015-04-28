package tableView;

import model.Customer;
import model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class OrderController {
	
	@FXML private ListView<String> orderlistView;
	@FXML private ObservableList<String> p = FXCollections.observableArrayList();
	
	private Stage primaryStage;
	private MainApp mainApp;
	private Customer loggedInCustomer;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setMainApp(MainApp mainApp, Customer customer) {
		this.mainApp = mainApp;
		this.loggedInCustomer = customer;
		this.viewOrders();
		
	}
	
	private void viewOrders() {
		//mainApp.loadFile(mainApp.orderFile);
		 if (mainApp.getOrderList().size() > 0) {
			 for ( int i=0;i<mainApp.getOrderList().size();i++) {
				 if (mainApp.getOrderList().get(i).getCustomer().equals(loggedInCustomer)) {
					 System.out.println("customers match");
					 Order order = mainApp.getOrderList().get(i);
					 //System.out.println("order: "+mainApp.getOrderList().get(i).getDate());
					 p.add(order.getDate()+"  -----  Total Price: £"+order.getTotalSum());
				 }
			 }
			 orderlistView.setItems(p);
		 }
	 }
	
	@FXML public void handleOk() {
		mainApp.showCustomer(loggedInCustomer);
	}
	
	@FXML public void handleCancel() {
		mainApp.showCustomer(loggedInCustomer);
	}

}
