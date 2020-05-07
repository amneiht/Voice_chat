package dccan.server.control.user;

public class Member {
	private String  id;// ip nguoi gui , id he thong , ma tooken tam thoi
	protected long time;// thoi gian hoat dong , qua 10p la xoa
	private static long MaxTime = 10 * 60 * 1000;

	public Member( String sid) {
		
		id = sid;
		time = System.currentTimeMillis();
	}


	/**
	 * xem qua han su dung hay chua
	 * 
	 * @return
	 */
	public boolean outDate() {
		if (System.currentTimeMillis() - time > MaxTime)
			return true;
		return false;
	}

	

	public String getId() {
		time = System.currentTimeMillis();
		return id;
	}
}