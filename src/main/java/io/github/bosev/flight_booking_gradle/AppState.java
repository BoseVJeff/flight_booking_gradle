package io.github.bosev.flight_booking_gradle;

public class AppState {
	private static AppState instance=null;

	public User user;

	public static AppState getInstance() {
		if(instance==null) {
			instance=new AppState();
		}
		return instance;
	}

	private AppState() {}
}
