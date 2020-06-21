package dccan.remote;

import java.rmi.RemoteException;
import java.util.List;

public class NoToken implements Rmt {
	private String token = null;
	public String id = null;
	Communication rmi;

	public NoToken(Communication rmi) {
		this.rmi = rmi;
	}

	@Override
	public String login(String user, String pass) throws RemoteException {
		token = rmi.login(user, pass);
		id = user;
		return token;

	}

	@Override
	public void register(String user, String pass, String hoten, String email) throws RemoteException {
		rmi.register(user, pass, hoten, email);

	}

	@Override
	public String getMember(String group) throws RemoteException {
		return rmi.getMember(token, group);
	}

	@Override
	public String getGroup() throws RemoteException {
		return rmi.getGroup(token);
	}

	@Override
	public boolean addFriend(String id) throws RemoteException {
		return rmi.addFriend(token, id);
	}

	@Override
	public String getFriendList() throws RemoteException {
		return rmi.getFriendList(token);
	}

	@Override
	public String getCommentList(String group, long date, boolean status) throws RemoteException {
		return rmi.getCommentList(token, group, date, status);
	}

	@Override
	public byte[] dowload(String idFile) throws RemoteException {
		return rmi.dowload(token, idFile);
	}

	@Override
	public boolean upload(String name, byte[] data, String group) throws RemoteException {
		return rmi.upload(token, name, data, group);
	}

	@Override
	public boolean createGroup(String name, List<String> member) throws RemoteException {
		return rmi.createGroup(token, name, member);
	}

	@Override
	public boolean addMember(String group, String id) throws RemoteException {
		return rmi.addMember(token, group, id);
	}

	@Override
	public boolean upComment(String group, String nDung) throws RemoteException {
		return rmi.upComment(token, group, nDung);
	}

	@Override
	public void outGroup(String group) throws RemoteException {
		rmi.outGroup(token, group);

	}

	@Override
	public void deleteGroup(String group) throws RemoteException {
		rmi.deleteGroup(token, group);

	}

	@Override
	public String getUserkey(String group) throws RemoteException {
		return rmi.getUserkey(token, group);
	}

	public String chatMember(String group) throws RemoteException {
		return rmi.chatMember(token, group);
	}

	@Override
	public boolean requestGroup(String group) throws RemoteException {
		return rmi.requestGroup(token, group);
	}

	@Override
	public String showRequest(String group) throws RemoteException {
		return rmi.showRequest(token, group);
	}

	public boolean acceptRequest(String group, List<String> member) throws RemoteException {
		return rmi.acceptRequest(token, group, member);
	}

	@Override
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

	@Override
	public boolean deleteComment(String group, long date) throws RemoteException {
		return rmi.deleteComment(token, group, date);
	}

	@Override
	public boolean addMember(String group, List<String> id) throws RemoteException {
		return rmi.addMember(token, group, id);
	}

	@Override
	public boolean logout() throws RemoteException {
		boolean d = rmi.logout(token);
		token = null;
		id = null;
		return d;
	}

	@Override
	public String showFriendInfo(String s) throws RemoteException {
		return rmi.showFriendInfo(token, s);
	}

	@Override
	public boolean changeGroupName(String group, String NewName) throws RemoteException {
		return rmi.changeGroupName(token, group, NewName);
	}

	@Override
	public boolean newPassword(String au, String pass) throws RemoteException {
		return rmi.newPassword(au, pass);
	}

	@Override
	public void resetPass(String user) throws RemoteException {
		rmi.resetPass(user);
	}

	@Override
	public boolean confirmRegister(String au) throws RemoteException {
		return rmi.confirmRegister(au);
	}

	@Override
	public boolean confirmChangeMail(String id) throws RemoteException {
		return rmi.confirmChangeMail(id);
	}

}
