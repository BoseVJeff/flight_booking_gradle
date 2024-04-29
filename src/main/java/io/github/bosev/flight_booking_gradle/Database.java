package io.github.bosev.flight_booking_gradle;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;
import java.time.LocalDateTime;

public class Database {
	Connection connection;

	public static Database instance;

	private static String flightTableSql="";
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
