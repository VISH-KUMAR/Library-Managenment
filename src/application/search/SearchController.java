package application.search;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import application.database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


public class SearchController implements Initializable {

    @FXML
    private Label bname;

    @FXML
    private JFXTextField bookName;

    @FXML
    private Label bid;

    @FXML
    private JFXTextField bookID;

    @FXML
    private JFXListView<String> searchedBookData;
    @FXML
    private JFXListView<String> searchedMemberData;

    @FXML
    private Label mname;

    @FXML
    private JFXTextField memberName;

    @FXML
    private Label mid;

    @FXML
    private JFXTextField memberID;
    DatabaseHandler databasehandler = new DatabaseHandler();

    @FXML
    void handleSearchByID(ActionEvent event) {
    	ObservableList<String> list = FXCollections.observableArrayList(); 
    	searchedBookData.getItems().clear();
    	String st = bookID.getText();
    	String qu = "SELECT * FROM VICKY WHERE id ='"+st+"'";
    	ResultSet r1 = databasehandler.execQuery(qu);
    	try {
				while(r1.next()) {
					list.add("Book Name :"+r1.getString("title"));
					list.add("Book Author :"+r1.getString("author"));
					list.add("Book ID :"+st);
					list.add("Publisher :"+r1.getString("publisher"));
				}
				
			}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	searchedBookData.getItems().addAll(list);
    }

	@FXML
    void handleSearchByName(ActionEvent event) {
		ObservableList<String> list = FXCollections.observableArrayList(); 
		searchedBookData.getItems().clear();
    	String st = bookName.getText();
    	String qu = "SELECT * FROM VICKY WHERE title ='"+st+"'";
    	ResultSet r1 = databasehandler.execQuery(qu);
    	boolean flag = false;
    	try {
			while(r1.next()) {
				list.add("Book Name :"+r1.getString("title"));
				list.add("Book Author :"+r1.getString("author"));
				list.add("Book ID :"+r1.getString("id"));
				list.add("Publisher :"+r1.getString("publisher"));
			}
			
		}
	 catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	searchedBookData.getItems().addAll(list);
			

    }

    @FXML
    void handleSearchBymID(ActionEvent event) {
    	ObservableList<String> list = FXCollections.observableArrayList(); 
    	searchedMemberData.getItems().clear();
    	String st = memberID.getText();
    	String qu = "SELECT * FROM MEMBER WHERE id ='"+st+"'";
    	ResultSet r1 = databasehandler.execQuery(qu);
    	try {
				while(r1.next()) {
					list.add("Member Name :"+r1.getString("name"));
					list.add("Member ID :"+r1.getString("id"));
					list.add("Member Contact NO :"+r1.getString("contactno"));
					list.add("Email ID :"+r1.getString("email"));
				}
				
			}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	searchedMemberData.getItems().addAll(list);
    }

    @FXML
    void handleSearchBymName(ActionEvent event) {
    	ObservableList<String> list = FXCollections.observableArrayList(); 
    	searchedMemberData.getItems().clear();
    	String st = memberName.getText();
    	String qu = "SELECT * FROM MEMBER WHERE name ='"+st+"'";
    	ResultSet r1 = databasehandler.execQuery(qu);
    	try {
				while(r1.next()) {
					list.add("Member Name :"+r1.getString("name"));
					list.add("Member ID :"+r1.getString("id"));
					list.add("Member Contact NO :"+r1.getString("contactno"));
					list.add("Email ID :"+r1.getString("email"));
				}
				
			}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	searchedMemberData.getItems().addAll(list);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
