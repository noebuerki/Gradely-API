package ch.noebuerki.gradelyapi.database.objects;

public class School extends BaseObject {
	private String name;
	private int userId;
	private int semester;

	public School(int id, String name, int userId, int semester) {
		super(id);
		this.name = name;
		this.userId = userId;
		this.semester = semester;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}
}
