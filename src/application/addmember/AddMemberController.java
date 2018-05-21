package application.addmember;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.database.DatabaseHandler;
import application.listbook.list_bookController;
import application.listmember.ListMemberController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddMemberController implements Initializable {
	@FXML
	private AnchorPane rootPane; 
	DatabaseHandler handler;
    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField contactno;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancel;
    Boolean isEditable = Boolean.FALSE;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		handler = new DatabaseHandler();
	}


    @FXML
    void addMember(ActionEvent event) {
    	String mName = name.getText();
		String mID = id.getText();
		String mContact = contactno.getText();
		String mEmail = email.getText();
		
		Boolean  flag = mName.isEmpty()||mID.isEmpty()||mContact.isEmpty()||mEmail.isEmpty();
		if(flag) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Enter the all information ");
			alert.showAndWait();
			return;
		}
		if(isEditable) {
			handleEditOperation();
			return ; 
		}
		
		String qu = "INSERT INTO MEMBER VALUES("+"'"+mName+"',"+"'"+mID+"',"+"'"+mContact+"',"+"'"+mEmail+"'"+")";
		System.out.println(qu);
		if(handler.execAction(qu)) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Member Added Successfully");
			alert.showAndWait();
			return;
		}else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Failed To Add Member");
			alert.showAndWait();
			return;
		}
	}
    @FXML
    void cancel(ActionEvent event) {
    	Stage stage = (Stage) rootPane.getScene().getWindow();
    	stage.close();
    }
    public void inflateUIMember(ListMemberController.Member member) {
    	name.setText(member.getName());
    	id.setText(member.getId());
    	contactno.setText(member.getContactno());
    	email.setText(member.getEmail());
    	id.setEditable(false);
    	isEditable  = Boolean.TRUE;
    }
    private void handleEditOperation() {
    	ListMemberController.Member member = new ListMemberController.Member (name.getText(),id.getText(),contactno.getText(),email.getText());
    	if(handler.updateMember(member)) {
    		Alert a= new Alert(Alert.AlertType.INFORMATION);
    		a.setTitle("Success");
    		a.setHeaderText(null);
    		a.setContentText("Member Inforamtion Has Been Updated");
    		a.showAndWait();
    	}else {
    		Alert a= new Alert(Alert.AlertType.ERROR);
    		a.setTitle("Failed");
    		a.setHeaderText(null);
    		a.setContentText("Member Information  Updation Failed");
    		a.showAndWait();
    	}
    }


}
