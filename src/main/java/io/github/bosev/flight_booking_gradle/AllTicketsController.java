package io.github.bosev.flight_booking_gradle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AllTicketsController extends FxSceneBase implements Initializable {
	private AppState appState;
	private Database database;
	private Navigator navigator;
	private ObservableList<FlightPassengerCombo> flightPassengerCombos= FXCollections.observableArrayList();

	public AllTicketsController() {
		this.appState=AppState.getInstance();
		this.navigator=Navigator.getInstance();
		try {
			this.database=Database.getInstance();
		} catch (SQLException e) {
			logException(e,"Error getting database instance!");
		}
	}

	public static Scene getScene() throws IOException {
		return getSceneForFile("AllTickets.fxml");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			ArrayList<Passenger> passengers=database.getTickets(appState.user.id);
			for (Passenger passenger : passengers) {
				Flight flight=database.getFlightById(passenger.flightId);
				flightPassengerCombos.add(new FlightPassengerCombo(flight,passenger));
			}
		} catch (SQLException e) {
			logException(e,"SQL Error getting tickets!");
		}

		try {
			this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("passengerName"));
			this.flightColumn.setCellValueFactory(new PropertyValueFactory<>("flightName"));
			this.departureDate.setCellValueFactory(new PropertyValueFactory<>("departureDate"));

			this.tableView.setItems(flightPassengerCombos);

			this.tableView.getSelectionModel().getSelectedCells().addListener(new ListChangeListener<TablePosition>() {
				@Override
				public void onChanged(Change<? extends TablePosition> c) {

					while (c.next()) {
						for (TablePosition tablePosition : c.getAddedSubList()) {
							appState.selectedTicket=flightPassengerCombos.get(tablePosition.getRow()).passenger;
							appState.selectedTicketFlight=flightPassengerCombos.get(tablePosition.getRow()).flight;
							try {
								navigator.push(ViewTicket.getScene());
							} catch (IOException e) {
								logException(e,"Error pushing ticket view!");
							}
						}
					}
				}
			});
		} catch (Exception e) {
			logException(e,"Error setting up all tickets table!");
		}
	}

	@FXML
	private TableView<FlightPassengerCombo> tableView;

	@FXML
	private TableColumn<FlightPassengerCombo,String> nameColumn;

	@FXML
	private TableColumn<FlightPassengerCombo,String> flightColumn;

	@FXML
	private TableColumn<FlightPassengerCombo, LocalDateTime> departureDate;
}
