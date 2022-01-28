package ch.gradely.api.database.repositories;

import ch.gradely.api.database.objects.Grade;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeRepository extends BaseRepository<Grade> {

	public static String COLUMN_USER_ID = "user_id";
	public static String COLUMN_SEMESTER_ID = "semester_id";
	public static String COLUMN_NAME = "name";
	public static String COLUMN_VALUE = "value";
	public static String COLUMN_WEIGHT = "weight";

	public GradeRepository() {
		super("grade");
	}

	@Override
	protected List<Grade> processAllColumns(ResultSet resultSet) {
		List<Grade> grades = new ArrayList<>();
		try {
			while (resultSet.next()) {
				grades.add(new Grade(
						resultSet.getInt(COLUMN_ID),
						resultSet.getInt(COLUMN_USER_ID),
						resultSet.getInt(COLUMN_SEMESTER_ID),
						resultSet.getString(COLUMN_NAME),
						resultSet.getDouble(COLUMN_VALUE),
						resultSet.getDouble(COLUMN_WEIGHT)
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return grades;
	}

	@Override
	protected PreparedStatement fillInsertStatement(PreparedStatement statement, Grade row) {
		try {
			statement.setInt(1, row.getUserId());
			statement.setInt(2, row.getSemesterId());
			statement.setString(3, row.getName());
			statement.setDouble(4, row.getValue());
			statement.setDouble(5, row.getWeight());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	@Override
	protected PreparedStatement fillUpdateStatement(PreparedStatement statement, Grade row) {
		try {
			statement.setString(1, row.getName());
			statement.setDouble(2, row.getValue());
			statement.setDouble(3, row.getWeight());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	@Override
	protected String createInsertStatement() {
		return "INSERT INTO " + tableName + " (" + COLUMN_USER_ID + ", " + COLUMN_SEMESTER_ID + ", " + COLUMN_NAME + ", " + COLUMN_VALUE + ", " + COLUMN_WEIGHT + ") VALUES (?, ?, ?, ?, ?)";
	}

	@Override
	protected String createUpdateStatement() {
		return "UPDATE " + tableName + " set " + COLUMN_NAME + " = ?, " + COLUMN_VALUE + " = ?, " + COLUMN_WEIGHT + " = ?";
	}
}
