package io.github.bosev.flight_booking_gradle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class BookingPage {

    @FXML
    VBox vboxForButtons = new VBox();

    public void initialize() {
            for (int i=1;i>10;i++) {
                Button btnNumber = new Button();
                btnNumber.setText(String.valueOf(i));
                btnNumber.setOnAction(actionEvent -> {
                    System.out.println(btnNumber.getText());
                });

                vboxForButtons.getChildren().add(btnNumber);
            }
    }

}
