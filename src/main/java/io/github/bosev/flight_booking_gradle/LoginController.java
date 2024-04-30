package io.github.bosev.flight_booking_gradle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {
	private Navigator navigator;
	public LoginController() {
		this.navigator=Navigator.getInstance();
	}

	@FXML
	private TextField username;

	@FXML
	private TextField password;

	@FXML
	private Button loginButton;

	@FXML
	private Button signupButton;
}
