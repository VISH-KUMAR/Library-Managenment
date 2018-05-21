package application.alertMaker;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertMaker {
	public AlertMaker(AlertType alert,String title,String content) {
		Alert a = new Alert(null);
		a.setAlertType(alert);
		a.setTitle(title);
		a.setHeaderText(null);
		a.setContentText(content);
		a.showAndWait();
	}

}
