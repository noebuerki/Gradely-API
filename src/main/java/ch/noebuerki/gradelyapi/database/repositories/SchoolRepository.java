package ch.noebuerki.gradelyapi.database.repositories;

import ch.noebuerki.gradelyapi.database.objects.School;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SchoolRepository extends BaseRepository<School> {

	public static String COLUMN_NAME = "name";
	public static String COLUMN_USER_ID = "user_id";
	public static String COLUMN_SEMESTER = "semester";

	public SchoolRepository() {
		super("school");
	}

	@Override
	protected List<School> processAllColumns(ResultSet resultSet) {
		List<School> schools = new ArrayList<>();
		try {
			while (resultSet.next()) {
				schools.add(new School(
						resultSet.getInt(COLUMN_ID),
						resultSet.getString(COLUMN_NAME),
						resultSet.getInt(COLUMN_USER_ID),
						resultSet.getInt(COLUMN_SEMESTER)
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return schools;
	}

	@Override
	protected PreparedStatement fillStatement(PreparedStatement statement, School row) {
		try {
			statement.setString(1, row.getName());
			statement.setInt(2, row.getUserId());
			statement.setInt(3, row.getSemester());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	@Override
	protected String createInsertStatement() {
		return "INSERT INTO " + tableName + " (" + COLUMN_NAME + ", " + COLUMN_USER_ID + ", " + COLUMN_SEMESTER + ") VALUES (?, ?, ?)";
	}

	@Override
	protected String createUpdateStatement() {
		return "UPDATE " + tableName + " set " + COLUMN_NAME + " = ?, " + COLUMN_USER_ID + " = ?, " + COLUMN_SEMESTER + " = ?";
	}
}
