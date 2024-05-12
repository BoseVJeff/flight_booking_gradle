package io.github.bosev.flight_booking_gradle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public abstract class FxSceneBase {

	public static Scene getSceneForFile(String fxmlName) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(FlightBookingController.class.getResource(fxmlName));
		return new Scene(fxmlLoader.load());
	}

	public void logException(Exception e,String message) {
		System.err.println("["+this.getClass().getName()+"] "+message);
		System.err.println(e.getMessage());
		for (int i = 0; i < e.getStackTrace().length; i++) {
			System.err.println(e.getStackTrace()[i].toString());
		}
	}
}
