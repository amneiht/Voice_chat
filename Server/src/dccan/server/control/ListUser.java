//package dccan.server.control;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class ListUser {
//	private static List<Member> map = Collections.synchronizedList(new ArrayList<Member>());
///**
// * lay ra user tu danh sach bang token
// * @param token
// * @return ten user neu co
// */
//	public static String getUserByToken1(String token) {
//		// thuat toan tim kiem nhi phan
//		// synchronized (map)
//		{
//			int d = 0, c = map.size() - 1;
//			int g;
//			while (c > d) {
//				g = (d + c) / 2;
//				int dem = stringCompare(token, map.get(g).token);
//				if (dem == 0)
//					return map.get(g).getId();
//				if (dem > 0)
//					d = g;
//				else
//					c = g;
//			}
//			return null;
//		}
//
//	}
//
//	public static String addNew(String id, String ip) {
//		Member np = new Member(ip, id);
//		String token = np.token;
//		// synchronized (map)
//		{
//			int d = 0, c = map.size() - 1;
//			if (c == -1) {
//				map.add(np);
//				return token;
//			} else if (c == 0) {
//				int dem = stringCompare(np.token, map.get(0).token);
//				if (dem > 0)
//					map.add(np);
//				else
//					map.add(0, np);
//				return token;
//			}
//
//			int g = 0;
//			while (c > d) {
//				g = (d + c) / 2;
//				int dem = stringCompare(np.token, map.get(g).token);
//				if (dem > 0) {
//					d = g;
//				} else {
//					c = g;
//				}
//			}
//			map.add(g + 1, np);
//			return token;
//		}
//	}
//
//	public static void clear() {
//		// xoa cac token loi thoi
//		// synchronized (map) // chua can dung
//		{
//			int s = map.size() - 1;
//			while (s > -1) {
//				if (map.get(s).outDate())
//					map.remove(s);
//				s = s - 1;
//			}
//		}
//	}
//
//	public static int stringCompare(String str1, String str2) {
//
//		int l1 = str1.length();
//		int l2 = str2.length();
//		int lmin = Math.min(l1, l2);
//
//		for (int i = 0; i < lmin; i++) {
//			int str1_ch = (int) str1.charAt(i);
//			int str2_ch = (int) str2.charAt(i);
//
//			if (str1_ch != str2_ch) {
//				return str1_ch - str2_ch;
//			}
//		}
//
//		// Edge case for strings like
//		// String 1="Geeks" and String 2="Geeksforgeeks"
//		if (l1 != l2) {
//			return l1 - l2;
//		}
//
//		// If none of the above conditions is true,
//		// it implies both the strings are equal
//		else {
//			return 0;
//		}
//	}
//}
