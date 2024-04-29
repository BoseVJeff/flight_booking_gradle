package io.github.bosev.flight_booking_gradle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.kordamp.bootstrapfx.BootstrapFX;

public class SignupController {
	public SignupController() {

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
