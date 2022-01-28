package ch.gradely.api.database.repositories;

import ch.gradely.api.database.Connector;
import ch.gradely.api.database.objects.BaseObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseRepository<T> {

	protected static final Connector connector = new Connector();
	public static String COLUMN_ID = "id";
	protected String tableName;

	public BaseRepository(String tableName) {
		this.tableName = tableName;
	}

	public List<T> readAll() {
		try {
			PreparedStatement statement = connector.connect().prepareStatement("SELECT * FROM " + tableName);

			ResultSet resultSet = statement.executeQuery();

			connector.disconnect();

			return processAllColumns(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected abstract List<T> processAllColumns(ResultSet resultSet);

	public List<T> readByColumn(String column, String value) {
		try {
			PreparedStatement statement = connector.connect().prepareStatement("SELECT * FROM " + tableName + " WHERE " + column + " = ?");

			if (column.equals("id")) {
				statement.setInt(1, Integer.parseInt(value));
			} else {
				statement.setString(1, value);
			}

			ResultSet resultSet = statement.executeQuery();

			connector.disconnect();

			return processAllColumns(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<T> readBy2Columns(String column1, String value1, String column2, String value2) {
		try {
			PreparedStatement statement = connector.connect().prepareStatement("SELECT * FROM " + tableName + " WHERE " + column1 + " = ? AND " + column2 + " = ?");

			statement.setString(1, value1);
			statement.setString(2, value2);

			ResultSet resultSet = statement.executeQuery();

			connector.disconnect();

			return processAllColumns(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<T> readBy3Columns(String column1, String value1, String column2, String value2, String column3, String value3) {
		try {
			PreparedStatement statement = connector.connect().prepareStatement("SELECT * FROM " + tableName + " WHERE " + column1 + " = ? AND " + column2 + " = ? AND " + column3 + " = ?");

			statement.setString(1, value1);
			statement.setString(2, value2);
			statement.setString(3, value3);

			ResultSet resultSet = statement.executeQuery();

			connector.disconnect();

			return processAllColumns(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean insert(T row) {
		Connection connection = connector.connect();
		try {
			PreparedStatement statement = this.fillInsertStatement(connection.prepareStatement(createInsertStatement()), row);

			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	protected abstract PreparedStatement fillInsertStatement(PreparedStatement statement, T row);
	protected abstract PreparedStatement fillUpdateStatement(PreparedStatement statement, T row);

	protected abstract String createInsertStatement();

	public boolean update(T row) {
		Connection connection = connector.connect();
		try {
			PreparedStatement statement = this.fillUpdateStatement(connection.prepareStatement(createUpdateStatement()), row);

			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	protected abstract String createUpdateStatement();

	public boolean delete(BaseObject row) {
		Connection connection = connector.connect();
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
			statement.setInt(1, row.getId());

			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
