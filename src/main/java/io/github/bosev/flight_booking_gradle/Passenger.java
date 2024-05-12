package io.github.bosev.flight_booking_gradle;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Passenger {
	public int passengerId;
	public String passengerName;
	public String idType;
	public String idId;
	public String flightNo;
	public int seatNo;
	public String notes;
	public String paymentId;
	public int age;
	public Gender gender;

	public Passenger(String name, String idType, String idId, String flightNo, int seatNo, String notes, int age, Gender gender) {
		this.passengerId = -1;
		this.passengerName = name;
		this.idType = idType;
		this.idId = idId;
		this.flightNo = flightNo;
		this.seatNo = seatNo;
		this.notes = notes;
		this.paymentId = null;
		this.age = age;
		this.gender = gender;
	}

	public Passenger(ResultSet resultSet) throws SQLException {
			this.passengerId = resultSet.getInt("passenger_id");
			this.passengerName =resultSet.getString("name");
			this.idType=resultSet.getString("identification_type");
			this.idId=resultSet.getString("identification_id");
			this.flightNo=resultSet.getString("flight_no");
			this.seatNo=resultSet.getInt("seat_no");
			this.notes=resultSet.getString("notes");
			this.paymentId=resultSet.getString("payment_id");
			this.age=resultSet.getInt("age");
			this.gender=Gender.valueOf(resultSet.getString("gender"));
	}

	public int getPassengerId() {
		return passengerId;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public String getIdType() {
		return idType;
	}

	public String getIdId() {
		return idId;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public int getSeatNo() {
		return seatNo;
	}

	public String getNotes() {
		return notes;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public int getAge() {
		return age;
	}

	public Gender getGender() {
		return gender;
	}
}
