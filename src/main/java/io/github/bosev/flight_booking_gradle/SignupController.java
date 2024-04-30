package io.github.bosev.flight_booking_gradle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class SignupController {
	public SignupController() {

	}

	public static Scene getScene() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		return scene;
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
