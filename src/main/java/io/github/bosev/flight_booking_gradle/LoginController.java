package io.github.bosev.flight_booking_gradle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
	private Navigator navigator;
	private Database database;
	public LoginController() {
		this.navigator=Navigator.getInstance();
		try {
			this.database=Database.getInstance();
		} catch (SQLException e) {
			System.err.println("Failed to get database instance!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
			this.database=null;
		}
	}

	public static Scene getScene() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("Login_page.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		return scene;
	}

	@FXML
	private TextField username;

	@FXML
	private TextField password;

	@FXML
	private Button loginButton;

	@FXML
	private Button signupButton;

	@FXML
	protected void login() {
		if(database==null) {
			System.err.println("No database instance available. Cannot login user!");

		}else {
			User user = null;
			try {
				user = this.database.validateUser(username.getText(), password.getText());
			} catch (SQLException e) {
				System.err.println("Error executing validation SQL!");
				System.err.println(e.getMessage());
				for (int i = 0; i < e.getStackTrace().length; i++) {
					System.err.println(e.getStackTrace()[i].toString());
				}
			}
			if (user == null) {
				System.err.println("User is invalid!");
//			TODO: Add statement to UI
			} else {

			}
		}
	}
}
