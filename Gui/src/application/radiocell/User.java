package application.radiocell;

public class User {

	private String name;
	private Chose action = Chose.MAYBE;

	public User(String a)	{
		name = a;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Chose getAction() {
		return action;
	}

	public void setAction(Chose action) {
		this.action = action;
	}
}
