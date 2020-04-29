package dccan.server.rmi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import dccan.remote.Communication;
import dccan.server.control.chat.Client;
import dccan.server.control.chat.Room;
import dccan.server.control.chat.RoomMap;
import dccan.server.control.chat.StaticMap;
import dccan.server.sql.Comments;
import dccan.server.sql.Groups;
import dccan.server.sql.Requests;
import dccan.server.sql.User;
import dccan.server.sql.file.SFile;

public class RemoteObj implements Communication {

	public String user = null;

	// static Roommap
	@Override
	public boolean login(String user, String pass) throws RemoteException {
		String id = User.login(user, pass);
		if (id != null) {
			this.user = user;
			return true;
		}
		return false;
	}

	@Override
	public boolean register(String user, String pass, String hoten, String email) throws RemoteException {
		boolean td = User.register(user, pass, email, hoten);
		if (td) {
			this.user = user;
		}
		return td;
	}

	@Override
	public String getMember(String group) throws RemoteException {
		if (user == null)
			return "false";
		if (!Groups.checkMember(group, user))
			return null;
		String res = Groups.getMember(group);
		return res;
	}

	@Override
	public String getGroup() throws RemoteException {
		if (user == null)
			return "false";
		String res = Groups.GetGroup(user);
		return res;
	}

	@Override
	public boolean addFriend(String id) throws RemoteException {
		if (user == null)
			return false;
		String rs = Groups.addFriend(user, id);
		return rs.equals("ok");
	}

	@Override
	public String getFriendList() throws RemoteException {
		String rs = Groups.getFrendList(user);
		return rs;
	}

	@Override
	public String getCommentList(String group, String date, boolean status) throws RemoteException {
		if (!Groups.checkMember(group, user))
			return "false";
		String res = null;
		if (status) {
			res = Comments.getNewChat(group, date);
		} else {
			res = Comments.getOldChat(group, date);
		}
		return res;
	}

	@Override
	public byte[] dowload(String idFile) throws RemoteException {
		if (user == null)
			return null;
		try {
			DataInputStream is = new DataInputStream(SFile.downloadFile(idFile));
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int nRead;
			byte[] data = new byte[1024];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();
			byte[] byteArray = buffer.toByteArray();
			is.close();
			buffer.close();
			return byteArray;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean upload(String name, byte[] data, String group) throws RemoteException {
		if (user == null)
			return false;
		try {
			System.out.println(data.length);
			String id = SFile.createFileId(name);
			String res = Comments.upFcoment(group, user, name, id);
			if (res.equals("false")) {

				return false;
			}
			InputStream in = new ByteArrayInputStream(data);
			res = SFile.insert(id, in, name);
			if (res.equals("false")) {

				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteMember(String group, String mem) throws RemoteException {
		if (user == null)
			return false;
		String res = Groups.deleteMember(group, mem, user);
		return res.equals("true");
	}

	@Override
	public boolean createGroup(String name, List<String> member) throws RemoteException {
		if (user == null)
			return false;
		String rs = Groups.addGroup(user, member, name);
		return rs.equals("true");
	}

	@Override
	public boolean addMember(String group, String id) throws RemoteException {
		if (user == null)
			return false;
		String res = Groups.addMember(id, group, user);
		return res.equals("true");
	}

	@Override
	public boolean upComment(String group, String nDung) throws RemoteException {
		if (user == null)
			return false;
		String res = Comments.up(group, user, nDung);
		return res.equals("true");
	}

	@Override
	public void outGroup(String group) throws RemoteException {
		if (user == null)
			return;
		Groups.outGroup(group, user);

	}

	@Override
	public void deleteGroup(String group) throws RemoteException {
		if (user == null)
			return;
		Groups.deleteGroup(group, user);
	}

	@Override
	public String getUserkey(String group) throws RemoteException {
		if (user == null)
			return null;
		if (!Groups.checkMember(group, user))
			return null;
		List<String> lp = new LinkedList<String>();
		RoomMap rm = StaticMap.getRm();
		lp.add(rm.getGroupKey(group));
		lp.add(rm.createUserKey(group, user));
		String up = new Gson().toJson(lp);
		return up;
	}

	@Override
	public String chatMember(String group) throws RemoteException {
		if (user == null)
			return null;
		if (!Groups.checkMember(group, user))
			return null;
		List<String> lp = new LinkedList<String>();
		RoomMap rm = StaticMap.getRm();
		long id = rm.getRoomId(group);
		if (id == -1)
			return null;
		Room room = rm.lp.get(id);
		List<Client> ls = room.getMem();
		for (Client cp : ls) {
			lp.add(cp.getUser());
		}
		String up = new Gson().toJson(lp);
		return up;
	}

	@Override
	public boolean requestGroup(String group) throws RemoteException {
		return Requests.addRequest(group, user);
	}

	@Override
	public String showRequest(String group) throws RemoteException {
		return Requests.showRq(group, user);
		
	}

	@Override
	public boolean acceptRequest(String group, List<String> member) throws RemoteException {
		return Requests.acceptRq(group, user, member);
	}

	@Override
	public boolean deleteRequest(String group, List<String> member) throws RemoteException {
		return Requests.deleteRq(group, user, member);
	}
}
