package io.github.bosev.flight_booking_gradle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PassengerShowController extends FxSceneBase implements Initializable {
	private AppState appState;
	private Navigator navigator;

	public PassengerShowController() {
		super();
		this.appState=AppState.getInstance();
		this.navigator=Navigator.getInstance();
	}

	public static Scene getScene() throws IOException {
		return getSceneForFile("PassengerShow.fxml");
	}

	@FXML
	private Button addPassengerButton;

	@FXML
	private Button paymentButton;

	@FXML
	private Button cancelButton;

	@FXML
	private Label messageLabel;

	@FXML
	private TableView<Passenger> passengerTableView;

	@FXML
	private TableColumn<Passenger,String> nameColumn;

	@FXML
	private TableColumn<Passenger,Integer> ageColumn;

	@FXML
	private TableColumn<Passenger,Gender> genderColumn;

	@FXML
	private TableColumn<Passenger,String> idTypeColumn;

	@FXML
	private TableColumn<Passenger,String> idColumn;

	@FXML
	private TableColumn<Passenger,Integer> seatColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.messageLabel.setAlignment(Pos.CENTER);

		try {
			this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("passengerName"));
			this.ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
			this.genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
			this.idTypeColumn.setCellValueFactory(new PropertyValueFactory<>("idType"));
			this.idColumn.setCellValueFactory(new PropertyValueFactory<>("idId"));
			this.seatColumn.setCellValueFactory(new PropertyValueFactory<>("seatNo"));

			this.passengerTableView.setItems(this.appState.passengers);

			this.addPassengerButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						navigator.push(PassengerController.getScene());
					} catch (IOException e) {
						logException(e,"Error loading PassengerController!");
					}
				}
			});
		} catch (Exception e) {
			logException(e,"Table Error!");
		}

		try {
			this.paymentButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						navigator.push(PaymentController.getScene());
					} catch (IOException e) {
						logException(e,"Unable to load PaymentController!");
					}
				}
			});
		} catch (Exception e) {
			logException(e,"Unable to set paymentButton handler!");
		}

		this.cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				appState.passengers.setAll();
				navigator.pop();
			}
		});
	}
}
