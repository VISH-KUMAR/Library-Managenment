package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.database.DatabaseHandler;
import application.listbook.list_bookController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController implements Initializable {
		@FXML
	    private JFXTextField title;

	    @FXML
	    private JFXTextField id;

	    @FXML
	    private JFXTextField author;

	    @FXML
	    private JFXTextField publisher;

	    @FXML
	    private JFXButton saveButton;

	    @FXML
	    private JFXButton cancel;
	    @FXML
	    private AnchorPane rootPane;
	    private Boolean isEditable = Boolean.FALSE; 
	    DatabaseHandler  databaseHandler;
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
			databaseHandler = new DatabaseHandler();
			checkData();
		}

	    @FXML
	    void addBook(ActionEvent event) {
	    	String bookTitle =  title.getText();
	    	String bookID = id.getText();
	    	String bookAuthor =  author.getText();
	    	String bookPublisher =  publisher.getText();
	    	if(bookID.isEmpty()||bookTitle.isEmpty()||bookPublisher.isEmpty()||bookAuthor.isEmpty()) {
	    		Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setHeaderText(null);
	    		alert.setContentText("Please fill all the information of the Book");
	    		alert.showAndWait();
	    		return ;
	    	}
	    	if(isEditable) {
	    		handleEditOperation();
	    		return;
	    	}
	    	String qu = "INSERT INTO VICKY VALUES ('"+bookID+"',"+
	    										"'" + bookTitle + "',"+
	    										"'" + bookAuthor + "',"+
	     										"'" + bookPublisher + "',"
	     												+ "true"+")";
	    	System.out.println(qu);
	    	if(databaseHandler.execAction(qu)) {
	    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
    		alert.setHeaderText(null);
    		alert.setContentText("Successfully Insert");
    		alert.showAndWait();
    		return ;
    		}else {    //error
	    	Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setHeaderText(null);
    		alert.setContentText("Failed to Insert");
    		alert.showAndWait();
    		return ;
	    }
	    }
	    @FXML
	    void cancel(ActionEvent event) {
	    	Stage stage = (Stage) rootPane.getScene().getWindow();
	    	stage.close();
	    }
	    public void checkData()  {
	    	String qu = "SELECT title FROM VICKY";	
	    	ResultSet rs = databaseHandler.execQuery(qu);
	    	try{
	    		while(rs.next()) {
	    		String tb = rs.getString("title");
	    		System.out.println(tb);
	    	}	
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    	}
	    }
	    public void inflateUI(list_bookController.Book book) {
	    	title.setText(book.getTitle());
	    	id.setText(book.getId());
	    	author.setText(book.getAuthor());
	    	publisher.setText(book.getPublisher());
	    	id.setEditable(false);
	    	isEditable  = Boolean.TRUE;
	    }
	    private void handleEditOperation() {
	    	list_bookController.Book book = new list_bookController.Book(title.getText(),id.getText(),author.getText(),publisher.getText(),true);
	    	if(databaseHandler.updateBook(book)) {
	    		Alert a= new Alert(Alert.AlertType.INFORMATION);
	    		a.setTitle("Success");
	    		a.setHeaderText(null);
	    		a.setContentText("Book Updated");
	    		a.showAndWait();
	    	}else {
	    		Alert a= new Alert(Alert.AlertType.ERROR);
	    		a.setTitle("Failed");
	    		a.setHeaderText(null);
	    		a.setContentText("Book Update Failed");
	    		a.showAndWait();
	    	}
	    }
}
