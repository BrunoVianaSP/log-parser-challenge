package com.ef;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DatabaseConnection {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	private String host = "localhost:3306";
	private String user = "root";
	private String passwd = "123456";
	private String database = "WALLETHUB_DB";

	public DatabaseConnection() {
		// TODO Auto-generated constructor stub
	}

	public DatabaseConnection(String user, String passwd, String host, String database) {
		super();
		this.user = user;
		this.passwd = passwd;
		this.host = host;
		this.database = database;
	}

	private void openDbConnection() throws ClassNotFoundException, SQLException {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database
				+ "?autoReconnect=true&useSSL=false&" + "user=" + user + "&password=" + passwd);
	}

	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

	public void saveAnalysisResult(List<FrequentIp> analysisResult) throws Exception {
		try {
			openDbConnection();

			preparedStatement = connect.prepareStatement("INSERT INTO FrequentIp VALUES (default, ?, ?, ?, default)");
			for (FrequentIp frequentIp : analysisResult) {
				preparedStatement.setString(1, frequentIp.getIp());
				preparedStatement.setInt(2, frequentIp.getFrequency());
				preparedStatement.setString(3, frequentIp.getComments());
				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
	}

	public void saveLogFile(List<Log> logs) throws Exception {

		try {
			openDbConnection();

			preparedStatement = connect.prepareStatement("INSERT INTO Log VALUES (default, ?, ?, ?, ?, ?, default)");
			for (Log log : logs) {
				preparedStatement.setString(1, log.getIp());
				preparedStatement.setString(2, log.getDate());
				preparedStatement.setString(3, log.getRequest());
				preparedStatement.setInt(4, log.getStatus());
				preparedStatement.setString(5, log.getUserAgent());
				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
	}
}