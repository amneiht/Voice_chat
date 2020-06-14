package application.radiocell;

public class UserRq {

	private String name;
	private Chose action = Chose.LATE;

	public UserRq(String a)	{
		name = a;
	}

	public String getName() {
		return name;
	}


	public Chose getAction() {
		return action;
	}

	public void setAction(Chose action) {
		this.action = action;
	}
}
