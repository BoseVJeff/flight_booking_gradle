package io.github.bosev.flight_booking_gradle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Flight {
	public String no;
	public String name;
	public String airline;
	public String source;
	public String destination;
	public LocalDateTime arrival;
	public LocalDateTime departure;
	public double price;
	public int availableSeats;
	public FlightStatus status;
	public int maxCapacity;

	public Flight(String flightNumber,String flightName,String airline,String source,String destination,LocalDateTime arrival,LocalDateTime departure,double price,int availableSeats,FlightStatus status,int maxCapacity) {
		this.airline=airline;
		this.arrival=arrival;
		this.no=flightNumber;
		this.availableSeats=availableSeats;
		this.departure=departure;
		this.destination=destination;
		this.maxCapacity=maxCapacity;
		this.name=flightName;
		this.price=price;
		this.source=source;
		this.status=status;
	}

	public Flight(ResultSet set) throws SQLException {
			this.no=set.getString("flight_no");
			this.name=set.getString("flight_name");
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
}
