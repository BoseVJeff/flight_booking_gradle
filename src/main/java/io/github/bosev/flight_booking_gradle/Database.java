package io.github.bosev.flight_booking_gradle;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Database {
	Connection connection;

	public static Database instance;

//	SQL to create tables. Note that the `GENERATED ALWAYS AS IDENTITY START WITH 0 INCREMENT BY 1` feature is supported only on version 12 and later.

	private static final String flightTableSql= """
			CREATE TABLE Flights (
				flight_no NUMBER GENERATED ALWAYS AS IDENTITY START WITH 0 INCREMENT BY 1 MINVALUE 0 PRIMARY KEY,
				flight_name VARCHAR2(250),
				airline_name VARCHAR2(250),
				source VARCHAR2(250),
				destination VARCHAR2(250),
				departure_time TIMESTAMP NOT NULL,
				arrival_time TIMESTAMP NOT NULL,
				price DECIMAL(10, 2) NOT NULL,
				available_seats NUMBER(5) NOT NULL,
				status VARCHAR2(10) NOT NULL,
				max_capacity NUMBER(5),
				CONSTRAINT check_status CHECK (status IN ('SCHEDULED', 'COMPLETED', 'CANCELLED')),
				CONSTRAINT check_source CHECK (source != destination),
				CONSTRAINT check_departure CHECK (departure_time < arrival_time),
				CONSTRAINT check_seats_capacity CHECK (available_seats <= max_capacity)
				);
			""";

	private static final String userTableSql= """
			CREATE TABLE Users (
				user_id NUMBER GENERATED ALWAYS AS IDENTITY START WITH 0 INCREMENT BY 1 MINVALUE 0 PRIMARY KEY,
				username VARCHAR2(255) NOT NULL,
				password VARCHAR2(255) NOT NULL,
				email VARCHAR2(255) NOT NULL,
				phone_number VARCHAR(20),
				isadmin NUMBER(1) NOT NULL CHECK (isadmin IN (0, 1)),
				CONSTRAINT unique_username UNIQUE (username),
				CONSTRAINT unique_email UNIQUE (email),
				CONSTRAINT unique_phone_number UNIQUE (phone_number)
			);
			""";

	public static final String passengersTableSql= """
			CREATE TABLE Passengers (
				passenger_id NUMBER GENERATED ALWAYS AS IDENTITY START WITH 0 INCREMENT BY 1 MINVALUE 0 PRIMARY KEY,
				name VARCHAR2(250) NOT NULL,
				identification_type VARCHAR2(250) NOT NULL,
				identification_id VARCHAR2(250) NOT NULL,
				flight_no NUMBER,
				seat_no NUMBER,
				user_id NUMBER,
				notes VARCHAR2(250),
				payment_id VARCHAR2(250),
				age NUMBER,
				gender VARCHAR2(8),
			    CONSTRAINT gender_constraint CHECK (gender IN ('Male','Female')),
			    FOREIGN KEY (flight_no) REFERENCES Flights(flight_no),
			    FOREIGN KEY (user_id) REFERENCES Users(user_id)
				);
			""";

	public static final String userCreate="INSERT INTO Users (username, password,email, phone_number, isadmin) VALUES (?,?,?,?,?)";
	public PreparedStatement userCreateStmt;
	public static final String userGet="SELECT user_id,username,email,phone_number,isadmin FROM Users WHERE username=? AND password=?";
	public PreparedStatement userGetStmt;

	public static final String flightCreate="INSERT INTO Flights (flight_name, airline_name,source,destination,departure_time,arrival_time,price,available_seats,status,max_capacity) VALUES (?,?,?,?,?,?,?,?,?)";
	public PreparedStatement flightCreateStmt;

	public static final String passengerCreate="INSERT INTO Passengers (name, identification_type,identification_id,flight_no,seat_no,notes,payment_id,age,gender) VALUES (?,?,?,?,?,?,?,?,?)";
	public PreparedStatement passengerCreateStmt;

	public static final String userGetAll="SELECT user_id,username,email,phone_number,isadmin FROM Users";
	public  PreparedStatement userGetAllPreparedStmt;

	public static final String makeAdminSql ="UPDATE Users SET isadmin=1 WHERE user_id=?";
	public PreparedStatement makeAdminPreparedStmt;

	public static final String removeAdminSql ="UPDATE Users SET isadmin=0 WHERE user_id=?";
	public PreparedStatement removeAdminPreparedStmt;

	public static final String deleteUserSql="DELETE FROM Users WHERE user_id=?";
	public PreparedStatement deleteUserStmt;

	public static final String getFlightsSql="SELECT * FROM Flights WHERE source=? AND destination=?";
	public PreparedStatement getFlightsStmt;

	public  static final String getSourceCitiesSql="SELECT UNIQUE(source) FROM Flights ORDER BY source ASC";
	public PreparedStatement getSourceCitiesStmt;

	public  static final String getDestinationCitiesSql="SELECT UNIQUE(destination) FROM Flights ORDER BY destination ASC";
	public PreparedStatement getDestinationCitiesStmt;

	public static final String getBookedSeatsSql="SELECT seat_no FROM Passengers WHERE flight_no=?";
	public PreparedStatement getBookedSeatsStmt;

	public static final String addPassengerSql="INSERT INTO Passengers (name, identification_type, identification_id, flight_no, seat_no, user_id, notes, payment_id, age, gender) VALUES (?,?,?,?,?,?,?,?,?,?)";
	public PreparedStatement addPassengerStmt;


	private static String wrapSql(String sqlStatement) {
		String sql=sqlStatement.replace("'","''");
		sql=sql.replace(";","");
		return """
				BEGIN
				EXECUTE IMMEDIATE '
				"""+
				sql+
				"""
				';
				EXCEPTION WHEN OTHERS THEN
				IF SQLCODE != -955 THEN
					RAISE;
				END IF;
				END;
				""";
	}

	public static Database getInstance() throws SQLException {
		if(instance==null) {
			instance=new Database();
		}
		return instance;
	}

	private Database() throws SQLException {
		OracleDataSource ods=new OracleDataSource();

		ods.setURL("jdbc:oracle:thin:@//localhost:1521/XE");
		ods.setUser(Credentials.getDatabaseUsernameString());
		ods.setPassword(Credentials.getDatabsePasswordString());

		this.connection=ods.getConnection();

		this.initTables();
	}

	private void initTables () throws SQLException {
//		Initialising database tables
		PreparedStatement flightPreparedStatement=this.connection.prepareStatement(wrapSql(flightTableSql));
		PreparedStatement userPreparedStatement=this.connection.prepareStatement(wrapSql(userTableSql));
		PreparedStatement passengerPreparedStatement=this.connection.prepareStatement(wrapSql(passengersTableSql));

		flightPreparedStatement.execute();
		userPreparedStatement.execute();
		passengerPreparedStatement.execute();

		this.userCreateStmt=this.connection.prepareStatement(userCreate);
		this.userGetStmt=this.connection.prepareStatement(userGet);
		this.flightCreateStmt=this.connection.prepareStatement(flightCreate);
		this.passengerCreateStmt=this.connection.prepareStatement(passengerCreate);
		this.userGetAllPreparedStmt=this.connection.prepareStatement(userGetAll);
		this.makeAdminPreparedStmt=this.connection.prepareStatement(makeAdminSql);
		this.removeAdminPreparedStmt=this.connection.prepareStatement(removeAdminSql);
		this.deleteUserStmt=this.connection.prepareStatement(deleteUserSql);
		this.getFlightsStmt=this.connection.prepareStatement(getFlightsSql);
		this.getSourceCitiesStmt=this.connection.prepareStatement(getSourceCitiesSql);
		this.getDestinationCitiesStmt=this.connection.prepareStatement(getDestinationCitiesSql);
		this.getBookedSeatsStmt=this.connection.prepareStatement(getBookedSeatsSql);
		this.addPassengerStmt=this.connection.prepareStatement(addPassengerSql);
	}

	public void insertUser(String name,String password, String email, String phoneNumber,boolean isAdmin) throws SQLException {
		this.userCreateStmt.setString(1,name);
		this.userCreateStmt.setString(2,password);
		this.userCreateStmt.setString(3,email);
		this.userCreateStmt.setString(4,phoneNumber);
		this.userCreateStmt.setInt(5,isAdmin?1:0);

		this.userCreateStmt.execute();
	}

	public User validateUser(String name, String password) throws SQLException,SQLTimeoutException {
		this.userGetStmt.setString(1,name);
		this.userGetStmt.setString(2,password);

		ResultSet resultSet=this.userGetStmt.executeQuery();
		if(resultSet.next()) {
			return new User(resultSet);
		} else {
			return null;
		}
	}

	public ArrayList<User> getAllUsers() throws SQLException {
		ArrayList<User> usersList=new ArrayList<User>();
		ResultSet resultSet=this.userGetAllPreparedStmt.executeQuery();

		while(resultSet.next()) {
			usersList.add(new User(resultSet));
		}

		return usersList;
	}

	public void makeAdmin(int userId) throws SQLException {
		this.makeAdminPreparedStmt.setInt(1,userId);
		this.makeAdminPreparedStmt.execute();
		return;
	}

	public void removeAdmin(int userId) throws SQLException {
		this.removeAdminPreparedStmt.setInt(1,userId);
		this.removeAdminPreparedStmt.execute();
		return;
	}

	public void deleteUser(int userId) throws SQLException {
		this.removeAdminPreparedStmt.setInt(1,userId);
		this.removeAdminPreparedStmt.execute();
		return;
	}

	public ArrayList<String> getSourceCities() throws SQLException {
		ArrayList<String> cities=new ArrayList<String>();

		ResultSet resultSet=this.getSourceCitiesStmt.executeQuery();

		while(resultSet.next()) {
			cities.add(resultSet.getString(1));
		}

		return cities;
	}

	public ArrayList<String> getDestinationCities() throws SQLException {
		ArrayList<String> cities=new ArrayList<String>();

		ResultSet resultSet=this.getDestinationCitiesStmt.executeQuery();

		while(resultSet.next()) {
			cities.add(resultSet.getString(1));
		}

		return cities;
	}

	public ArrayList<Flight> getFlights(String fromCity, String toCity) throws SQLException {
		ArrayList<Flight> flightArrayList=new ArrayList<Flight>();

		this.getFlightsStmt.setString(1,fromCity);
		this.getFlightsStmt.setString(2,toCity);
		ResultSet resultSet=this.getFlightsStmt.executeQuery();

		while(resultSet.next()) {
			flightArrayList.add(new Flight(resultSet));
		}

		return flightArrayList;
	}

	public ArrayList<Integer> getBookedSeats(int flightNo) throws SQLException {
		ArrayList<Integer> seatsList=new ArrayList<>();

		this.getBookedSeatsStmt.setInt(1,flightNo);
		ResultSet resultSet=this.getBookedSeatsStmt.executeQuery();

		while (resultSet.next()) {
			seatsList.add(resultSet.getInt(1));
		}

		return seatsList;
	}

	public void addPassenger(Passenger passenger,int userId) throws SQLException {
		this.addPassengerStmt.setString(1,passenger.passengerName);
		this.addPassengerStmt.setString(2,passenger.idType);
		this.addPassengerStmt.setString(3,passenger.idId);
		this.addPassengerStmt.setInt(4,passenger.flightId);
		this.addPassengerStmt.setInt(5,passenger.seatNo);
		this.addPassengerStmt.setInt(6,userId);
		this.addPassengerStmt.setString(7,passenger.notes);
		this.addPassengerStmt.setString(8,passenger.paymentId);
		this.addPassengerStmt.setInt(9,passenger.age);
		this.addPassengerStmt.setString(10,passenger.gender.toString());

		this.addPassengerStmt.execute();
	}

	public void test() throws SQLException {
		// Create Oracle DatabaseMetaData object
		DatabaseMetaData meta = this.connection.getMetaData();

		// gets driver info:
		System.out.println("JDBC driver version is " + meta.getDriverVersion());

		String query = "SELECT SYSDATE FROM DUAL";
		Statement statement = connection.createStatement();

		ResultSet rSet = statement.executeQuery(query);

		while (rSet.next()) {
			System.out.println("Current date is " + rSet.getString("SYSDATE"));
		}

		String timeStmapQuery = "SELECT LOCALTIMESTAMP FROM DUAL";
		Statement timeStampStatement = connection.createStatement();
		ResultSet getSet = statement.executeQuery(timeStmapQuery);

		while (getSet.next()) {
//			LocalDateTime localDateTime = LocalDateTime.parse(getSet.getString("LocalTimeStamp"));
			Timestamp timestamp=getSet.getTimestamp("LocalTimeStamp");
			LocalDateTime localDateTime=timestamp.toLocalDateTime();
			System.out.println(localDateTime.getHour());
		}

		getSet.close();
		timeStampStatement.close();

		rSet.close();

		statement.close();
	}

	public void cleanup() throws SQLException {
		this.connection.close();
	}
}
