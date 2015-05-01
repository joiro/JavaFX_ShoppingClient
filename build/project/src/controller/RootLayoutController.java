package controller;

import javafx.stage.Stage;

public class RootLayoutController {
	
	private MainApp mainApp;
	private Stage mainStage;
	
    public void setMainStage(Stage mainStage) {
    	this.mainStage = mainStage;
    }
    
    public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
    }
    
    public void handleLogOut() {
    	mainApp.getBasketList().clear();
    	mainStage.close();
    	mainApp.showLoginView();
    }

	public void handleQuit() {
		System.exit(0);
	}
}
