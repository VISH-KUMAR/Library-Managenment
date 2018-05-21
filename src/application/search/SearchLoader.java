package application.search;

import application.utilIcons.LibraryutilIcons;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SearchLoader extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/application/search/Searching.fxml")); 
		Scene scene =new Scene(root);
		primaryStage.setTitle("Search");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		LibraryutilIcons.setStageIcon(primaryStage);
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
