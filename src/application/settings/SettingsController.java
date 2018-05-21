package application.settings;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingsController implements Initializable {
	@FXML
	private AnchorPane rootPane;

    @FXML
    private JFXTextField nDaysWithoutFine;

    @FXML
    private JFXTextField fineperday;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	initDefaultValues();
		
	}

    @FXML
    void handleCancelButton(ActionEvent event) {
    	Stage stage = (Stage)rootPane.getScene().getWindow();
    	stage.close();
 
    }

    @FXML
    void handleSaveButton(ActionEvent event) {
    	Preferences pref = Preferences.getPreferences();
    	pref.setnDaysWithoutFine(Integer.parseInt(nDaysWithoutFine.getText()));
    	pref.setFineperday(Float.parseFloat(fineperday.getText()));
    	pref.setUsername(username.getText());
    	pref.setPassword(password.getText());
    	
    	Preferences.writePreferencesToFile(pref);
    }
    private void initDefaultValues() {
    	Preferences pref = Preferences.getPreferences();
    	nDaysWithoutFine.setText(String.valueOf(pref.getnDaysWithoutFine()));
    	fineperday.setText(String.valueOf(pref.getFineperday()));
    	username.setText(String.valueOf(pref.getUsername()));
    	password.setText(String.valueOf(pref.getPassword()));
    }

}
