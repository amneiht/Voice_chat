package dccan.remote;

import java.rmi.RemoteException;
import java.util.List;

public class Remote {
	private String token = null;
	Communication rmi;

	public Remote(Communication rmi) {
		this.rmi = rmi;
	}

	public boolean login(String user, String pass) throws RemoteException {
		token = rmi.login(user, pass);
		
		return token != null;
	}

	public boolean register(String user, String pass, String hoten, String email) throws RemoteException {
		token = rmi.register(user, pass, hoten, email);
		return token != null;
	}

	public String getMember(String group) throws RemoteException {
		return rmi.getMember(token, group);
	}

	public String getGroup() throws RemoteException {
		return rmi.getGroup(token);
	}

	public boolean addFriend(String id) throws RemoteException {
		return rmi.addFriend(token, id);
	}

	public String getFriendList() throws RemoteException {
		return rmi.getFriendList(token);
	}

	public String getCommentList(String group, long date, boolean status) throws RemoteException {
		return rmi.getCommentList(token, group, date, status);
	}

	public byte[] dowload(String idFile) throws RemoteException {
		return rmi.dowload(token, idFile);
	}

	public boolean upload(String name, byte[] data, String group) throws RemoteException {
		return rmi.upload(token, name, data, group);
	}

	public boolean deleteMember(String group, String mem) throws RemoteException {
		return rmi.deleteMember(token, group, mem);
	}

	public boolean createGroup(String name, List<String> member) throws RemoteException {
		return rmi.createGroup(token, name, member);
	}

	public boolean addMember(String group, String id) throws RemoteException {
		return rmi.addMember(token, group, id);
	}

	public boolean upComment(String group, String nDung) throws RemoteException {
		return rmi.upComment(token, group, nDung);
	}

	public void outGroup(String group) throws RemoteException {
		rmi.outGroup(token, group);

	}

	public void deleteGroup(String group) throws RemoteException {
		rmi.deleteGroup(token, group);

	}

	public String getUserkey(String group) throws RemoteException {
		return rmi.getUserkey(token, group);
	}

	/**
	 * get mem ber on chat group
	 * 
	 * @param group
	 * @return
	 * @throws RemoteException
	 */
	public String chatMember(String group) throws RemoteException {
		return rmi.chatMember(token, group);
	}

	public boolean requestGroup(String group) throws RemoteException {
		return rmi.requestGroup(token, group);
	}

	public String showRequest(String group) throws RemoteException {
		return rmi.showRequest(token, group);
	}

	public boolean acceptRequest(String group, List<String> member) throws RemoteException {
		return rmi.acceptRequest(token, group, member);
	}

	public boolean deleteRequest(String group, List<String> member) throws RemoteException {
		return rmi.deleteRequest(token, group, member);
	}

}
