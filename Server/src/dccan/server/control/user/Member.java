package dccan.server.control.user;

public class Member {
	private String id;// ip nguoi gui , id he thong , ma tooken tam thoi
	protected long time;// thoi gian hoat dong , qua 10p la xoa
	private static long MaxTime = 24 * 60 * 60 * 1000; // 1 ngay
	private String mail = null;

	public Member(String sid) {

		id = sid;
		time = System.currentTimeMillis();
	}

	public Member(String sid, String em) {
		mail = em;
		id = sid;
		time = System.currentTimeMillis();
	}

	public String getMail() {
		return mail;
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

	public long getTime() {
		return time;
	}

	public String getId() {
		time = System.currentTimeMillis();
		return id;
	}
}