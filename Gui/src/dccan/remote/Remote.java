package dccan.remote;

import java.rmi.RemoteException;
import java.util.List;

public class Remote implements Rmt {
	private String token = null;
	public String id = null;
	Communication rmi;

	public Remote(Communication rmi) {
		this.rmi = rmi;
	}

	public String login(String user, String pass) throws RemoteException {
		token = rmi.login(user, pass);
		id = user;
		return token;

	}

	public String register(String user, String pass, String hoten, String email) throws RemoteException {
		token = rmi.register(user, pass, hoten, email);
		id = user;
		return token;
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

	@Override
	public boolean changeName(String NewName) throws RemoteException {

		return rmi.changeName(token, NewName);
	}

	@Override
	public boolean setImage(String img, byte[] data) throws RemoteException {
		return rmi.setImage(token, img, data);
	}

	@Override
	public boolean checkLive() throws RemoteException {
		return rmi.checkLive(token);
	}

	@Override
	public String getInfo() throws RemoteException {
		return rmi.getInfo(token);
	}

	@Override
	public boolean changeMail(String mail) throws RemoteException {
		return rmi.changeMail(token, mail);
	}

	@Override
	public String getUserOnLimit(String name) throws RemoteException {
		return rmi.getUserOnLimit(token, name);
	}

	@Override
	public boolean deleteMember(String group, List<String> mem) throws RemoteException {
		return rmi.deleteMember(token, group, mem);
	}

	@Override
	public boolean deleteFriend(List<String> mem) throws RemoteException {
		return rmi.deleteFriend(token, mem);
	}

	@Override
	public boolean isAdmin(String group) throws RemoteException {
		return rmi.isAdmin(token, group);
	}

	@Override
	public void setAdmin(String group, List<String> mem) throws RemoteException {
		rmi.setAdmin(token, group, mem);

	}

	@Override
	public void delAdmin(String group, List<String> mem) throws RemoteException {
		rmi.delAdmin(token, group, mem);

	}

	@Override
	public String getAdminOnGroup(String group) throws RemoteException {
		return rmi.getAdminOnGroup(token, group);
	}

	@Override
	public String getNonAdminOnGroup(String group) throws RemoteException {
		return rmi.getNonAdminOnGroup(token, group);
	}

	@Override
	public String showFriendRq() throws RemoteException {
		return rmi.showFriendRq(token);
	}

	@Override
	public boolean acceptFriendRequest(List<String> member) throws RemoteException {
		return rmi.acceptFriendRequest(token, member);
	}

	@Override
	public boolean deleteFriendRequest(List<String> member) throws RemoteException {
		return rmi.deleteFriendRequest(token, member);
	}

}
