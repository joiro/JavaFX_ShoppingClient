package tableView;

import java.io.IOException;
import java.text.SimpleDateFormat;

import model.Customer;
import model.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BasketController {
	
	@FXML private Label totalPrice, totalOrder, shippingFee, basketError;
	@FXML private Alert alert;
	@FXML private Button cancelOrder, editOrder, sendOrder, deleteOrder;
	@FXML private TableView<Product> order;
	@FXML private TableColumn<Product, String> orderProduct;
	@FXML private TableColumn<Product, String> orderPrice;
	@FXML private TableColumn<Product, String> orderCategory;
	@FXML private TableColumn<Product, String> orderQuantity;
	
	private MainApp mainApp;
	private Stage orderStage;
	private MainViewController controller;
	private double shipping = 2.55;
	
	private Customer loggedInCustomer;
	
	@FXML
	private void initialize() {
		orderProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		orderPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
		orderCategory.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
		orderQuantity.setCellValueFactory(new PropertyValueFactory<Product, String>("number"));
	}
	
	public void setEditStage(Stage orderStage){
		 this.orderStage = orderStage;
	}
	
	public BasketController() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/mainView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public void setMainApp(MainApp mainApp, Customer customer){
        this.mainApp = mainApp;
        // Add observable list data to the table
        ObservableList<Product> p = mainApp.getBasketList();
        order.setPlaceholder(new Label("The Basket is empty!"));
        loggedInCustomer = customer;
        order.setItems(p);
    }
    
    @FXML public void getPrice() {
    	if (mainApp.getBasketList().size() != 0) {
    		if (mainApp.getTotalPrice() >= 20.0) {
    			shipping = 0;
    		}
    		totalPrice.setText("£"+Double.toString(mainApp.getTotalPrice()));
    		shippingFee.setText("£"+shipping);
    		double finalPrice = mainApp.getTotalPrice() + shipping;
    		finalPrice = Math.round(finalPrice * 100);
    		finalPrice = finalPrice / 100;
			totalOrder.setText("£"+finalPrice);
    	} else {
    		totalPrice.setText("£0.00");
    	}
    }
        
	 @FXML private void handleCancel(){
		 orderStage.close();
		 mainApp.updateUIMainView();
	 }
	 
	 @FXML private void handleDelete(){
	        int selectedIndex = order.getSelectionModel().getSelectedIndex();
	        if (selectedIndex >= 0){
	            order.getItems().remove(selectedIndex);
	            this.getPrice();
	        }
	 }
	 
	 @FXML private void handleSend(){
		 java.util.Date date= new java.util.Date();
		 if (mainApp.getBasketList().size() != 0){
			 mainApp.saveOrder(loggedInCustomer, mainApp.getTotalPrice(), date.toString());
			 mainApp.getBasketList().clear();
			 createAlert();
		 } else {
			 basketError.setText("the basket is empty!");
		 }
	 }
	 
	 private void createAlert() {
		 Alert alert = new Alert(AlertType.INFORMATION);
		 alert.setTitle("Confirmation");
		 alert.setHeaderText(null);
		 alert.setContentText("Your order has been sent!");
		 alert.showAndWait();
	 }
}