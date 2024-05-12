package io.github.bosev.flight_booking_gradle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class AppState {
	private static AppState instance=null;

	public User user;

	public Flight selectedFlight;

	public ObservableList<Passenger> passengers= FXCollections.observableArrayList();

	public SimpleIntegerProperty seatObs=new SimpleIntegerProperty(-1);

	public Passenger selectedTicket;
	public Flight selectedTicketFlight;

	public static AppState getInstance() {
		if(instance==null) {
			instance=new AppState();
		}
		return instance;
	}

	private AppState() {}

	public void setPaymentId(String id) {
		for (Passenger passenger : passengers) {
			passenger.paymentId=id;
		}
	}
}
