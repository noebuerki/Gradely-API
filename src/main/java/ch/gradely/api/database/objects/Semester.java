package ch.gradely.api.database.objects;

public class Semester extends BaseObject {
	private int subjectId;
	private int userId;
	private int semester;

	public Semester(int id, int subjectId, int userId, int semester) {
		super(id);
		this.subjectId = subjectId;
		this.userId = userId;
		this.semester = semester;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
