	package application.listmember;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import application.addmember.AddMemberController;
import application.database.DatabaseHandler;
import application.utilIcons.LibraryutilIcons;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ListMemberController implements Initializable {
	ObservableList<Member> list = FXCollections.observableArrayList();
	@FXML
	private AnchorPane rootPane;
    @FXML
    private TableView<Member> tableview;

    @FXML
    private TableColumn<Member, String> nameCol;

    @FXML
    private TableColumn<Member, String> idCol;

    @FXML
    private TableColumn<Member, String> contactCol;

    @FXML
    private TableColumn<Member, String> emailCol;
    DatabaseHandler handler = new DatabaseHandler();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCol();
		loadData();		
	}
	private void loadData(){
		list.clear();
		DatabaseHandler handler = new DatabaseHandler();
		String st = "SELECT * FROM MEMBER";
		ResultSet rs = handler.execQuery(st);
		try {
			while(rs.next()) {
				String name = rs.getString("name");
				String id = rs.getString("id");
				String contactno = rs.getString("contactno");
				String email = rs.getString("email");
				list.add(new Member(name,id,contactno,email));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		//tableview.getItems().setAll(list);
		tableview.setItems(list);
	}
	
	void initCol() {
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		contactCol.setCellValueFactory(new PropertyValueFactory<>("contactno"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
	}
	public static class Member{
		private final SimpleStringProperty name;
		private final SimpleStringProperty id;
		private final SimpleStringProperty contactno;
		private final SimpleStringProperty email;
		
		public Member(String name, String id, String contactno,String email) {
			this.name = new SimpleStringProperty(name);
			this.id = new SimpleStringProperty(id);;
			this.contactno = new SimpleStringProperty(contactno);;
			this.email = new SimpleStringProperty(email);;
		}
		public String getName() {
			return name.get();
		}
		public String getId() {
			return id.get();
		}
		public String getContactno() {
			return contactno.get();
		}
		public String getEmail() {
			return email.get();
		}	
	}
	  @FXML
	    void handleMemberMenuDelete(ActionEvent event) {
			//Fetching the row for deletion
			Member selectForDeletion = tableview.getSelectionModel().getSelectedItem();
			if(selectForDeletion == null) {
				Alert a  = new Alert(Alert.AlertType.ERROR);
				a.setHeaderText(null);
				a.setTitle("No Member Selected");
				a.setContentText("Please select a Member");
				a.showAndWait();
				return;
			}
			Boolean result1 = handler.isMemberAlreadyIssueed(selectForDeletion);
			if(result1) {
				Alert a2  = new Alert(Alert.AlertType.INFORMATION);
				a2.setHeaderText(null);
				a2.setTitle("Cannot be delete");
				a2.setContentText("Book "+selectForDeletion.getName()+" is issued to someone");
				a2.showAndWait();
				return;
			}
			Alert a  = new Alert(Alert.AlertType.CONFIRMATION);
			a.setHeaderText(null);
			a.setTitle("Deleting a Member");
			a.setContentText("Are you sure You want to delete the Member "+selectForDeletion.getName()+" ?");
			Optional<ButtonType> ans =a.showAndWait();
			if(ans.get() == ButtonType.OK) {
				Boolean result =handler.deleteMember(selectForDeletion);
//				String str = "DELETE FORM MEMBER WHERE ID='"+selectForDeletion.getId()+"'";
				if(result) {
					Alert a2  = new Alert(Alert.AlertType.INFORMATION);
					a2.setHeaderText(null);
					a2.setTitle("Successful");
					a2.setContentText("Member "+selectForDeletion.getName()+" is deleted successfully");
					a2.showAndWait();
					
					list.remove(selectForDeletion);
				}else {
					Alert a1  = new Alert(Alert.AlertType.ERROR);
					a1.setHeaderText(null);
					a1.setTitle("Deletion Error");
					a1.setContentText("Error In Member Deletion ");
					a1.showAndWait();
				}
			}else {
				Alert a1  = new Alert(Alert.AlertType.INFORMATION);
				a1.setHeaderText(null);
				a1.setTitle("Deletion Canceled");
				a1.setContentText("Member Deletion is canceled");
				a1.showAndWait();
			}
			
	    }

	    @FXML
	    void handleMemberMenuEdit(ActionEvent event) {
	    	Member selectForDeletion = tableview.getSelectionModel().getSelectedItem();
			if(selectForDeletion == null) {
				Alert a  = new Alert(Alert.AlertType.ERROR);
				a.setHeaderText(null);
				a.setTitle("No Member Selected");
				a.setContentText("Please select a Member");
				a.showAndWait();
				return;
			}
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/addmember/Add_Member.fxml"));
				Parent parent = loader.load();
				
				AddMemberController controller = (AddMemberController)loader.getController();
				controller.inflateUIMember(selectForDeletion);
				
				Stage stage = new Stage(StageStyle.DECORATED);
				stage.setTitle("Edit Member");
				stage.setScene(new Scene(parent));
				stage.show();
				LibraryutilIcons.setStageIcon(stage);
				stage.setOnCloseRequest((e)->{
				handleMemberMenuRefresh(new ActionEvent());	//to refresh on closing the window
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    @FXML
	    void handleMemberMenuRefresh(ActionEvent event) {
	    	loadData();
	    }
	
}
