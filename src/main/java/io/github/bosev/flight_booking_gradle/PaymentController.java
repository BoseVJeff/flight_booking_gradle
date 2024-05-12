package io.github.bosev.flight_booking_gradle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class PaymentController extends FxSceneBase implements Initializable {
	private AppState appState;
	private Navigator navigator;

	public PaymentController() {
		this.appState=AppState.getInstance();
		this.navigator=Navigator.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			this.payButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					appState.setPaymentId(UUID.randomUUID().toString());
					try {
						navigator.push(PaymentConfirmController.getScene());
					} catch (IOException e) {
						logException(e,"Error loading payment conformation!");
					}
				}
			});
		} catch (Exception e) {
			logException(e,"Error initing PaymentController!");
		}
	}

	public static Scene getScene() throws IOException {
		return getSceneForFile("Payment.fxml");
	}

	@FXML
	private Button payButton;
}
