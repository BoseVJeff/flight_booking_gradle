package io.github.bosev.flight_booking_gradle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {
	public static Parent getParent() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
		return fxmlLoader.load();
	}

	public static Scene getScene() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		return scene;
	}

	@FXML
	private Label welcomeText;

	@FXML
	protected void onHelloButtonClick() {
		System.out.println("Button clicked!");
		welcomeText.setText("Welcome to JavaFX Application!");
	}
}