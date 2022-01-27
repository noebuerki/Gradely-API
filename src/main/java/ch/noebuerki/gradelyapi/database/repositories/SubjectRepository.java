package ch.noebuerki.gradelyapi.database.repositories;

import ch.noebuerki.gradelyapi.database.objects.Subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepository extends BaseRepository<Subject> {

	public static String COLUMN_NAME = "name";
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
						resultSet.getInt(COLUMN_SCHOOL_ID)
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjects;
	}

	@Override
	protected PreparedStatement fillStatement(PreparedStatement statement, Subject row) {
		try {
			statement.setString(1, row.getName());
			statement.setInt(2, row.getSchoolId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	@Override
	protected String createInsertStatement() {
		return "INSERT INTO " + tableName + " (" + COLUMN_NAME + ", " + COLUMN_SCHOOL_ID + ") VALUES (?, ?)";
	}

	@Override
	protected String createUpdateStatement() {
		return "UPDATE " + tableName + " set " + COLUMN_NAME + " = ?, " + COLUMN_SCHOOL_ID + " = ?";
	}
}
