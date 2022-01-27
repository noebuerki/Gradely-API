package ch.noebuerki.gradelyapi.database.objects;

public class Semester extends BaseObject {
	private int subjectId;
	private int semester;

	public Semester(int id, int subjectId, int semester) {
		super(id);
		this.subjectId = subjectId;
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
}
