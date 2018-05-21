package application.login;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.settings.Preferences;
import application.utilIcons.LibraryutilIcons;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {

	@FXML
    private Label lbl;
    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;
    
    Preferences pref;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		pref = Preferences.getPreferences();
	}

    @FXML
    void handleCancelButton(ActionEvent event) {
    	System.exit(0);
    }
    @FXML
    void handleLoginButton(ActionEvent event) {
    	lbl.setText("Library Assistant Login");
    	lbl.setStyle("-fx-background-color:#000000,-fx-text-fill:# 3e7bb7");
    	String uname = username.getText();
    	String pword = DigestUtils.shaHex(password.getText());
    	if(uname.equals(pref.getUsername())&&pword.equals(pref.getPassword())) {
    		closeStage();
    		loadMain();
    	}else {
    		lbl.setText("Invalid Credentials");
    		lbl.setStyle("-fx-background-color:#000000,-fx-text-fill:red") ;
    	}
    	

    }
    private void closeStage() {
    	((Stage)username.getScene().getWindow()).close();
    }
     void loadMain() {
try {
    	Parent root = FXMLLoader.load(getClass().getResource("/application/MainInterface/MainInterface.fxml"));
    	Stage stage = new Stage(StageStyle.DECORATED);
    	Scene scene = new Scene(root);
    	stage.setTitle("Library Assistant Management ");
    	stage.setScene(scene);
    	stage.show();
    	LibraryutilIcons.setStageIcon(stage);
	}catch(Exception e) {
		e.printStackTrace();
	}
    }

}
