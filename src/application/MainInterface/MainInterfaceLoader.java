package application.MainInterface;

import application.database.DatabaseHandler;
import application.utilIcons.LibraryutilIcons;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainInterfaceLoader extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/application/login/Login.fxml")); 
		Scene scene =new Scene(root);
		primaryStage.setTitle("Library Management");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		LibraryutilIcons.setStageIcon(primaryStage);
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//DatabaseHandler handler = DatabaseHandler.getInstance(); 
				DatabaseHandler.getInstance(); 
				}
		}).start();
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
