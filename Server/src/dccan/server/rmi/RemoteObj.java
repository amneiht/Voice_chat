package dccan.server.rmi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.List;

import dccan.remote.Communication;
import dccan.server.sql.Comments;
import dccan.server.sql.Group;
import dccan.server.sql.User;
import dccan.server.sql.file.SFile;

public class RemoteObj implements Communication {
	public String user = null;

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
		String res = Group.getMember(group);
		return res;
	}

	@Override
	public String getGroup() throws RemoteException {
		if (user == null)
			return "false";
		String res = Group.GetGroup(user);
		return res;
	}

	@Override
	public boolean addFriend(String id) throws RemoteException {
		if (user == null)
			return false;
		String rs = Group.addFriend(user, id);
		return rs.equals("ok");
	}

	@Override
	public String getFriendList() throws RemoteException {
		String rs = Group.getFrendList(user);
		return rs;
	}

	@Override
	public String getCommentList(String group, String date, boolean status) throws RemoteException {
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
		try {
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
		String res = Group.deleteMember(mem, group);
		return res.equals("true");
	}

	@Override
	public boolean createGroup(String name, List<String> member) throws RemoteException {
		String rs = Group.addGroup(user, member, name);
		return rs.equals("true");
	}

	@Override
	public boolean addMember(String id, String group) throws RemoteException {
		String res = Group.addMember(id, group);
		return res.equals("true");
	}

}
