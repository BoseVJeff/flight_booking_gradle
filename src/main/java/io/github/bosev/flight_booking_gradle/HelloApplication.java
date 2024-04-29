package io.github.bosev.flight_booking_gradle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		Navigator navigator=Navigator.getInstance(stage);
		navigator.startNavigator();
		try {
			Database database=Database.getInstance();
			database.test();
		} catch (SQLException e) {
			System.out.println("Error getting database instance or test!");
		}
//		navigator.push(scene);
//		navigator.push(HelloController.getParent());
		navigator.push(HelloController.getScene());
	}

	public static void main(String[] args) {
		launch();
	}
}