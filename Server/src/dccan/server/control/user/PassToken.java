package dccan.server.control.user;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PassToken {
	public static Map<String, Member> op = new HashMap<String, Member>();
	public static Map<String, String> mp2 = new HashMap<String, String>();

	public static void addNew(String token, String user) {
		op.put(token, new Member(user));
		mp2.put(user, token);

	}

	public static void addNew(String token, String user, String mail) {
		op.put(token, new Member(user, mail));
		mp2.put(user, token);

	}

	static long mtime = 600000;

	public static boolean check(String user) {
		String lp = mp2.get(user);
		if (lp == null)
			return true;
		Member ls = op.get(lp);
		if (System.currentTimeMillis() - ls.time > mtime) {
			op.remove(lp);
			mp2.remove(user);
			return true;
		} else
			return false;
	}

	public static Member getUserByToken(String lg) {
		Member lp = op.remove(lg);
		if (lp == null)
			return null;
		else {
			Member res = null;
			if (System.currentTimeMillis() - lp.getTime() < mtime) {

				res = lp;
			}
			String user = lp.getId();
			mp2.remove(user);
			return res;
		}
	}

	public static void clear() {
		Iterator<Map.Entry<String, Member>> mp = op.entrySet().iterator();
		for (; mp.hasNext();) {
			Map.Entry<String, Member> x = mp.next();
			if (System.currentTimeMillis() - x.getValue().getTime() > mtime) {
				mp.remove();
			}
		}
	}
}
