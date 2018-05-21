package application.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import application.listbook.list_bookController.Book;
import application.listmember.ListMemberController.Member;

public class DatabaseHandler {
	//private static DatabaseHandler handler;
	
	private static final String DB_URL =  "jdbc:derby:database;create=true";
	private static Connection conn = null;
	private static Statement stmt = null; 
	
	public DatabaseHandler() {
		createConnection();
		setupBookTable();
		setupMemberTable();
		setupIssueTable();
	}
	
	void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();   //loading jdbc deriver 
			conn = DriverManager.getConnection(DB_URL);                            //creating connection to database
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Can't Load Database","Database Error",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			//e.printStackTrace();
		}
	}
	void setupBookTable() {
		String TABLE_NAME = "VICKY";
		try {
			stmt = conn.createStatement();
		DatabaseMetaData dbm = conn.getMetaData();      //?
		ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);     //?  //getTables(String catalogs, schema pattern ,table name,pattern style)
		if(tables.next()) {
			System.out.println("table "+TABLE_NAME+" already exists and ready for go");
		}else {
			stmt.execute("CREATE TABLE "+ TABLE_NAME + "("
					+ " id varchar(200) primary key ,\n"
					+ " title varchar(200) ,\n"
					+ " author varchar(200),\n"
					+ "publisher varchar(200),\n"
					+ "isAvail boolean default true"+")");
			}
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage()+"....setupdatabase in Vicky");
		//	System.err.println(e.toString()+"....setupdatabase");
		}
		
	}
	void setupMemberTable() {
		String TABLE_NAME = "MEMBER";
		try {
			stmt = conn.createStatement();
		DatabaseMetaData dbm = conn.getMetaData();
		ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);   //getTables(String catalogs, schema pattern ,table name,pattern style)
		if(tables.next()) {
			System.out.println("table "+TABLE_NAME+" already exists and ready for go");
		}else {
			stmt.execute("CREATE TABLE "+ TABLE_NAME + "("
					+ " name varchar(200) ,\n"
					+ " id varchar(200) primary key,\n"
					+ " contactno varchar(20),\n"
					+ "email varchar(200)\n"
					+ ")");
			}
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage()+"....setupdatabase  in member");
			//System.err.println(e.toString()+"....setupdatabase");
		}
		
	}
	
	void setupIssueTable() {
		String TABLE_NAME = "ISSUE";
		try {
			stmt = conn.createStatement();
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
			if(tables.next()) {
				System.out.println("Table "+TABLE_NAME +" already exists  and ready for go");
			}else{
				stmt.execute("CREATE TABLE "+TABLE_NAME+" ("
						+ "bookID varchar(200) primary key,\n "
						+ "memberID varchar(200) primary key ,\n"
						+ "issueTime timestamp default CURRENT_TIMESTAMP,\n"
						+ "renew_count integer default 0,\n"
						+ "FOREIGN KEY (bookId) REFERENCES VICKY(id),\n"
						+ "FOREIGN KEY (memberId) REFERENCES MEMBER(id)"
						+ ")");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage()+"....setupdatabase in issue table");
		//	System.err.println(e.toString()+"....setupdatabase");
		}
	}
	
	public ResultSet execQuery(String query) {
		ResultSet result = null;
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	public boolean execAction(String qu) {
		try {
			stmt = conn.createStatement();
			stmt.execute(qu);
			return true;
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error:"+e.getMessage(),"Error occured",JOptionPane.ERROR_MESSAGE);
			System.out.println(e.getLocalizedMessage());
			return false;
		}
	}

	public static DatabaseHandler getInstance() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean deleteBook(Book book) {
		String deleteStatement = "DELETE FROM VICKY WHERE ID= ? ";
		try {
			PreparedStatement stmt = conn.prepareStatement(deleteStatement);
			stmt.setString(1, book.getId());
			int res =stmt.executeUpdate();
			if(res==1)
				return true;	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isBookAlreadyIssueed(Book book) {
		String checkStatus = "SELECT COUNT(*) FROM VICKY WHERE ID=?";
		try {
			PreparedStatement stmt = conn.prepareStatement(checkStatus);
			stmt.setString(1, book.getId());
			ResultSet rs = stmt.executeQuery(checkStatus);
			if(rs.next()) {
				int count = rs.getInt(1);
				System.out.println(count);
				if(count>0)
						return true;
				else
						return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateBook(Book book) {
		String update = "UPDATE VICKY SET TITLE =?, AUTHOR=?, PUBLISHER=? WHERE ID=?";
		System.out.println(update);
		try {
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.setString(3, book.getPublisher());
			stmt.setString(4, book.getId());
			int res = stmt.executeUpdate();
			return (res>0);
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return false;
		
	}
	public boolean deleteMember(Member member) {
		String deleteStatement = "DELETE FROM MEMBER WHERE ID= ? ";
		try {
			PreparedStatement stmt = conn.prepareStatement(deleteStatement);
			stmt.setString(1, member.getId());
			int res =stmt.executeUpdate();
			if(res==1)
				return true;	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isMemberAlreadyIssueed(Member member) {
		String checkStatus = "SELECT COUNT(*) FROM MEMBER WHERE ID=?";
		try {
			PreparedStatement stmt = conn.prepareStatement(checkStatus);
			stmt.setString(1, member.getId());
			ResultSet rs = stmt.executeQuery(checkStatus);
			if(rs.next()) {
				int count = rs.getInt(1);
				System.out.println(count);
				if(count>0)
						return true;
				else
						return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateMember(Member member) {
		String update = "UPDATE MEMBER SET NAME =?, CONTACTNO=?, EMAIL=? WHERE ID=?";
		System.out.println(update);
		try {
			PreparedStatement stmt = conn.prepareStatement(update);
			stmt.setString(1, member.getName());
			stmt.setString(2, member.getContactno());
			stmt.setString(3, member.getEmail());
			stmt.setString(4, member.getId());
			int res = stmt.executeUpdate();
			return (res>0);
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return false;
		
	}
}


