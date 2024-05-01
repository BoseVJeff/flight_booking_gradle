package io.github.bosev.flight_booking_gradle;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	public int id;
	public String name;
	public String email;
	public String phoneNumber;
	public boolean isAdmin;

	public User(int id, String name, String email, String phoneNumber, boolean isAdmin) {
		this.id = id;
		this.email = email;
		this.isAdmin = isAdmin;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public User(ResultSet set) throws SQLException {
		this.id= set.getInt("user_id");
		this.name=set.getString("username");
		this.email=set.getString("email");
		this.phoneNumber=set.getString("phone_number");
		this.isAdmin=set.getInt("isadmin")==1;
	}

	public int getId() {
		System.out.println("ID getter!");
		return this.id;
	}

	public String getName() {
		System.out.println("Name getter!");
		return this.name;
	}

	public String getEmail() {
		System.out.println("Email getter!");
		return this.email;
	}

	public String getPhoneNumber() {
		System.out.println("Phone getter!");
		return this.phoneNumber;
	}

	public boolean getIsAdmin() {
		System.out.println("IsAdmin getter!");
		return this.isAdmin;
	}
}

