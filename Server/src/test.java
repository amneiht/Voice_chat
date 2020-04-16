import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dccan.server.rmi.RemoteObj;
import dccan.suport.Friend;

public class test {
	public static void main(String[] args) {
		try {
			RemoteObj rmi = new RemoteObj();
			try {
				boolean res = rmi.login("can", "1");
				System.out.println(res);
				// rmi.addFriend("test2");
				Gson gson = new Gson();

				String ps = rmi.getFriendList();
				Type mem = new TypeToken<List<Friend>>() {
				}.getType();
				List<Friend> lm = gson.fromJson(ps, mem);
				for (Friend d : lm) {
					System.out.println(d.getNguoiDung());
					System.out.println(d.getTen() + "\n");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
