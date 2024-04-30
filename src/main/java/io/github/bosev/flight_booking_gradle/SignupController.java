package io.github.bosev.flight_booking_gradle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.SQLException;

public class SignupController {
	private Navigator navigator;
	private Database database=null;

	public SignupController() {
		this.navigator=Navigator.getInstance();
		try {
			this.database=Database.getInstance();
		} catch (SQLException e) {
			System.err.println("Failed to get database instance!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
		}
	}

	public static Scene getScene() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		return scene;
	}

	@FXML
	protected void gotoLogin() {
		try {
			this.navigator.push(LoginController.getScene());
		} catch (IOException e) {
			System.err.println("Failed to load login page!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
		}
	}

	@FXML
	protected void signup() {
		if(this.database==null) {
			System.err.println("Cannot create user. No database instance available!");
		} else {
			try {
				this.database.insertUser(usernameField.getText(),passwordField.getText(),emailField.getText(),phoneField.getText(),false);
			} catch (SQLException e) {
				System.err.println("SQL error creating user!");
				System.err.println(e.getMessage());
				for (int i = 0; i < e.getStackTrace().length; i++) {
					System.err.println(e.getStackTrace()[i].toString());
				}
			}
			gotoLogin();
		}
	}

	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private PasswordField passwordConfirmationField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField phoneField;

	@FXML
	private Button signupButton;

	@FXML
	private Button backButton;


}
