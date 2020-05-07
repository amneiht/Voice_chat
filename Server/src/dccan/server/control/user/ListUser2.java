package dccan.server.control.user;

import java.util.HashMap;
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

}
