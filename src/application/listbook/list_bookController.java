package application.listbook;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import application.MainController;
import application.database.DatabaseHandler;
import application.utilIcons.LibraryutilIcons;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;

public class list_bookController implements Initializable {
	ObservableList<Book> list = FXCollections.observableArrayList();
	@FXML
	private AnchorPane rootPane;	
	@FXML
	private  TableView<Book> tableview;
	@FXML
	private TableColumn<Book, String> titleCol;
	@FXML
	private TableColumn<Book, String> idCol;
	@FXML
	private TableColumn<Book, String> authorCol;
	@FXML
	private TableColumn<Book, String> publisherCol;
	@FXML
	private TableColumn<Book, Boolean> availabilityCol;
	DatabaseHandler handler = new DatabaseHandler();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCol();
		loadData();
		
	}
	private void loadData() {
		list.clear();
		DatabaseHandler handler=  new DatabaseHandler();
		String qu = "SELECT * FROM VICKY";
		ResultSet rs = handler.execQuery(qu);
		try {
			while(rs.next()) {
				String title = rs.getString("title");
				String id = rs.getString("id");
				String author = rs.getString("author");
				String publisher = rs.getString("publisher");
				Boolean avail = rs.getBoolean("isAvail");
				list.add(new Book(title,id,author,publisher,avail));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		//tableview.getItems().setAll(list);
		tableview.setItems(list);
		
		}
	private void initCol() {
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
		publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
		availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
	}
	public static class Book{
		private final SimpleStringProperty title;
		private final SimpleStringProperty id;
		private final SimpleStringProperty author;
		private final SimpleStringProperty publisher;
		private final SimpleBooleanProperty availability;
		
		public Book(String title, String id ,String author, String publisher, boolean avail) {
			this.title = new SimpleStringProperty(title);
			this.id = new SimpleStringProperty(id);
			this.author = new SimpleStringProperty(author);
			this.publisher = new SimpleStringProperty(publisher);
			this.availability = new SimpleBooleanProperty(avail);
		}

		public String getTitle() {
			return title.get();
		}
		public String getId() {
			return id.get();
		}
		public String getAuthor() {
			return author.get();
		}
		public String getPublisher() {
			return publisher.get();
		}

		public Boolean getAvailability() {
			return availability.get();
		}

	}
	@FXML
	private  void handleBookDeletion(ActionEvent ae) {
		//Fetching the row for deletion
		Book selectForDeletion = tableview.getSelectionModel().getSelectedItem();
		if(selectForDeletion == null) {
			Alert a  = new Alert(Alert.AlertType.ERROR);
			a.setHeaderText(null);
			a.setTitle("No Book Selected");
			a.setContentText("Please select a book");
			a.showAndWait();
			return;
		}
		Boolean result1 = handler.isBookAlreadyIssueed(selectForDeletion);
		if(result1) {
			Alert a2  = new Alert(Alert.AlertType.INFORMATION);
			a2.setHeaderText(null);
			a2.setTitle("Cannot be delete");
			a2.setContentText("Book "+selectForDeletion.getTitle()+" is issued to someone");
			a2.showAndWait();
			return;
		}
		Alert a  = new Alert(Alert.AlertType.CONFIRMATION);
		a.setHeaderText(null);
		a.setTitle("Deleting a Book");
		a.setContentText("Are you sure You want to delete the book "+selectForDeletion.getTitle()+" ?");
		Optional<ButtonType> ans =a.showAndWait();
		if(ans.get() == ButtonType.OK) {
			Boolean result =handler.deleteBook(selectForDeletion);
//			String str = "DELETE FORM VICKY WHERE ID='"+selectForDeletion.getId()+"'";
			if(result) {
				Alert a2  = new Alert(Alert.AlertType.INFORMATION);
				a2.setHeaderText(null);
				a2.setTitle("Successful");
				a2.setContentText("Book "+selectForDeletion.getTitle()+" is deleted successfully");
				a2.showAndWait();
				
				list.remove(selectForDeletion);
			}else {
				Alert a1  = new Alert(Alert.AlertType.ERROR);
				a1.setHeaderText(null);
				a1.setTitle("Deletion Error");
				a1.setContentText("Error In Book Deletion ");
				a1.showAndWait();
			}
		}else {
			Alert a1  = new Alert(Alert.AlertType.INFORMATION);
			a1.setHeaderText(null);
			a1.setTitle("Deletion Canceled");
			a1.setContentText("Book Deletion is canceled");
			a1.showAndWait();
		}
		
	}
	@FXML
	private  void handleEditBookOption(ActionEvent ae) {
		Book selectForDeletion = tableview.getSelectionModel().getSelectedItem();
		if(selectForDeletion == null) {
			Alert a  = new Alert(Alert.AlertType.ERROR);
			a.setHeaderText(null);
			a.setTitle("No Book Selected");
			a.setContentText("Please select a book");
			a.showAndWait();
			return;
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/AddBook.fxml"));
			Parent parent = loader.load();
			
			MainController controller = (MainController)loader.getController();
			controller.inflateUI(selectForDeletion);
			
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("Edit Book");
			stage.setScene(new Scene(parent));
			stage.show();
			LibraryutilIcons.setStageIcon(stage);
			stage.setOnCloseRequest((e)->{
			handleRefreshOption(new ActionEvent());	//to refresh on closing the window
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
	private void handleRefreshOption(ActionEvent ae) {
		loadData();
	}
}
