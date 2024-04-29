package io.github.bosev.flight_booking_gradle;

public enum FlightStatus {
	SCHEDULED,
	COMPLETED,
	CANCELLED,
	UNKNOWN;

	public static FlightStatus fromString(String str) throws IllegalArgumentException {
		String inputString = str.toUpperCase();
		return FlightStatus.valueOf(inputString);
	}
}
