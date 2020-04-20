package dccan.server.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dccan.server.sql.Comments;
import dccan.server.sql.Groups;
import dccan.server.sql.User;
import dccan.server.sql.file.SFile;

public class Manager  {
	/*
	Socket sock;
	static Type mapType;
	static Type ListType;
	static Gson gson = new Gson();
	private static boolean run = true;

	public static void main(String[] args) {
		try {
			mapType = new TypeToken<Map<String, String>>() {
			}.getType();
			ListType = new TypeToken<List<String>>() {
			}.getType();
			Des.init("can 1234");
			ServerSocket sc = new ServerSocket(8888);
			int dem = 0;
			while (run) {
				dem++;
				Socket s = sc.accept();
				new Thread(new Manager(s)).start();
				if (dem > 10000) { // loai cac token khong dung
					dem = 0;
					ListUser.clear();
				}
			}
			sc.close();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public Manager(Socket d) {
		sock = d;
	}

	
	
	public void run() {
		DataInputStream in;
		try {
			in = new DataInputStream(sock.getInputStream());
			String input = in.readUTF();

			Map<String, String> mp = gson.fromJson(Des.decrypt(input), mapType);
			String task = mp.get("task");
			if (task.equals("login")) {
				dologin(mp);
			} else if (task.equals("register")) {
				System.out.println("client register");
				doregis(mp);
			} else if (task.equals("getComment")) {
				doGetComment(mp);
			} else if (task.equals("addFriend")) {
				doAddFriend(mp);
			} else if (task.equals("createGroup")) {
				doCreateGroup(mp);
			} else if (task.equals("sendFile")) {
				doUpfile(in, mp);
			} else if (task.equals("comment")) {
				doComment(mp);
			} else if (task.equals("downFile")) {
				doDownLoad(mp);
			} else if (task.equals("getgroup")) {
				doGetGroup(mp);
			} else if (task.equals("getMember")) {
				doGetMember(mp);
			} else if (task.equals("addMember")) {
				doAddMember(mp);
			} else if (task.equals("deleteMember")) {
				doDeleteMember(mp);
			}else if (task.equals("deleteFriend")) {
				doDeleteFriend(mp);
			} else {
				System.out.println("chuc nang" + task + " quen chua lam");
			}
			in.close();
			sock.close();
		} catch (

		Exception e) {
			e.printStackTrace();
		}

	}

	private void doDeleteFriend(Map<String, String> mp) throws Exception {
		String token = mp.get("token");
		String user = ListUser.getUserByToken(token);
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		if (user == null) {
			out.writeUTF(Des.encrypt("false"));// loi dang nhap
			out.close();
			return;
		}
		String group = mp.get("group");
		String id = mp.get("member");
		
		String res = Groups.deleteFriend(id, group);
		out.writeUTF(Des.encrypt(res));
		out.close();
		
	}

	private void doDeleteMember(Map<String, String> mp) throws IOException, Exception {
		String token = mp.get("token");
		String user = ListUser.getUserByToken(token);
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		if (user == null) {
			out.writeUTF(Des.encrypt("false"));// loi dang nhap
			out.close();
			return;
		}
		String group = mp.get("group");
		String id = mp.get("member");
		
		String res = Groups.deleteMember(id, group);
		out.writeUTF(Des.encrypt(res));
		out.close();
	}

	private void doAddMember(Map<String, String> mp) throws Exception {
		String token = mp.get("token");
		String user = ListUser.getUserByToken(token);
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		if (user == null) {
			out.writeUTF(Des.encrypt("false"));// loi dang nhap
			out.close();
			return;
		}
		String group = mp.get("group");
		String id = mp.get("id");

		String res = Groups.addMember(id, group,user);
		out.writeUTF(Des.encrypt(res));
		out.close();
	}

	private void doGetMember(Map<String, String> mp) throws IOException, Exception {
		String token = mp.get("token");
		String user = ListUser.getUserByToken(token);
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		if (user == null) {
			out.writeUTF(Des.encrypt("false"));// loi dang nhap
			out.close();
			return;
		}
		String id = mp.get("group");
		String res = Groups.getMember(id);
		out.writeUTF(Des.encrypt(res));
		out.close();
	}

	private void doGetGroup(Map<String, String> mp) throws IOException, Exception {
		String token = mp.get("token");
		String user = ListUser.getUserByToken(token);
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		if (user == null) {
			out.writeUTF(Des.encrypt("false"));// loi dang nhap
			out.close();
			return;
		}
		String res = Groups.GetGroup(user);
		out.writeUTF(Des.encrypt(res));
		out.close();
	}

	private void doDownLoad(Map<String, String> mp) throws IOException, Exception {
		String token = mp.get("token");
		String user = ListUser.getUserByToken(token);
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		if (user == null) {
			out.writeUTF(Des.encrypt("false"));// loi dang nhap
			out.close();
			return;
		}
		String id = mp.get("idfile");
		DataInputStream in = new DataInputStream(SFile.downloadFile(id));
		byte[] sock = new byte[1024 * 8];
		int read = 0;
		while (true) {
			read = in.read(sock);
			if (read < 0)
				break;
			out.write(sock, 0, read);

		}
		in.close();
		out.close();
	}

	private void doComment(Map<String, String> mp) throws IOException, Exception {
		String token = mp.get("token");
		String user = ListUser.getUserByToken(token);
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		if (user == null) {
			out.writeUTF(Des.encrypt("false"));// loi dang nhap
			out.close();
			return;
		}
		String group = mp.get("group");
		String cmt = mp.get("value");
		String res = Comments.up(group, user, cmt);
		out.writeUTF(Des.encrypt(res));
		out.close();
	}

	private void doUpfile(DataInputStream in, Map<String, String> mp) throws IOException, Exception {
		String token = mp.get("token");
		String user = ListUser.getUserByToken(token);
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		if (user == null) {
			out.writeUTF(Des.encrypt("false"));// loi dang nhap
			out.close();
			return;
		}
		String filename = mp.get("file");
		String id = SFile.createFileId(filename);
		String group = mp.get("group");
		String res = Comments.upFcoment(group, user, filename, id);
		if (res.equals("false")) {
			out.writeUTF(Des.encrypt("false"));// loi dang nhap
			out.close();
			return;
		}
		res = SFile.insert(id, in, filename);
		out.writeUTF(Des.encrypt(res));
		out.close();

	}

	private void doCreateGroup(Map<String, String> mp) throws IOException, Exception {

		String token = mp.get("token");
		String user = ListUser.getUserByToken(token);
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		if (user == null) {
			out.writeUTF(Des.encrypt("false"));// loi dang nhap
			out.close();
			return;
		}
		String lt = mp.get("list");
		List<String> lp = gson.fromJson(lt, ListType);
		String ten = mp.get("name");
		String rs = Groups.addGroup(user, lp, ten);
		out.writeUTF(Des.encrypt(rs));
		out.close();
	}

	private void doAddFriend(Map<String, String> mp) throws Exception {

		String token = mp.get("token");
		String user = ListUser.getUserByToken(token);
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		if (user == null) {
			out.writeUTF(Des.encrypt("false"));// loi dang nhap
			out.close();
			return;
		}
		String id2 = mp.get("friend");
		String rs = Groups.addFriend(user, id2);
		out.writeUTF(Des.encrypt(rs));
		out.close();
	}

	private void doGetComment(Map<String, String> mp) throws Exception {
		String token = mp.get("token");
		String user = ListUser.getUserByToken(token);
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		if (user == null) {
			out.writeUTF(Des.encrypt("false"));// loi dang nhap
			out.close();
			return;
		}
		String id = mp.get("group");
		String time = mp.get("time");
		if (time.equals("false"))
			time = new Timestamp(System.currentTimeMillis()).toString();
		String kq = Comments.getOldChat(id, time);
		out.writeUTF(Des.encrypt(kq));
		out.close();
	}

	
	private void doregis(Map<String, String> mp) throws Exception {
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		String user = mp.get("user");
		String pass = mp.get("pass");
		String email = mp.get("email");
		String hoten = mp.get("nguoiDung");
		boolean td = User.register(user, pass, email, hoten);
		if (td) {
			String token = ListUser.addNew(user, sock.getInetAddress().toString());
			out.writeUTF(Des.encrypt(token));
		} else
			out.writeUTF(Des.encrypt("false"));
		out.close();

	}

	
	private void dologin(Map<String, String> mp) throws Exception {
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		String user = mp.get("user");
		String pass = mp.get("pass");
		String id = User.login(user, pass);// lay ten nguoi dung
		if (id == null) {
			out.writeUTF(Des.encrypt("false"));// loi dang nhap
		} else {
			String token = ListUser.addNew(id, sock.getInetAddress().toString());
			out.writeUTF(Des.encrypt(token));
		}
		out.close();
	}
*/
}
