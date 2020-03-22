package dccan.connect;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class Task {
	private String token, host;

	public Task(String dhost, String dtoken) {
		host = dhost;
		token = dtoken;
	}
	public String downFile(String id,String filename,String url)
	{
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", "downFile");
		mp.put("token", token);
		mp.put("idfile", id);
		String gson = new Gson().toJson(mp);
		String token = null;
		try {
			token = Sts.downFile(host, gson,filename,url);
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
	 * @param group
	 * @param file
	 * @return
	 */
	public String sendFile(String group,String file)
	{
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", "sendFile");
		mp.put("token", token);
		mp.put("group", group);
		mp.put("file", file);
		String gson = new Gson().toJson(mp);
		String token = null;
		try {
			token = Sts.sendFile(host, gson,file);
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
	 * @param id
	 * @return
	 */
	public String addFriend(String id)
	{
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", "addFriend");
		mp.put("token", token);
		mp.put("friend", id);
		String gson = new Gson().toJson(mp);
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
		String gson = new Gson().toJson(mp);
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
		String gson = new Gson().toJson(mp);
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
	 * @return
	 */
	public String getGroup() {
		return doTask("getgroup");
	}
	/**
	 * lay danh ban be
	 * @return
	 */
	public String getFriendList() {
		return doTask("getfriendlist");
	}

	private String doTask(String task) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("task", task);
		mp.put("token", token);
		String gson = new Gson().toJson(mp);
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
