package io.github.bosev.flight_booking_gradle;

import java.time.LocalDateTime;

public class FlightPassengerCombo {
	public Flight flight;
	public Passenger passenger;

	public FlightPassengerCombo(Flight flight, Passenger passenger) {
		this.flight = flight;
		this.passenger = passenger;
	}

	public String getPassengerName() {
		return passenger.passengerName;
	}

	public String getFlightName() {
		return flight.flightName;
	}

	public LocalDateTime getDepartureDate() {
		return flight.departure;
	}
}
