package io.github.bosev.flight_booking_gradle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

public class DashboardController {
	public DashboardController() {
		this.appState=AppState.getInstance();
		this.username.setText(this.appState.user.name);
	}

	public static Scene getScene() throws IOException {
		System.out.println(DashboardController.class.getResource("hello-view.fxml").toString());
		FXMLLoader fxmlLoader = new FXMLLoader(DashboardController.class.getResource("Dashboard.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		return scene;
	}

	private AppState appState;

	@FXML
	private Label username;
}
