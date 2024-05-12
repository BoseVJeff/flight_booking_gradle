package io.github.bosev.flight_booking_gradle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FlightBookingController extends FxSceneBase implements Initializable {
	private Database database;
	private AppState appState;
	private Navigator navigator;
	private ObservableList<String> fromCities= FXCollections.observableArrayList();
	private String selectedFromCity=null;
	private ObservableList<String> toCities=FXCollections.observableArrayList();
	private String selectedToCity=null;
	private ObservableList<Flight> flights=FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.appState=AppState.getInstance();
		this.navigator=Navigator.getInstance();

		this.usernameLabel.setText(this.appState.user.name);

		try {
			this.database=Database.getInstance();
		} catch (SQLException e) {
			System.err.println("Error getting database instance!");
//			this.errorLabel.setText("Error getting database instance!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
			return;
		}
		this.fromComboBox.setItems(this.fromCities);
		try {
			fromCities.setAll(this.database.getSourceCities());
		} catch (SQLException e) {
			System.err.println("Error getting source cities!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
		}
		this.fromComboBox.getSelectionModel().selectedItemProperty().addListener((options,oldValue,newValue)->{
			System.out.println("Changed fromComboBox from "+oldValue+" to "+newValue);
			this.selectedFromCity=newValue;
			if(selectedFromCity!=null&selectedToCity!=null) {
				System.out.println("Getting flights!");
				try {
					this.flights.setAll(this.database.getFlights(selectedFromCity,selectedToCity));
				} catch (SQLException e) {
					System.err.println("SQL Exception: Unable to get flights!");
					System.err.println(e.getMessage());
					for (int i = 0; i < e.getStackTrace().length; i++) {
						System.err.println(e.getStackTrace()[i].toString());
					}
				}
			}
		});


		this.toComboBox.setItems(this.toCities);
		try {
			toCities.setAll(this.database.getDestinationCities());
		} catch (SQLException e) {
			System.err.println("Error getting destination cities!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
		}
		this.toComboBox.getSelectionModel().selectedItemProperty().addListener((options,oldValue,newValue)->{
			System.out.println("Changed toComboBox from "+oldValue+" to "+newValue);
			this.selectedToCity=newValue;
			if(selectedFromCity!=null&selectedToCity!=null) {
				System.out.println("Getting flights!");
				try {
					this.flights.setAll(this.database.getFlights(selectedFromCity,selectedToCity));
				} catch (SQLException e) {
					System.err.println("SQL Exception: Unable to get flights!");
					System.err.println(e.getMessage());
					for (int i = 0; i < e.getStackTrace().length; i++) {
						System.err.println(e.getStackTrace()[i].toString());
					}
				}
			}
		});

		try {
			this.noColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
			this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("flightName"));
			this.airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airline"));
			this.arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrival"));
			this.departureColumn.setCellValueFactory(new PropertyValueFactory<>("departure"));
			this.priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
			this.availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
			this.statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		} catch (Exception e) {
			System.err.println("Error setting value factories!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
		}

		this.optionsTable.setItems(this.flights);
		this.optionsTable.setPlaceholder(new Text("No Flights available!"));

		this.optionsTable.getSelectionModel().getSelectedCells().addListener(new ListChangeListener<TablePosition>() {
			@Override
			public void onChanged(Change<? extends TablePosition> c) {
				while(c.next()) {
					for (TablePosition tablePosition : c.getAddedSubList()) {
						System.out.println("Selected flight"+flights.get(tablePosition.getRow()).flightName);
						appState.selectedFlight=flights.get(tablePosition.getRow());
						optionsTable.getSelectionModel().clearSelection();
						try {
							navigator.push(PassengerShowController.getScene());
						} catch (IOException e) {
							System.err.println("["+this.getClass().getName()+"] "+"Unable to load PassengerShowController!");
							System.err.println(e.getMessage());
							for (int i = 0; i < e.getStackTrace().length; i++) {
								System.err.println(e.getStackTrace()[i].toString());
							}
						}

					}
				}
			}
		});

		this.backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					navigator.push(FlightDashboardController.getScene());
				} catch (IOException e) {
					logException(e,"Unable to push FlightDashboardController!");
				}
			}
		});
	}

	public static Scene getScene() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(FlightBookingController.class.getResource("Flight_booking.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		return scene;
	}

	@FXML
	private Button backButton;

	@FXML
	private ComboBox<String> fromComboBox;

	@FXML
	private ComboBox<String> toComboBox;

	@FXML
	private TableView<Flight> optionsTable;

	@FXML
	private TableColumn<Flight,String> noColumn;

	@FXML
	private TableColumn<Flight,String> nameColumn;

	@FXML
	private TableColumn<Flight,String> airlineColumn;

	@FXML
	private TableColumn<Flight, LocalDateTime> arrivalColumn;

	@FXML
	private TableColumn<Flight,LocalDateTime> departureColumn;

	@FXML
	private TableColumn<Flight, Double> priceColumn;

	@FXML
	private TableColumn<Flight, Integer> availableSeatsColumn;

	@FXML
	private TableColumn<Flight,FlightStatus> statusColumn;

	@FXML
	private Label usernameLabel;
}
