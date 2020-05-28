import com.google.gson.Gson;

import dccan.suport.Friend;
import net.help.Convert;

public class Test_Gson {
	public static void main(String[] args) {
		Friend lp = new Friend();
		byte [] ls = Convert.objectToByte(lp);
		System.out.println("Độ dài chuỗi byte object:"+ls.length);
		String gd = new Gson().toJson(lp);
		System.out.println("Chuỗi  json:"+gd);
		System.out.println("Độ dài json:"+gd.length());
	}
}
