package io.github.bosev.flight_booking_gradle;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.time.LocalDateTime;

public class Database {
	Connection connection;

	public static Database instance;

	private static final String flightTableSql= """
			BEGIN
			    EXECUTE IMMEDIATE 'CREATE TABLE Flights (
			    flight_no VARCHAR2(7) PRIMARY KEY,
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
			    CONSTRAINT check_status CHECK (status IN ("SCHEDULED", "COMPLETED", "CANCELLED")),
			    CONSTRAINT check_source CHECK (source != destination),
			    CONSTRAINT check_departure CHECK (departure_time < arrival_time),
			    CONSTRAINT check_seats_capacity CHECK (available_seats <= max_capacity)
			    )';
			EXCEPTION WHEN OTHERS THEN
			    IF SQLCODE != -955 THEN
			    RAISE;
			END IF;
			END;
			""";

	private static final String userTableSql= """
			BEGIN
			EXECUTE IMMEDIATE '
			CREATE TABLE Users (
				user_id NUMBER PRIMARY KEY,
				username VARCHAR2(255) NOT NULL,
				password VARCHAR2(255) NOT NULL,
				email VARCHAR2(255) NOT NULL,
				phone_number VARCHAR(20),
				isadmin NUMBER(1) NOT NULL CHECK (isadmin IN (0, 1)),
				CONSTRAINT unique_username UNIQUE (username),
				CONSTRAINT unique_email UNIQUE (email),\
				CONSTRAINT unique_phone_number UNIQUE (phone_number)
			)';
			EXCEPTION WHEN OTHERS THEN
				IF SQLCODE != -955 THEN
					RAISE;
				END IF;
			END;
			""";

	public static final String passengersTableSql= """
			BEGIN
			EXECUTE IMMEDIATE 'CREATE TABLE Passengers (
				passenger_id NUMBER PRIMARY KEY,
				name VARCHAR2(250) NOT NULL,
				identification_type VARCHAR2(250) NOT NULL,
				identification_id VARCHAR2(250) NOT NULL,
				flight_no VARCHAR2(7),
				seat_no NUMBER,user_id NUMBER,
				notes VARCHAR2(250),
				payment_id VARCHAR2(250),
				age NUMBER,
				gender VARCHAR2(8),
			    CONSTRAINT gender_constraint CHECK (gender IN (''Male'',''Female'')),
			    FOREIGN KEY (flight_no) REFERENCES Flights(flight_no),
			    FOREIGN KEY (user_id) REFERENCES Users(user_id)
				)';
			EXCEPTION WHEN OTHERS THEN
				IF SQLCODE != -955 THEN
					RAISE;
				END IF;
			END;
			""";

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

//		Initialising database tables
		PreparedStatement flightPreparedStatement=this.connection.prepareStatement(flightTableSql);
		PreparedStatement userPreparedStatement=this.connection.prepareStatement(userTableSql);
		PreparedStatement passengerPreparedStatement=this.connection.prepareStatement(passengersTableSql);

		flightPreparedStatement.execute();
		userPreparedStatement.execute();
		passengerPreparedStatement.execute();
	}

	private void initTables () {

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
