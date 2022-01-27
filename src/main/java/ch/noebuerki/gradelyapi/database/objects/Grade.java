package ch.noebuerki.gradelyapi.database.objects;

public class Grade extends BaseObject {
	private int userId;
	private int semesterId;
	private String name;
	private double value;
	private double weight;

	public Grade(int id, int userId, int semesterId, String name, double value, double weight) {
		super(id);
		this.userId = userId;
		this.semesterId = semesterId;
		this.name = name;
		this.value = value;
		this.weight = weight;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
