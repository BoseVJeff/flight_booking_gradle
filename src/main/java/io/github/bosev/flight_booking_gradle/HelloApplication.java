package io.github.bosev.flight_booking_gradle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
	private Database database;

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		Navigator navigator=Navigator.getInstance(stage);
		navigator.startNavigator();
		try {
			this.database=Database.getInstance();
			this.database.test();
		} catch (SQLException e) {
			System.out.println("Error getting database instance or test!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
		}
		try {
//			navigator.push(HelloController.getScene());
			navigator.push(LoginController.getScene());
//			navigator.push(Seat.getScene());
		} catch (IOException e) {
			System.err.println("Error loading Login page!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
		}
//		navigator.push(UserDump.getScene());
	}

	@Override
	public void stop() throws Exception {
		if(this.database!=null) {
			this.database.cleanup();
		}
		super.stop();
	}

	public static void main(String[] args) {
		launch();
	}
}