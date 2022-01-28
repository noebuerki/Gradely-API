package ch.gradely.api.database.objects;

public class Subject extends BaseObject {
	private String name;
	private int userId;
	private int schoolId;

	public Subject(int id, String name, int userId, int schoolId) {
		super(id);
		this.name = name;
		this.userId = userId;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
