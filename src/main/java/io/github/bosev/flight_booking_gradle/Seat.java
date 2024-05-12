package io.github.bosev.flight_booking_gradle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Seat extends FxSceneBase implements Initializable {
	private AppState appState;
	private Database database;
	private Navigator navigator;

	public Seat() {
		this.appState=AppState.getInstance();
		try {
			this.database=Database.getInstance();
		} catch (SQLException e) {
			logException(e,"Unable to get database instance!");
		}
		this.navigator=Navigator.getInstance();
	}

	public static Scene getScene() throws IOException {
		return getSceneForFile("Seat.fxml");
	}

	@FXML
	private ScrollPane vBox;

	private EventHandler<ActionEvent> btnHandler(int index) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				appState.seatObs.set(index);
				System.out.println(appState.seatObs.getValue());
				navigator.pop();
			}
		};
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		int numSeats=appState.selectedFlight.maxCapacity;

		GridPane gridPane=new GridPane();
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
					btn1.setOnAction(btnHandler(numSeats));

					Button btn2 = new Button(Integer.toString(numSeats - 1));
					btn2.setPrefWidth(btnWidth);
					btn2.setOnAction(btnHandler(numSeats-1));

					Button btn3 = new Button(Integer.toString(numSeats - 2));
					btn3.setPrefWidth(btnWidth);
					btn3.setOnAction(btnHandler(numSeats-2));

					Button btn4 = new Button(Integer.toString(numSeats - 3));
					btn4.setPrefWidth(btnWidth);
					btn4.setOnAction(btnHandler(numSeats-3));

					gridPane.addRow(rowIndex, btn1, btn2, btn3, btn4);
					numSeats-=4;
				}
				else if (numSeats==3) {
//					One left empty
					Button btn1 = new Button(Integer.toString(numSeats));
					btn1.setPrefWidth(btnWidth);
					btn1.setOnAction(btnHandler(numSeats));

					Button btn2 = new Button(Integer.toString(numSeats - 1));
					btn2.setPrefWidth(btnWidth);
					btn2.setOnAction(btnHandler(numSeats-1));

					Button btn3 = new Button(Integer.toString(numSeats - 2));
					btn3.setPrefWidth(btnWidth);
					btn3.setOnAction(btnHandler(numSeats-2));

					gridPane.addRow(rowIndex,btn1,btn2,eSlt1,btn3);
					numSeats-=3;
				} else if (numSeats == 2) {
//					Two left empty
					Button btn1 = new Button(Integer.toString(numSeats));
					btn1.setPrefWidth(btnWidth);
					btn1.setOnAction(btnHandler(numSeats));

					Button btn2 = new Button(Integer.toString(numSeats - 1));
					btn2.setPrefWidth(btnWidth);
					btn2.setOnAction(btnHandler(numSeats-1));

					gridPane.addRow(rowIndex,btn1,eSlt1,eSlt2,btn2);
					numSeats-=2;
				} else if (numSeats == 1) {
//					Three left empty
					Button btn1 = new Button(Integer.toString(numSeats));
					btn1.setPrefWidth(btnWidth);
					btn1.setOnAction(btnHandler(numSeats));

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
