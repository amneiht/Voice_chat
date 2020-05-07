package test.token;

import java.util.ArrayList;
import java.util.List;

public class ListUser {
	public static List<Member> map = new ArrayList<Member>();
	static {
		System.out.println("tao linh canh");
		Member last = new Member(null);
		last.time = Long.MAX_VALUE;
		last.token = "~~~~~~~";// ki tu cuoi cung

		Member frist = new Member(null);
		frist.time = Long.MAX_VALUE;
		frist.token = "      ";// ki tu cuoi cung

		map.add(frist);
		map.add(last);

	}

	/**
	 * lay ra user tu danh sach bang token
	 * 
	 * @param token
	 * @return ten user neu co
	 */
	public static String getUserByToken(String token) {
		{
			int d = 0, c = map.size() - 1;
			int g;
			while (c > d) {
				g = (d + c) / 2;
				if(d==g ) break ;
				int dem = stringCompare(token, map.get(g).token);
				if (dem == 0)
					return map.get(g).getId();
				if (dem > 0)
					d = g;
				else
					c = g;
			}
			return null;
		}

	}

	public static String addNew(String id) {
		Member np = new Member(id);
		String token = np.token;
		{
			int d = 0, c = map.size() - 1;
			int g = 0;
			while (c > d) {
				g = (d + c) / 2;
				if(g==d) break ;
				int dem = stringCompare(np.token, map.get(g).token);
				if (dem > 0) {
					d = g;
				} else {
					c = g;
				}
			}
			
			map.add(g + 1, np);
			return token;
		}
	}

	public static void clear() {
		{
			int s = map.size() - 1;
			while (s > -1) {
				if (map.get(s).outDate())
					map.remove(s);
				s = s - 1;
			}
		}
	}

	public static int stringCompare(String str1, String str2) {

		int l1 = str1.length();
		int l2 = str2.length();
		int lmin = Math.min(l1, l2);

		for (int i = 0; i < lmin; i++) {
			int str1_ch = (int) str1.charAt(i);
			int str2_ch = (int) str2.charAt(i);

			if (str1_ch != str2_ch) {
				return str1_ch - str2_ch;
			}
		}
		if (l1 != l2) {
			return l1 - l2;
		} else {
			return 0;
		}
	}
}