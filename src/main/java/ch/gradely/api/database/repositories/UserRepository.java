package ch.gradely.api.database.repositories;

import ch.gradely.api.database.objects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends BaseRepository<User> {

	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_ADMIN = "admin";

	public UserRepository() {
		super("user");
	}

	@Override
	protected List<User> processAllColumns(ResultSet resultSet) {
		List<User> users = new ArrayList<>();
		try {
			while (resultSet.next()) {
				users.add(new User(
						resultSet.getInt(COLUMN_ID),
						resultSet.getString(COLUMN_USERNAME),
						resultSet.getString(COLUMN_EMAIL),
						resultSet.getString(COLUMN_PASSWORD),
						resultSet.getBoolean(COLUMN_ADMIN)
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	protected PreparedStatement fillInsertStatement(PreparedStatement statement, User row) {
		try {
			statement.setString(1, row.getUsername());
			statement.setString(2, row.getEmail());
			statement.setString(3, row.getPassword());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	@Override
	protected PreparedStatement fillUpdateStatement(PreparedStatement statement, User row) {
		try {
			statement.setString(1, row.getUsername());
			statement.setString(2, row.getEmail());
			statement.setString(3, row.getPassword());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	@Override
	protected String createInsertStatement() {
		return "INSERT INTO " + tableName + " (" + COLUMN_USERNAME + ", " + COLUMN_EMAIL + ", " + COLUMN_PASSWORD + ") VALUES (?, ?, ?)";
	}

	@Override
	protected String createUpdateStatement() {
		return "UPDATE " + tableName + " set " + COLUMN_USERNAME + " = ?, " + COLUMN_EMAIL + " = ?, " + COLUMN_PASSWORD + " = ?";
	}
}
