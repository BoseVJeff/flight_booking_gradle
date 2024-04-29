package io.github.bosev.flight_booking_gradle;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Passenger {
	public int id;
	public String name;
	public String idType;
	public String idId;
	public String flightNo;
	public int seatNo;
	public String notes;
	public String paymentId;
	public int age;
	public Gender gender;

	public Passenger(int id, String name, String idType, String idId, String flightNo, int seatNo, String notes, String paymentId, int age, Gender gender) {
		this.id = id;
		this.name = name;
		this.idType = idType;
		this.idId = idId;
		this.flightNo = flightNo;
		this.seatNo = seatNo;
		this.notes = notes;
		this.paymentId = paymentId;
		this.age = age;
		this.gender = gender;
	}

	public Passenger(ResultSet resultSet) throws SQLException {
			this.id= resultSet.getInt("passenger_id");
			this.name=resultSet.getString("name");
			this.idType=resultSet.getString("identification_type");
			this.idId=resultSet.getString("identification_id");
			this.flightNo=resultSet.getString("flight_no");
			this.seatNo=resultSet.getInt("seat_no");
			this.notes=resultSet.getString("notes");
			this.paymentId=resultSet.getString("payment_id");
			this.age=resultSet.getInt("age");
			this.gender=Gender.valueOf(resultSet.getString("gender"));
	}
}
