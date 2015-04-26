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
		 //System.out.println("orderList size: "+mainApp.getOrderList().size());
		 if (mainApp.getOrderList().size() > 0) {
			 for ( int i=0;i<mainApp.getOrderList().size();i++) {
				 if (mainApp.getOrderList().get(i).getCustomer().equals(loggedInCustomer)) {
					 Order order = mainApp.getOrderList().get(i);
					 p.add(order.getDate()+": "+order.getTotalSum());
					 System.out.println("p: "+p);
					 //System.out.println("date and totalSum: "+order.getDate()+" : "+order.getTotalSum());
				 }
			 }
			 orderlistView.setItems(p);
		 }
	 }
	
	@FXML public void handleOk() {
		System.out.println("handleOk");
		mainApp.showCustomer(loggedInCustomer);
	}
	
	@FXML public void handleCancel() {
		System.out.println("handleCancel");
		mainApp.showCustomer(loggedInCustomer);
	}

}
