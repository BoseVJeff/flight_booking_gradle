package io.github.bosev.flight_booking_gradle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Flight {
	public int no;
	public String flightName;
	public String airline;
	public String source;
	public String destination;
	public LocalDateTime arrival;
	public LocalDateTime departure;
	public double price;
	public int availableSeats;
	public FlightStatus status;
	public int maxCapacity;

	public Flight(int flightNumber,String flightName,String airline,String source,String destination,LocalDateTime arrival,LocalDateTime departure,double price,int availableSeats,FlightStatus status,int maxCapacity) {
		this.airline=airline;
		this.arrival=arrival;
		this.no=flightNumber;
		this.availableSeats=availableSeats;
		this.departure=departure;
		this.destination=destination;
		this.maxCapacity=maxCapacity;
		this.flightName =flightName;
		this.price=price;
		this.source=source;
		this.status=status;
	}

	public Flight(ResultSet set) throws SQLException {
			this.no=set.getInt("flight_no");
			this.flightName =set.getString("flight_name");
			this.airline=set.getString("airline_name");
			this.source=set.getString("source");
			this.destination=set.getString("destination");
			this.departure=set.getTimestamp("departure_time").toLocalDateTime();
			this.arrival=set.getTimestamp("arrival_time").toLocalDateTime();
			this.price=set.getDouble("price");
			this.availableSeats=set.getInt("available_seats");
			this.status=FlightStatus.fromString(set.getString("status"));
			this.maxCapacity=set.getInt("max_capacity");
	}

	public int getNo() {
		return this.no;
	}

	public String getFlightName() {
		return flightName;
	}

	public String getAirline() {
		return airline;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public LocalDateTime getArrival() {
		return arrival;
	}

	public LocalDateTime getDeparture() {
		return departure;
	}

	public double getPrice() {
		return price;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public FlightStatus getStatus() {
		return status;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}
}
