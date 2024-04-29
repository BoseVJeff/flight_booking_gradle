package io.github.bosev.flight_booking_gradle;

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
}
