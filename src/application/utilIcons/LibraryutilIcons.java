package application.utilIcons;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LibraryutilIcons {
	 private static final String IMAGE_LOC="/application/images/6.png";
	 
	 public static void setStageIcon(Stage stage) {
		 stage.getIcons().add(new Image(IMAGE_LOC));
	 }

}
