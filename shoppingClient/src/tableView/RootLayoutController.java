package tableView;

import javafx.stage.Stage;

public class RootLayoutController {
	
	private MainApp MainApp;
	private Stage mainStage;
	
    public void setMainStage(Stage mainStage) {
    	this.mainStage = mainStage;
    }
    
    public void setMainApp(MainApp mainApp) {
    	this.MainApp = mainApp;
    }
    
    public void handleLogOut() {
    	mainStage.close();
    	MainApp.showLoginView();
    }

	public void handleQuit() {
		System.exit(0);
		//mainStage.close();
	}
}
