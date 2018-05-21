package application.settings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import javafx.scene.control.Alert;

public class Preferences {
	public static final String CONFIG_FILE ="config.txt"; 
	int nDaysWithoutFine;
	float fineperday;
	String username;
	String password;
	
	public Preferences() {
		nDaysWithoutFine = 14;
		fineperday = 2;
		username = "admin";
		setPassword("admin");
		//password = "vicky";
	}

	public int getnDaysWithoutFine() {
		return nDaysWithoutFine;
	}

	public void setnDaysWithoutFine(int nDaysWithoutFine) {
		this.nDaysWithoutFine = nDaysWithoutFine;
	}

	public float getFineperday() {
		return fineperday;
	}

	public void setFineperday(float fineperday) {
		this.fineperday = fineperday;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = DigestUtils.shaHex(password);
	}
	
	public static void initConfig() {
		Writer writer = null;
		Preferences pref = new Preferences();
		Gson gson = new Gson();
		try {
			 writer = new FileWriter(CONFIG_FILE);
			gson.toJson(pref,writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Preferences getPreferences() {
		Gson gson =new Gson();
		Preferences pref1 = new Preferences();
		try {
			pref1 = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
		} catch (Exception e) {
			initConfig();
			e.printStackTrace();
		} 
		return pref1;
	}
	public static void writePreferencesToFile(Preferences pref) {
		Writer writer = null;
		Gson gson = new Gson();
		try {
			 writer = new FileWriter(CONFIG_FILE);
			gson.toJson(pref,writer);
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("Settings updated successfully");
			alert.showAndWait();
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Failed");
			alert.setHeaderText(null);
			alert.setContentText(e.getLocalizedMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
