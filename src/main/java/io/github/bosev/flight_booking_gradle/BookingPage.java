package io.github.bosev.flight_booking_gradle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookingPage implements Initializable {
    @FXML
    private ScrollPane sp;
    @FXML
    private Label Admin;
//    sql sql = new sql();


    @Override
    public void initialize(URL arg, ResourceBundle arg1) {
//        VBox vbox = new VBox();
//        vbox.getChildren().clear();
//        List<Integer> count = getFlightId();
//
//        for (int j = 0; j < count.size(); j++) {
//            int i = count.get(j);
//            Label PN = new Label();
//            PN.setLayoutX(26);
//            PN.setLayoutY(36);
//            PN.setFont(new Font(18));
//            PN.setText(sql.getdata("flight_name", "airline_name", "where pid=" + sql.getdata("flight_no", "flight_info", "where flight_no=" + i)));
////          You can set as per our database
//
//            sp.setContent(vbox);
//        }
    }
}
