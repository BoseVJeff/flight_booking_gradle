package io.github.bosev.flight_booking_gradle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewTicket extends FxSceneBase implements Initializable {
	private AppState appState;
	private Navigator navigator;

	public ViewTicket() {
		this.appState=AppState.getInstance();
		this.navigator=Navigator.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.username.setText(appState.user.name);

		this.passengerName.setText(appState.selectedTicket.passengerName);
		this.phoneNo.setText(appState.user.phoneNumber);
		this.email.setText(appState.user.email);
		this.idType.setText(appState.selectedTicket.idType);
		this.idId.setText(appState.selectedTicket.idId);
		this.seatNo.setText(Integer.toString(appState.selectedTicket.seatNo));
		this.age.setText(Integer.toString(appState.selectedTicket.age));
		this.gender.setText(appState.selectedTicket.gender.toString());

		this.flightName.setText(appState.selectedTicketFlight.flightName);
		this.flightNo.setText(appState.selectedTicketFlight.airline);
		this.flightSource.setText(appState.selectedTicketFlight.source);
		this.flightDestination.setText(appState.selectedTicketFlight.destination);
		this.departureTime.setText(appState.selectedTicketFlight.departure.toString());
		this.arrivalTime.setText(appState.selectedTicketFlight.arrival.toString());
		this.ticketPrice.setText(String.valueOf(appState.selectedTicketFlight.price));

		this.backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					navigator.push(FlightDashboardController.getScene());
				} catch (IOException e) {
					logException(e,"Error loading flight dashboard!");
				}
			}
		});
	}

	public static Scene getScene() throws IOException {
		return getSceneForFile("View_Ticket.fxml");
	}

	@FXML
	private Label username;

	@FXML
	private Button backButton;

	@FXML
	private Label passengerName;

	@FXML
	private Label phoneNo;

	@FXML
	private Label email;

	@FXML
	private Label idType;

	@FXML
	private Label idId;

	@FXML
	private Label seatNo;

	@FXML
	private Label age;

	@FXML
	private Label gender;

	@FXML
	private Label flightName;

	@FXML
	private Label flightNo;

	@FXML
	private Label flightSource;

	@FXML
	private Label flightDestination;

	@FXML
	private Label arrivalTime;

	@FXML
	private Label departureTime;

	@FXML
	private Label ticketPrice;
}
