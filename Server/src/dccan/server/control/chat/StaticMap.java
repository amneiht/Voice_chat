package dccan.server.control.chat;

public class StaticMap {
	static RoomMap rm;
	static {
		System.out.println("create new roommap aka room control");
		rm = new RoomMap();
		//new Thread(rm).start();
	}

	public static RoomMap getRm() {
		return rm;
	}
}
