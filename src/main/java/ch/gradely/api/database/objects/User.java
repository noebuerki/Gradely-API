package ch.gradely.api.database.objects;

public class User extends BaseObject {
	private String username;
	private String email;
	private String password;
	private boolean admin;

	public User(int id, String username, String email, String password, boolean admin) {
		super(id);
		this.username = username;
		this.email = email;
		this.password = password;
		this.admin = admin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void isAdmin(boolean admin) {
		this.admin = admin;
	}
}
