package io.github.bosev.flight_booking_gradle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FlightDashboardController extends FxSceneBase implements Initializable {
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
			System.err.println("["+this.getClass().getName()+"]"+"Exception binding boolean!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
		}

		try {
//			Media media=new Media("file:src/main/resources/io/github/bosev/flight_booking_gradle/dashboard_video.mp4");
			Media media=new Media(Objects.requireNonNull(this.getClass().getResource("dashboard_video.mp4")).toURI().toURL().toString());
			MediaPlayer mediaPlayer=new MediaPlayer(media);
			mediaPlayer.setAutoPlay(true);
			this.topMediaView.setMediaPlayer(mediaPlayer);
			this.topMediaView.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
				@Override
				public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
					topMediaView.setFitWidth(newValue.getWidth());
				}
			});
		} catch (Exception e) {
			System.err.println("["+this.getClass().getName()+"]"+"Exception binding boolean!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
		}
	}



	@FXML
	protected void mediaErrorHandler() {
		System.out.println("Media playback error!");
	}

	@FXML
	protected void bookFlightsButtonAction() {
		try {
			this.navigator.push(FlightBookingController.getScene());
		} catch (IOException e) {
			System.err.println("Error loading Flight booking page!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
		}
	}

	@FXML
	protected void viewTicketsButtonAction() {
		try {
			navigator.push(AllTicketsController.getScene());
		} catch (IOException e) {
			logException(e,"Error loading all tickets page!");
		}
	}

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

	@FXML
	private MediaView leftMediaView;

	@FXML
	private MediaView topMediaView;
}
