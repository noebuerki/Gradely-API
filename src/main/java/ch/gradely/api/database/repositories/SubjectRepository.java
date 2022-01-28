package ch.gradely.api.database.repositories;

import ch.gradely.api.database.objects.Subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepository extends BaseRepository<Subject> {

	public static String COLUMN_NAME = "name";
	public static String COLUMN_USER_ID = "user_id";
	public static String COLUMN_SCHOOL_ID = "school_id";

	public SubjectRepository() {
		super("subject");
	}

	@Override
	protected List<Subject> processAllColumns(ResultSet resultSet) {
		List<Subject> subjects = new ArrayList<>();
		try {
			while (resultSet.next()) {
				subjects.add(new Subject(
						resultSet.getInt(COLUMN_ID),
						resultSet.getString(COLUMN_NAME),
						resultSet.getInt(COLUMN_USER_ID),
						resultSet.getInt(COLUMN_SCHOOL_ID)
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjects;
	}

	@Override
	protected PreparedStatement fillInsertStatement(PreparedStatement statement, Subject row) {
		try {
			statement.setString(1, row.getName());
			statement.setInt(2, row.getUserId());
			statement.setInt(3, row.getSchoolId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	@Override
	protected PreparedStatement fillUpdateStatement(PreparedStatement statement, Subject row) {
		try {
			statement.setString(1, row.getName());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	@Override
	protected String createInsertStatement() {
		return "INSERT INTO " + tableName + " (" + COLUMN_NAME + ", " + COLUMN_USER_ID  +", " + COLUMN_SCHOOL_ID + ") VALUES (?, ?, ?)";
	}

	@Override
	protected String createUpdateStatement() {
		return "UPDATE " + tableName + " set " + COLUMN_NAME + " = ?";
	}
}
