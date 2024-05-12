package io.github.bosev.flight_booking_gradle;

import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PaymentConfirmController extends FxSceneBase implements Initializable {
	private AppState appState;
	private Database database;
	private Navigator navigator;

	public PaymentConfirmController() {
		this.appState=AppState.getInstance();
		this.navigator=Navigator.getInstance();
		try {
			this.database=Database.getInstance();
		} catch (SQLException e) {
			logException(e,"Unable to get Database instance!");
		}
	}

	public static Scene getScene() throws IOException {
		return getSceneForFile("PaymentConfirm.fxml");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		User user=appState.user;
		for (Passenger passenger : appState.passengers) {
			try {
				database.addPassenger(passenger,user.id);
			} catch (SQLException e) {
				logException(e,"SQL exception, unable to add ticket to database!");
			}
		}
		try {
			this.navigator.push(FlightDashboardController.getScene());
		} catch (IOException e) {
			logException(e,"Unable to push FlightDashboardController!");
		}
	}
}
