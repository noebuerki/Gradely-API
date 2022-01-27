package ch.noebuerki.gradelyapi.database.objects;

public class Subject extends BaseObject {
	private String name;
	private int schoolId;

	public Subject(int id, String name, int schoolId) {
		super(id);
		this.name = name;
		this.schoolId = schoolId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
}
