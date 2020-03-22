package dccan.connect;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import dccan.sercuty.Des;

public class Login {
	String host;
	public Login(String d) {
		host = d;
	}

	public String log(String user, String pass) {
		
			try {
				if (!Des.isInit()) Des.init(Info.key);
				Map<String, String> mp = new HashMap<String, String>();
				mp.put("task", "login");
				mp.put("user", user);
				mp.put("pass", pass);
				String gson = new Gson().toJson(mp);
				String token = Sts.getString(host, gson);
				return token;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		
	}
	public String register(String user, String pass,String email,String hoten) {
		
		try {
			if (!Des.isInit()) Des.init(Info.key);
			Map<String, String> mp = new HashMap<String, String>();
			mp.put("task", "register");
			mp.put("user", user);
			mp.put("pass", pass);
			mp.put("email", email);
			mp.put("nguoiDung", hoten);
			String gson = new Gson().toJson(mp);
			String token = Sts.getString(host, gson);
			return token;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
}
}
