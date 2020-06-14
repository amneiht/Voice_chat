package dccan.server.control.user;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

public class ListUser2 {
	public static Map<String, Member> op = new HashMap<String, Member>();

	public static String addNew(String string) {
		Member mp = new Member(string);
		String token = RandomStringUtils.randomAlphanumeric(30);
		op.put(token, mp);
		return token;
	}

	public static String getUserByToken(String lg) {
		Member lp = op.get(lg);
		if (lp == null)
			return null;
		else
			return lp.getId();
	}

	public static boolean removeToken(String key) {
		Member lp = op.remove(key);
		if (lp == null)
			return false;
		return true;
	}

	public static void clear() {
		Iterator<Map.Entry<String, Member>> mp = ListUser2.op.entrySet().iterator();
		for (; mp.hasNext();) {
			Map.Entry<String, Member> x = mp.next();
			if (x.getValue().outDate()) {
				mp.remove();
			}
		}
	}
}
