package ch.gradely.api.database.repositories;

import ch.gradely.api.database.objects.Semester;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SemesterRepository extends BaseRepository<Semester> {

	public static String COLUMN_SUBJECT_ID = "subject_id";
	public static String COLUMN_USER_ID = "user_id";
	public static String COLUMN_SEMESTER = "semester";

	public SemesterRepository() {
		super("semester-subject");
	}

	@Override
	protected List<Semester> processAllColumns(ResultSet resultSet) {
		List<Semester> semester = new ArrayList<>();
		try {
			while (resultSet.next()) {
				semester.add(new Semester(
						resultSet.getInt(COLUMN_ID),
						resultSet.getInt(COLUMN_SUBJECT_ID),
						resultSet.getInt(COLUMN_USER_ID),
						resultSet.getInt(COLUMN_SEMESTER)
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return semester;
	}

	@Override
	protected PreparedStatement fillInsertStatement(PreparedStatement statement, Semester row) {
		try {
			statement.setInt(1, row.getSubjectId());
			statement.setInt(2, row.getUserId());
			statement.setInt(2, row.getSemester());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	@Override
	protected PreparedStatement fillUpdateStatement(PreparedStatement statement, Semester row) {
		try {
			statement.setInt(1, row.getSemester());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	@Override
	protected String createInsertStatement() {
		return "INSERT INTO " + tableName + " (" + COLUMN_SUBJECT_ID + ", " + COLUMN_USER_ID + ", " + COLUMN_SEMESTER + ") VALUES (?, ?, ?)";
	}

	@Override
	protected String createUpdateStatement() {
		return "UPDATE " + tableName + " set " + COLUMN_SEMESTER + " = ?";
	}
}
