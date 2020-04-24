//package dccan.server.control;
//
//import org.apache.commons.lang3.RandomStringUtils;
//
//public class Member {
//	protected String ip, token, id;// ip nguoi gui , id he thong , ma tooken tam thoi
//	private long time;// thoi gian hoat dong , qua 10p la xoa
//	private static long MaxTime = 10 * 60 * 1000;
//
//	public Member(String sip, String sid) {
//		ip = sip;
//		id = sid;
//		time = System.currentTimeMillis();
//		token = RandomStringUtils.randomAlphanumeric(30);
//	}
//
//	public boolean checkToken(String token) {
//		if (this.token.equals(token)) {
//			time = System.currentTimeMillis();// tao moi thoi gian
//			return true;
//		}
//		return false;
//	}
//
//	/**
//	 * xem qua han su dung hay chua
//	 * 
//	 * @return
//	 */
//	public boolean outDate() {
//		if (System.currentTimeMillis() - time > MaxTime)
//			return true;
//		return false;
//	}
//
//	public String getIdByToken(String token) {
//		if (this.token.equals(token)) {
//			time = System.currentTimeMillis();// tao moi thoi gian
//			return id;
//		}
//		return null;
//	}
//
//	public String getId() {
//		time = System.currentTimeMillis();
//		return id;
//	}
//}
