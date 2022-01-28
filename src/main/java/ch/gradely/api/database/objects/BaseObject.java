package ch.gradely.api.database.objects;

public class BaseObject {
	private int id;

	public BaseObject(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
