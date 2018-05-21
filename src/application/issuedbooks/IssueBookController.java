package application.issuedbooks;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import application.database.DatabaseHandler;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class IssueBookController implements Initializable {

	@FXML
	private TableView<issuebooks> tableview;

	@FXML
	private TableColumn<issuebooks, String> bookidCol;

	@FXML
	private TableColumn<issuebooks, String> bnameCol;

	@FXML
	private TableColumn<issuebooks, String> memberidCol;

	@FXML
	private TableColumn<issuebooks, String> mnameCol;

	@FXML
	private TableColumn<issuebooks, Integer> mcontactCol;

	@FXML
	private TableColumn<issuebooks, String> memailCol;
	DatabaseHandler databasehandler = new DatabaseHandler();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCol();
		loadData();
	}

	private void loadData() {
		ObservableList<issuebooks> list = FXCollections.observableArrayList();
		String qu = "SELECT bookID,memberID FROM ISSUE ";

		ResultSet rs = databasehandler.execQuery(qu);

		try {
			//outer:
			while (rs.next()) {
				String bookid = rs.getString("bookID");
				String memberid = rs.getString("memberID");

				String qu1 = "SELECT title FROM VICKY  WHERE id = '" + bookid + "'";
				ResultSet rs1 = databasehandler.execQuery(qu1);
				while (rs1.next()) {
					String bname = rs1.getString("title");
					String qu2 = "SELECT name,contactno,email FROM MEMBER WHERE id = '" + memberid + "'";
					ResultSet rs2 = databasehandler.execQuery(qu2);
					while (rs2.next()) {
						String mname = rs2.getString("name");
						int mcontact = rs2.getInt("contactno");
						String memail = rs2.getString("email");
						list.add(new issuebooks(bookid, bname, memberid, mname, mcontact, memail));
					//	continue outer;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		tableview.setItems(list);
	}

	public void initCol() {
		bookidCol.setCellValueFactory(new PropertyValueFactory<issuebooks, String>("bookid"));
		bnameCol.setCellValueFactory(new PropertyValueFactory<issuebooks, String>("bname"));
		memberidCol.setCellValueFactory(new PropertyValueFactory<issuebooks, String>("memberid"));
		mnameCol.setCellValueFactory(new PropertyValueFactory<issuebooks, String>("mname"));
		mcontactCol.setCellValueFactory(new PropertyValueFactory<issuebooks, Integer>("mcontact"));
		memailCol.setCellValueFactory(new PropertyValueFactory<issuebooks, String>("memail"));
	}

	public class issuebooks {
		private final SimpleStringProperty bookid;
		private final SimpleStringProperty bname;
		private final SimpleStringProperty memberid;
		private final SimpleStringProperty mname;
		private final SimpleIntegerProperty mcontact;
		private final SimpleStringProperty memail;

		public issuebooks(String bookid, String bname, String memberid, String mname, int mcontact, String memail) {
			this.bookid = new SimpleStringProperty(bookid);
			this.bname = new SimpleStringProperty(bname);
			this.memberid = new SimpleStringProperty(memberid);
			this.mname = new SimpleStringProperty(mname);
			this.mcontact = new SimpleIntegerProperty(mcontact);
			this.memail = new SimpleStringProperty(memail);
		}

		public String getBookid() {
			return bookid.get();
		}

		public String getBname() {
			return bname.get();
		}

		public String getMemberid() {
			return memberid.get();
		}

		public String getMname() {
			return mname.get();
		}

		public int getMcontact() {
			return mcontact.get();
		}

		public String getMemail() {
			return memail.get();
		}

	}

}