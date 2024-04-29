package io.github.bosev.flight_booking_gradle;

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
}

