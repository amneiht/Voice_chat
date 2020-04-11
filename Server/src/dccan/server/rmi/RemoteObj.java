package dccan.server.rmi;

import java.rmi.RemoteException;
import java.util.List;

import dccan.remote.Communication;
import dccan.server.sql.Group;
import dccan.server.sql.User;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGroup() throws RemoteException {
		String res = Group.GetGroup(user);
		return res ;
	}

	@Override
	public boolean addFriend(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getFriendList() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommentList(String group, String date, boolean status) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] dowload(String idFile) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean upload(String name, byte[] data, int length) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteMember(String group, String mem) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createGroup(String name, List<String> member) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
