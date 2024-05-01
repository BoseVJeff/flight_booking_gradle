package io.github.bosev.flight_booking_gradle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class FlightDashboardController {

	public FlightDashboardController() {}

	@FXML
	protected void bookFlightsButtonAction() {}

	@FXML
	protected void viewTicketsButtonAction() {}

	@FXML
	private Label username;

	@FXML
	private Button bookFlightsButton;

	@FXML
	private Button viewTicketsButton;

	@FXML
	private Button signupButton;

	@FXML
	private Button exitButton;
}
