package application.radiocell;

import dccan.suport.Friend;

public class GFriend {
	private String name;
	private AddF action = AddF.NO;
	private String id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AddF getAction() {
		return action;
	}

	public void setAction(AddF action) {
		this.action = action;
	}

	
	public GFriend(Friend a) {
		name = a.getNguoiDung();
		id = a.getTen();
	}
}
