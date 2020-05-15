package application.radiocell;

public class GFriend {
	private String name;
	private AddF action = AddF.NO;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AddF getAction() {
		return action;
	}

	public void setAction(AddF action) {
		this.action = action;
	}

	public GFriend(String a)	{
		name = a;
	}
}
