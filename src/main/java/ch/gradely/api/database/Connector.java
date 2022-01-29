package ch.gradely.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
	private static final String DATABASE_URL = "jdbc:mysql://XXX.XXX.XXX.XXX/gradely";
	private static final String USERNAME = "XXX";
	private static final String PASSWORD = "XXX";
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