package dccan.server.control.user;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import dccan.mail.SendEmail;
import dccan.server.sql.User;

public class UserToken {
	public static Map<String, UserInfo> op = new HashMap<String, UserInfo>();
	public static Map<String, String> mp2 = new HashMap<String, String>();

	public static void addNew(String ten, String pass, String hoten, String email) {
		if (check(ten))
			return;
		String token = RandomStringUtils.randomNumeric(6);
		SendEmail.sendRegiterConFirm(email, token);
		op.put(token, new UserInfo(ten, pass, hoten, email));
		mp2.put(ten, token);

	}

	public static String confirm(String lp) {
		UserInfo up = getUserByToken(lp);
		if (up == null)
			return null;
		if (User.register(up.getTen(), up.getPass(), up.getEmail(), up.getNguoiDung())) {
			return up.getTen();
		}
		return null;

	}

	static long mtime = 600000;

	public static boolean check(String user) {
		String lp = mp2.get(user);
		if (lp == null)
			return true;
		UserInfo ls = op.get(lp);
		if (System.currentTimeMillis() - ls.time > mtime) {
			op.remove(lp);
			mp2.remove(user);
			return true;
		} else
			return false;
	}

	public static UserInfo getUserByToken(String lg) {
		UserInfo lp = op.remove(lg);
		if (lp == null)
			return null;
		else {
			UserInfo res = null;
			if (System.currentTimeMillis() - lp.getTime() < mtime) {

				res = lp;
			}
			String user = op.remove(lg).getTen();
			mp2.remove(user);
			return res;
		}
	}

	public static void clear() {
		Iterator<Map.Entry<String, UserInfo>> mp = op.entrySet().iterator();
		for (; mp.hasNext();) {
			Map.Entry<String, UserInfo> x = mp.next();
			if (System.currentTimeMillis() - x.getValue().getTime() > mtime) {
				mp.remove();
			}
		}
	}
}
