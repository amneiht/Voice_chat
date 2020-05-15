package dccan.suport;

public class CheckImage {
	public static boolean cmp(String a) {
		if (a.endsWith(".png") || a.endsWith(".jpg"))
			return true;
		return false;
	}
}
