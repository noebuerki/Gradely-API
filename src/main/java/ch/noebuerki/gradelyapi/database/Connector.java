package ch.noebuerki.gradelyapi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
	private static final String DATABASE_URL = "jdbc:mysql://10.10.3.3/gradely";
	private static final String USERNAME = "app";
	private static final String PASSWORD = "app";
	private static final String MAX_POOL = "250";

	private Connection connection;
	private Properties properties;

	public Connection connect() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(DATABASE_URL, getProperties());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	private Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			properties.setProperty("user", USERNAME);
			properties.setProperty("password", PASSWORD);
			properties.setProperty("MaxPooledStatements", MAX_POOL);
		}
		return properties;
	}

	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}