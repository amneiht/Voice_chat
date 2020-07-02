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
import dccan.server.control.user.ListUser2;
import dccan.server.control.user.UserToken;
import dccan.server.pass.ChangeMail;
import dccan.server.pass.ChangePass;
import dccan.server.sql.Comments;
import dccan.server.sql.Groups;
import dccan.server.sql.Requests;
import dccan.server.sql.User;
import dccan.server.sql.file.SFile;

public class RemoteObj implements Communication {

	// static Roommap
	@Override
	public String login(String user, String pass) throws RemoteException {
		String id = User.login(user, pass);
		// System.out.println(id);
		if (id != null) {
			return ListUser2.addNew(user);
		}
		return null;
	}

	@Override
	public boolean register(String user, String pass, String hoten, String email) throws RemoteException {
		return UserToken.addNew(user, pass, hoten, email);
	}

	@Override
	public boolean confirmRegister(String au) throws RemoteException {
		String s = UserToken.confirm(au);
		return s != null;
	}

	@Override
	public String getMember(String token, String group) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return "false";
		if (!Groups.checkMember(group, user))
			return null;
		String res = Groups.getMember(group);
		return res;
	}

	@Override
	public String getGroup(String token) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return null;
		String res = Groups.GetGroup(user);
		return res;
	}

	@Override
	public boolean addFriend(String token, String id) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;
		return Requests.addFriendRequest(id, user);

	}

	@Override
	public String getFriendList(String token) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return null;
		String rs = Groups.getFrendList(user);
		return rs;
	}

	@Override
	public String getCommentList(String token, String group, long date, boolean status) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return null;
		if (date == -1)
			date = System.currentTimeMillis();
		if (!Groups.checkMember(group, user)) {

			return null;
		}
		String res = null;
		if (status) {
			res = Comments.getNewChat(group, date);
		} else {
			res = Comments.getOldChat(group, date);
		}
		return res;
	}

	@Override
	public byte[] dowload(String token, String idFile) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
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
	public String getFrendListNotOnGroup(String token, String group) throws RemoteException
	{
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return null ;
		return Groups.getFrendListNotOnGroup(token, group);
	}
	@Override
	public boolean upload(String token, String name, byte[] data, String group) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;
		try {
			// System.out.println(data.length);
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
		return true;
	}

	@Override
	public boolean deleteMember(String token, String group, List<String> mem) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;
		return Groups.deleteMember(group, mem, user);

	}

	@Override
	public boolean createGroup(String token, String name, List<String> member) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;
		return Groups.addGroup(user, member, name);
		// return rs.equals("true");
	}

	@Override
	public boolean addMember(String token, String group, String id) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;
		String res = Groups.addMember(id, group, user);
		return res.equals("true");
	}

	@Override
	public boolean addMember(String token, String group, List<String> id) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;

		return Groups.addMember(group, user, id);
	}

	@Override
	public boolean upComment(String token, String group, String nDung) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;
		String res = Comments.up(group, user, nDung);
		return res.equals("true");
	}

	@Override
	public void outGroup(String token, String group) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return;
		Groups.outGroup(group, user);

	}

	@Override
	public void deleteGroup(String token, String group) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return;
		Groups.deleteGroup(group, user);
	}

	@Override
	public String getUserkey(String token, String group) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
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
	public String chatMember(String token, String group) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return null;
		if (!Groups.checkMember(group, user))
			return null;
		RoomMap rm = StaticMap.getRm();
		long id = rm.getRoomId(group);
		if (id == -1)
			return null;
		Room room = rm.lp.get(id);
		List<Client> ls = room.getMem();
		String up = new Gson().toJson(ls);
		return up;
	}

	@Override
	public boolean requestGroup(String token, String group) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		return Requests.addRequest(group, user);
	}

	@Override
	public String showRequest(String token, String group) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		return Requests.showRq(group, user);

	}

	@Override
	public boolean acceptRequest(String token, String group, List<String> member) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		return Requests.acceptRq(group, user, member);
	}

	@Override
	public boolean deleteRequest(String token, String group, List<String> member) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;
		return Requests.deleteRq(group, user, member);
	}

	@Override
	public boolean changeName(String token, String NewName) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;

		return User.changName(user, NewName);
	}

	@Override
	public boolean setImage(String token, String img, byte[] data) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;
		try {
			// System.out.println(data.length);
			String id = SFile.createFileId(img);
			InputStream in = new ByteArrayInputStream(data);
			String res = SFile.insert(id, in, img);
			if (res.equals("false")) {

				return false;
			}
			return User.setImg(user, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkLive(String token) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		return user != null;
	}

	@Override
	public String getInfo(String token) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return null;
		return User.getInfo(user);
	}

	@Override
	public boolean changeMail(String token, String mail) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;

		return ChangeMail.addMailToken(user, mail);
	}

	public boolean confirmChangeMail(String id) throws RemoteException {
		return ChangeMail.confrim(id);
	}

	@Override
	public String getUserOnLimit(String token, String name) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return null;
		return User.getLimitName(name,user);

	}

	@Override
	public boolean deleteFriend(String token, List<String> mem) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;
		return Groups.deleteFriend(user, mem);
	}

	@Override
	public boolean isAdmin(String token, String group) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;
		return Groups.isAdmin(user, group);
	}

	@Override
	public void setAdmin(String token, String group, List<String> mem) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return;
		Groups.setPri(user, group, mem, 1);

	}

	@Override
	public void delAdmin(String token, String group, List<String> mem) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return;
		Groups.setPri(user, group, mem, 0);
	}

	@Override
	public String getAdminOnGroup(String token, String group) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return null;
		return Groups.getPriMember(group, 1);
	}

	@Override
	public String getNonAdminOnGroup(String token, String group) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return null;
		return Groups.getPriMember(group, 0);
	}

	@Override
	public String showFriendRq(String token) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return null;
		return Requests.showFriendRq(user);
	}

	@Override
	public boolean acceptFriendRequest(String token, List<String> member) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;
		return Requests.acceptFriend(user, member);
	}

	@Override
	public boolean deleteFriendRequest(String token, List<String> member) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;
		return Requests.deleteFriendRq(user, member);
	}

	@Override
	public boolean deleteComment(String token, String group, long date) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;
		return Comments.delCommand(user, group, date);
	}

	@Override
	public boolean logout(String token) throws RemoteException {

		return ListUser2.removeToken(token);
	}

	@Override
	public boolean changeGroupName(String token, String group, String NewName) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return false;
		return Groups.changeGroupName(user, group, NewName);
	}

	@Override
	public boolean newPassword(String au, String pass) throws RemoteException {
		return ChangePass.changePass(au, pass);

	}

	@Override
	public void resetPass(String user) throws RemoteException {
		ChangePass.sendToken(user);
	}

	@Override
	public String showFriendInfo(String token, String s) throws RemoteException {
		String user = ListUser2.getUserByToken(token);
		if (user == null)
			return null;
		return User.getInfo(s);
	}

}
