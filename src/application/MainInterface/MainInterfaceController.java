package application.MainInterface;

import java.io.IOException;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import com.jfoenix.effects.JFXDepthManager;

import application.alertMaker.AlertMaker;
import application.database.DatabaseHandler;
import application.utilIcons.LibraryutilIcons;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainInterfaceController implements Initializable {
	@FXML
	private StackPane rootPane;
	@FXML
	private TextField bookID1;
	@FXML
	private HBox book_info, member_info;
	@FXML
	private Text bookname;
	@FXML
	private Text authorname;
	@FXML
	private Text bookstatus;
	@FXML
	private Text membername;
	@FXML
	private Text contactno;
	@FXML
	private TextField bookIDInput;
	@FXML
	private TextField memberIDInput;
	@FXML
	private ListView<String> issueDataList;
	Boolean isReadyForSubmission = false;
	DatabaseHandler databasehandler;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		JFXDepthManager.setDepth(book_info, 2);
		JFXDepthManager.setDepth(member_info, 2);
		databasehandler = new DatabaseHandler();

	}

	@FXML
	void loadAddBook(ActionEvent event) {
		loadWindow("/application/AddBook.fxml", "Add Book");
	}

	@FXML
	void loadAddMember(ActionEvent event) {
		loadWindow("/application/addmember/Add_Member.fxml", "Add New Member");
	}

	@FXML
	void loadBookTable(ActionEvent event) {
		loadWindow("/application/listbook/list_book.fxml", "All Books");
	}

	@FXML
	void loadMemberTable(ActionEvent event) {
		loadWindow("/application/listmember/List_Member.fxml", "All Members");
	}

	@FXML
	void loadSettings(ActionEvent event) {
		loadWindow("/application/settings/Settings.fxml", "All Members");
	}

	@FXML
	void loadSearch(ActionEvent event) {
		loadWindow("/application/search/Searching.fxml", "All Members");
	}
	
	@FXML
	void loadIssuedBooks(ActionEvent event) {
		loadWindow("/application/issuedbooks/Issued_Books.fxml", "All Members");
	}


	void loadWindow(String loc, String title) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource(loc));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle(title);
			stage.setScene(new Scene(parent));
			stage.show();
			LibraryutilIcons.setStageIcon(stage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void loadBookInfo(ActionEvent ae) {
		clearBookCache();
		String st = bookIDInput.getText();
		String qu = "SELECT title,author,isAvail FROM VICKY WHERE id ='" + st + "'";
		ResultSet rs = databasehandler.execQuery(qu);
		boolean flag = false;
		try {
			while (rs.next()) {
				String bName = rs.getString("title");
				String bAuthor = rs.getString("author");
				Boolean bStatus = rs.getBoolean("isAvail");

				bookname.setText(bName);
				authorname.setText(bAuthor);
				String status = (bStatus) ? "Available" : "Not Availavle";
				bookstatus.setText(status);
				flag = true;
			}
			if (!flag) {
				bookname.setText("Such Book is not available");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void clearBookCache() {
		bookname.setText("");
		authorname.setText("");
		bookstatus.setText("");
	}

	@FXML
	private void loadMemberInfo(ActionEvent ae) {
		clearMemberCache();
		String st = memberIDInput.getText();
		String query = "SELECT * FROM MEMBER WHERE id='" + st + "'";
		System.out.println(query);

		ResultSet rs1 = databasehandler.execQuery(query);
		boolean flag = false;
		try {
			while (rs1.next()) {
				String mName = rs1.getString("name");
				String mContact = rs1.getString("contactno");

				membername.setText(mName);
				contactno.setText(mContact);
				flag = true;
			}
			if (!flag) {
				bookname.setText("Not a valid id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void clearMemberCache() {
		membername.setText("");
		contactno.setText("");

	}

	@FXML
	private void loadIssueOperation(ActionEvent ae) {
		String memberID = memberIDInput.getText();
		String bookID1 = bookIDInput.getText();

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm Issue Opertation");
		alert.setHeaderText(null);
		alert.setContentText("Are yous sure issue the book " + bookname.getText());
		// alert.showAndWait();

		Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
		if (result.get() == javafx.scene.control.ButtonType.OK) {
			String str = "INSERT INTO ISSUE(bookID , memberID) VALUES (" + "'" + bookID1 + "'," + "'" + memberID + "')";
			String str2 = "UPDATE VICKY SET isAvail = false WHERE id= '" + bookID1 + "'";
			if (databasehandler.execAction(str) && databasehandler.execAction(str2)) {
//				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
//				alert1.setTitle("Success");
//				alert1.setHeaderText(null);
//				alert1.setContentText("Book Issue Complete");
//				alert1.showAndWait();
				new AlertMaker(AlertType.INFORMATION,"Success","Book Issue Complete");
			} else {
//				Alert alert2 = new Alert(Alert.AlertType.ERROR);
//				alert2.setTitle("Failed");
//				alert2.setHeaderText(null);
//				alert2.setContentText("book Issue Is Not Successful");
//				alert2.showAndWait();
				new AlertMaker(AlertType.ERROR,"Failed","Book Issue Is not Successful");
			}

		} else {
//			Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
//			alert1.setTitle("Cancel");
//			alert1.setHeaderText(null);
//			alert1.setContentText("Book Issue Cancelled");
//			alert1.showAndWait();
			new AlertMaker(AlertType.INFORMATION,"Cancel","Book Issue Cancelled");
		}
	}

	@FXML
	private void loadBookInfo2(ActionEvent ae) {
		ObservableList<String> issueData = FXCollections.observableArrayList();
		if (isReadyForSubmission)
			issueDataList.getItems().clear();
		isReadyForSubmission = false;

		// clearBook1Cache();
		String st = bookID1.getText();
		String query = "SELECT * FROM ISSUE WHERE bookID='" + st + "'";
		System.out.println(query);

		ResultSet rs1 = databasehandler.execQuery(query);

		try {
			while (rs1.next()) {
				String mBookID = st;
				String mMemberID = rs1.getString("memberID");
				Timestamp mTime = rs1.getTimestamp("issueTime");
				int renewCount = rs1.getInt("renew_count");

				issueData.add("Issue Time and Date :" + mTime.toLocalDateTime());
				issueData.add("RenewCount  :" + renewCount);
				issueData.add("Book Infomatoion:");

				String st1 = "SELECT * FROM VICKY WHERE id ='" + mBookID + "'";
				ResultSet r1 = databasehandler.execQuery(st1);
				while (r1.next()) {
					issueData.add("Book Name :" + r1.getString("title"));
					issueData.add("Book Author :" + r1.getString("author"));
					issueData.add("Book ID :" + mBookID);
					issueData.add("Publisher :" + r1.getString("publisher"));
				}
				issueData.add("Member Information:");
				String st2 = "SELECT * FROM MEMBER WHERE id ='" + mMemberID + "'";
				ResultSet r2 = databasehandler.execQuery(st2);
				while (r2.next()) {
					issueData.add("Member Name :" + r2.getString("name"));
					issueData.add("Contact NO. :" + r2.getString("contactno"));
					issueData.add("Email :" + r2.getString("email"));
				}
			}
			isReadyForSubmission = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		issueDataList.getItems().addAll(issueData);
	}

	@FXML
	public void bookSubmissionOperation(ActionEvent ae) {
		if (!isReadyForSubmission) {
//			Alert alert = new Alert(Alert.AlertType.ERROR);
//			alert.setTitle("Failed");
//			alert.setHeaderText(null);
//			alert.setContentText("enter the correct bookid");
//			alert.showAndWait();
			new AlertMaker(AlertType.ERROR,"Failed","Enter the correct BookID");
			return;
		}

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm Issue Opertation");
		alert.setHeaderText(null);
		alert.setContentText("Are yous sure issue the book " + bookname.getText());
		// alert.showAndWait();

		Optional<javafx.scene.control.ButtonType> response = alert.showAndWait();
		if (response.get() == javafx.scene.control.ButtonType.OK) {

			String id = bookID1.getText();
			String ac1 = "DELETE FROM ISSUE WHERE bookID='" + id + "'";
			String ac2 = "UPDATE VICKY SET isAvail = true WHERE id ='" + id + "'";
			if (databasehandler.execAction(ac1) && databasehandler.execAction(ac2)) {
//				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
//				alert1.setTitle("Success");
//				alert1.setHeaderText(null);
//				alert1.setContentText("Book has successfully submitted");
//				alert1.showAndWait();
				new AlertMaker(AlertType.INFORMATION,"Success","Book has successfully submitted");
			} else {
//				Alert alert2 = new Alert(Alert.AlertType.ERROR);
//				alert2.setTitle("Failed");
//				alert2.setHeaderText(null);
//				alert2.setContentText("Error In Submission");
//				alert2.showAndWait();
				new AlertMaker(AlertType.ERROR,"Failed","Error in submission");
			}
		} else {
//			Alert alert3 = new Alert(Alert.AlertType.ERROR);
//			alert3.setTitle("Cancel");
//			alert3.setHeaderText(null);
//			alert3.setContentText("Submission Canceled");
//			alert3.showAndWait();
			new AlertMaker(AlertType.ERROR,"Cancel","Submission Canceled");
		}
	}

	@FXML
	private void loadRenewOperation(ActionEvent ae) {
		if (!isReadyForSubmission) {
//			Alert alert = new Alert(Alert.AlertType.ERROR);
//			alert.setTitle("Failed");
//			alert.setHeaderText(null);
//			alert.setContentText("Select the Book to renew");
//			alert.showAndWait();
			new AlertMaker(AlertType.ERROR,"Failed","Select the book to renew");
			return;
		}

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm Renew Opertation");
		alert.setHeaderText(null);
		alert.setContentText("Are yous sure renew the book " + bookname.getText());
		// alert.showAndWait();

		Optional<javafx.scene.control.ButtonType> response = alert.showAndWait();
		if (response.get() == javafx.scene.control.ButtonType.OK) {

			String id = bookID1.getText();
			String ac = "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP,renew_count = renew_count +1  WHERE  bookID ='"
					+ id + "'";
			if (databasehandler.execAction(ac)) {
//				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
//				alert1.setTitle("Success");
//				alert1.setHeaderText(null);
//				alert1.setContentText("Book has successfully RENEWED");
//				alert1.showAndWait();
				new AlertMaker(AlertType.INFORMATION,"Success","Book is successfully renewed");
			} else {
//				Alert alert2 = new Alert(Alert.AlertType.ERROR);
//				alert2.setTitle("Failed");
//				alert2.setHeaderText(null);
//				alert2.setContentText("Error In RENEW ");
//				alert2.showAndWait();
				new AlertMaker(AlertType.ERROR,"Failed","Error in renew");
			}
		} else {
//			Alert alert3 = new Alert(Alert.AlertType.ERROR);
//			alert3.setTitle("Cancel");
//			alert3.setHeaderText(null);
//			alert3.setContentText("Renew Operation is Canceled");
//			alert3.showAndWait();
			new AlertMaker(AlertType.ERROR,"Failed","Renew ooperation is Canceled");
		}
	}

	@FXML
	public void handleClose(ActionEvent ae) {
		((Stage) rootPane.getScene().getWindow()).close();
	}

	@FXML
	private void handleAddBook(ActionEvent ae) {
		loadWindow("/application/AddBook.fxml", "Add New Book");

	}

	@FXML
	private void handleAddMember(ActionEvent ae) {
		loadWindow("/application/addmember/Add_Member.fxml", "Add Member");
	}

	@FXML
	private void handleMenuViewMember(ActionEvent ae) {
		loadWindow("/application/listmember/List_Member.fxml", "All Members");
	}

	@FXML
	private void handleMenuViewBook(ActionEvent ae) {
		loadWindow("/application/listbook/list_book.fxml", "All Books");
	}

	@FXML
	private void handleMenuSearch(ActionEvent ae) {
		loadWindow("/application/search/Searching.fxml", "Search Details");
	}

	@FXML
	private void handleMenuFullScreen(ActionEvent ae) {
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.setFullScreen(!stage.isFullScreen());
	}
	@FXML
	void handleDeleteBook(ActionEvent e) {
		
	}
	@FXML
	void handleDeleteMember(ActionEvent e) {
		
	}

	
}
