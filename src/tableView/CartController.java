package tableView;

import java.io.IOException;

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

public class CartController {
	
	@FXML private Label totalPrice;
	@FXML private Alert alert;
	@FXML private Button cancelOrder, editOrder, sendOrder, deleteOrder;
	@FXML private TableView<Product> order;
	@FXML private TableColumn<Product, String> orderProduct;
	@FXML private TableColumn<Product, String> orderPrice;
	@FXML private TableColumn<Product, String> orderCategory;
	@FXML private TableColumn<Product, String> orderQuantity;
	
	private MainApp mainApp;
	private Stage orderStage;
	private mainViewController controller;
	
	@FXML
	private void initialize() {
		orderProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		orderPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
		orderCategory.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));

	}
	
	public void setEditStage(Stage orderStage){
		 this.orderStage = orderStage;
	}
	
	public CartController() {
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/mainView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
        // Add observable list data to the table
        ObservableList<Product> p = mainApp.getOrderList();
        order.setPlaceholder(new Label("The Basket is empty!"));
        order.setItems(p);
    }
    
    @FXML public void getPrice() {
    	totalPrice.setText("£"+Double.toString(mainApp.getTotalSum()));
    }
        
	 @FXML private void handleCancel(){
		 orderStage.close();
		 mainApp.updateUIMainView();
	 }
	 
	 @FXML private void handleEdit(){
		 System.out.println("handleEdit");
	 }
	 
	 @FXML private void handleDelete(){
	        int selectedIndex = order.getSelectionModel().getSelectedIndex();
	        if (selectedIndex >= 0){
	            order.getItems().remove(selectedIndex);
	            totalPrice.setText("£"+Double.toString(mainApp.getTotalSum()));
	        }
	 }
	 
	 @FXML private void handleSend(){
		 System.out.println("handleSend");
		 createAlert();
	 }
	 
	 private void createAlert() {
		 Alert alert = new Alert(AlertType.INFORMATION);
		 alert.setTitle("Confirmation");
		 alert.setHeaderText(null);
		 alert.setContentText("Your order has been sent!");

		 alert.showAndWait();
	 }

}
