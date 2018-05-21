package application.settings;

import application.database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SettingsLoader extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Settings.fxml")); 
		Scene scene =new Scene(root);
		primaryStage.setTitle("Settings");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//DatabaseHandler handler = DatabaseHandler.getInstance(); 
				DatabaseHandler.getInstance(); 
				}
		}).start();
	//	Preferences.initConfig();
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
 