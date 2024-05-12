package io.github.bosev.flight_booking_gradle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Seat implements Initializable {

	public Seat() {
	}

	public static Scene getScene() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Seat.class.getResource("Seat.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		return scene;
	}

	@FXML
	private ScrollPane vBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GridPane gridPane=new GridPane();
		int numSeats=101;
		int rowIndex=0;
		double btnWidth=50;
		Text eSlt1=new Text();
		eSlt1.prefWidth(btnWidth);
		Text eSlt2=new Text();
		eSlt2.prefWidth(btnWidth);
		Text eSlt3=new Text();
		eSlt3.prefWidth(btnWidth);
		try {
			while(numSeats>0) {
				if(numSeats>=4) {
					//				All four columns will be occupied
					Button btn1 = new Button(Integer.toString(numSeats));
					btn1.setPrefWidth(btnWidth);
					Button btn2 = new Button(Integer.toString(numSeats - 1));
					btn2.setPrefWidth(btnWidth);
					Button btn3 = new Button(Integer.toString(numSeats - 2));
					btn3.setPrefWidth(btnWidth);
					Button btn4 = new Button(Integer.toString(numSeats - 3));
					btn4.setPrefWidth(btnWidth);
					gridPane.addRow(rowIndex, btn1, btn2, btn3, btn4);
					numSeats-=4;
				}
				else if (numSeats==3) {
//					One left empty
					Button btn1 = new Button(Integer.toString(numSeats));
					btn1.setPrefWidth(btnWidth);
					Button btn2 = new Button(Integer.toString(numSeats - 1));
					btn2.setPrefWidth(btnWidth);
					Button btn3 = new Button(Integer.toString(numSeats - 2));
					btn3.setPrefWidth(btnWidth);
					gridPane.addRow(rowIndex,btn1,btn2,eSlt1,btn3);
					numSeats-=3;
				} else if (numSeats == 2) {
//					Two left empty
					Button btn1 = new Button(Integer.toString(numSeats));
					btn1.setPrefWidth(btnWidth);
					Button btn2 = new Button(Integer.toString(numSeats - 1));
					btn2.setPrefWidth(btnWidth);
					gridPane.addRow(rowIndex,btn1,eSlt1,eSlt2,btn2);
					numSeats-=2;
				} else if (numSeats == 1) {
//					Three left empty
					Button btn1 = new Button(Integer.toString(numSeats));
					btn1.setPrefWidth(btnWidth);
					gridPane.addRow(rowIndex,btn1,eSlt1,eSlt2,eSlt3);
					numSeats-=1;
				} else {
//					None or invalid seats
					break;
				}
				rowIndex++;
			}
			gridPane.setHgap(0.1f);
			gridPane.setVgap(2f);
			gridPane.setAlignment(Pos.CENTER);
			this.vBox.setContent(gridPane);
		} catch (Exception e) {
			System.err.println("Error setting seat grid!");
			System.err.println(e.getMessage());
			for (int i = 0; i < e.getStackTrace().length; i++) {
				System.err.println(e.getStackTrace()[i].toString());
			}
		}
	}
}
