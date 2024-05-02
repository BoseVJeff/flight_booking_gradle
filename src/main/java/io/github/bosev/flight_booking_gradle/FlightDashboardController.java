package io.github.bosev.flight_booking_gradle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FlightDashboardController implements Initializable {
	private Navigator navigator;
	private AppState appState;

	public FlightDashboardController() {
		this.navigator=Navigator.getInstance();
		this.appState=AppState.getInstance();
	}

	public static Scene getScene() throws IOException {
		FXMLLoader fxmlLoader=new FXMLLoader(FlightDashboardController.class.getResource("Flight_Dashboard.fxml"));
		return new Scene(fxmlLoader.load());
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.username.setText(appState.user.name);
		System.out.println(appState.user.isAdmin);
		try {
			this.adminButton.visibleProperty().bind(new SimpleBooleanProperty(appState.user.isAdmin));
		} catch (Exception e) {
			System.err.println("["+e.getClass().getName()+"]"+"Exception binding boolean!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
		}
	}

	@FXML
	protected void bookFlightsButtonAction() {}

	@FXML
	protected void viewTicketsButtonAction() {}

	@FXML
	protected void signupButtonAction() {
		try {
			this.navigator.push(SignupController.getScene());
		} catch (IOException e) {
			System.err.println("Error navigating to Signup Scene!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
		}
	}

	@FXML
	protected void launchAdminPage() {
		try {
			this.navigator.push(UserDump.getScene());
		} catch (IOException e) {
			System.err.println("Error loading admin page!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
		}
	}

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

	@FXML
	private Button adminButton;
}
