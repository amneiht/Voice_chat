package dccan.connect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class Task {
	private String token, host;
	Gson gson;

	public Task(String dhost, String dtoken) {
		host = dhost;
		token = dtoken;
		gson = new Gson();
	}

	/**
	 * tao moi 1 nhom
	 * 
	 * @param name
	 *            ten nhom
	 * @param ap
	 *            danh sach thanh vien
	 * @return
	 */
	public String makegroup(String name, ArrayList<String> ap) {

		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", "createGroup");
		mp.put("token", token);
		mp.put("name", name);
		mp.put("list", gson.toJson(ap));
		String gs = gson.toJson(mp);
		String token = null;
		try {
			token = Sts.getString(host, gs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (token == null)
			return "false";
		else
			return token;
	}

	public String getMember(String group) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", "getMember");
		mp.put("token", token);
		mp.put("group", group);
		String gs = this.gson.toJson(mp);
		String token = null;
		try {
			token = Sts.getString(host, gs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (token == null)
			return "false";
		else
			return token;
	}
	/**
	 * xoa 1 nguoi ban
	 * @param id
	 * @return
	 */
	public String deleteFiend(String id) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", "deleteFriend");
		mp.put("token", token);
		mp.put("id", id);
		String gs = this.gson.toJson(mp);
		String token = null;
		try {
			token = Sts.getString(host, gs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (token == null)
			return "false";
		else
			return token;
	}
	public String deleteMember(String id,String mb) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", "deleteMenber");
		mp.put("token", token);
		mp.put("group", id);
		mp.put("member", mb);
		String gs = this.gson.toJson(mp);
		String token = null;
		try {
			token = Sts.getString(host, gs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (token == null)
			return "false";
		else
			return token;
	}
	/**
	 * tai file va luu vao url
	 * 
	 * @param id
	 * @param filename
	 * @param url
	 * @return
	 */
	public String downFile(String id, String filename, String url) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", "downFile");
		mp.put("token", token);
		mp.put("idfile", id);
		String gson = this.gson.toJson(mp);
		String token = null;
		try {
			token = Sts.downFile(host, gson, filename, url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (token == null)
			return "false";
		else
			return token;
	}

	/**
	 * gui file len server
	 * 
	 * @param group
	 * @param file
	 * @return
	 */
	public String sendFile(String group, String filename) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", "sendFile");
		mp.put("token", token);
		mp.put("group", group);
		mp.put("file", filename);
		String gs = gson.toJson(mp);
		String token = null;
		try {
			token = Sts.sendFile(host, gs, filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (token == null)
			return "false";
		else
			return token;
	}

	/**
	 * them 1 nguoi ban
	 * 
	 * @param id
	 * @return
	 */
	public String addFriend(String id) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", "addFriend");
		mp.put("token", token);
		mp.put("friend", id);
		String gs = gson.toJson(mp);
		String token = null;
		try {
			token = Sts.getString(host, gs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (token == null)
			return "false";
		else
			return token;
	}

	/**
	 * lay cac comment
	 * 
	 * @param id
	 * @param date
	 * @return
	 */
	public String getComment(String id, String date) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", "getComment");
		mp.put("token", token);
		mp.put("time", date);
		mp.put("group", id);
		String gson = this.gson.toJson(mp);
		String token = null;
		try {
			token = Sts.getString(host, gson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (token == null)
			return "false";
		else
			return token;
	}

	public String getComment(String id) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", "getComment");
		mp.put("token", token);
		mp.put("time", "false");
		mp.put("group", id);
		String gson = this.gson.toJson(mp);
		String token = null;
		try {
			token = Sts.getString(host, gson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (token == null)
			return "false";
		else
			return token;
	}

	/**
	 * gui commet len server
	 * 
	 * @param cmt
	 * @return
	 */
	public String comment(String cmt, String id) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", "comment");
		mp.put("token", token);
		mp.put("value", cmt);
		mp.put("group", id);
		String gson = this.gson.toJson(mp);
		String token = null;
		try {
			token = Sts.getString(host, gson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (token == null)
			return "false";
		else
			return token;
	}

	public String addMember(String group, String id) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", "addMember");
		mp.put("token", token);
		mp.put("group", group);
		mp.put("id", id);
		String gson = this.gson.toJson(mp);
		String token = null;
		try {
			token = Sts.getString(host, gson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (token == null)
			return "false";
		else
			return token;

	}

	/**
	 * lay danh sach nhom chat
	 * 
	 * @return
	 */
	public String getGroup() {
		return doTask("getgroup");
	}

	/**
	 * lay danh ban be
	 * 
	 * @return
	 */
	public String getFriendList() {
		return doTask("getfriendlist");
	}

	private String doTask(String task) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", task);
		mp.put("token", token);
		String gson = this.gson.toJson(mp);
		String token = null;
		try {
			token = Sts.getString(host, gson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (token == null)
			return "false";
		else
			return token;
	}
}
