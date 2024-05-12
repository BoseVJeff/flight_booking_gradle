package io.github.bosev.flight_booking_gradle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PassengerController extends FxSceneBase implements Initializable {
	private AppState appState;
	private Navigator navigator;

	public PassengerController() {
		this.appState=AppState.getInstance();
		this.navigator=Navigator.getInstance();
	}

	public static Scene getScene() throws IOException {
		return getSceneForFile("passenger.fxml");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.ageSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,200));

		this.usernameLabel.setText(this.appState.user.name);

		this.maleToggle.setUserData(Gender.Male);
		this.femaleToggle.setUserData(Gender.Female);
		this.otherToggle.setUserData(Gender.Other);

		appState.seatObs.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(newValue.intValue()>0) {
					seatButton.setText("Seat "+newValue);
				} else {
					seatButton.setText("Select Seat");
				}
			}
		});

		this.addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Name: "+passengerName.getText());
				System.out.println("Age: "+ageSpinner.getValue());
				System.out.println("Gender: "+gender.getSelectedToggle().getUserData());

				appState.passengers.add(new Passenger(passengerName.getText(),idType.getText(),idId.getText(),appState.selectedFlight.no,appState.seatObs.getValue(),remarksArea.getText(),ageSpinner.getValue(), (Gender) gender.getSelectedToggle().getUserData()));
				navigator.pop();
			}
		});

		this.seatButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					navigator.push(Seat.getScene());
				} catch (IOException e) {
					logException(e,"Unable to load seat selector!");
				}
			}
		});

		this.cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				navigator.pop();
			}
		});
	}

	@FXML
	private TextField passengerName;

	@FXML
	private TextField idType;

	@FXML
	private TextField idId;

	@FXML
	private Button seatButton;

	@FXML
	private Spinner<Integer> ageSpinner;

	@FXML
	private ToggleGroup gender;

	@FXML
	private ToggleButton maleToggle;

	@FXML
	private ToggleButton femaleToggle;

	@FXML
	private ToggleButton otherToggle;

	@FXML
	private TextArea remarksArea;

	@FXML
	private Label usernameLabel;

	@FXML
	private Button addButton;

	@FXML
	private Button cancelButton;
}
