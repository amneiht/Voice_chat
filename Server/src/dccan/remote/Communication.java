package dccan.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Communication extends Remote {
	public String login(String user, String pass) throws RemoteException;

	public String register(String user, String pass, String hoten, String email) throws RemoteException;

	public String getMember(String token, String group) throws RemoteException;

	public String getGroup(String token) throws RemoteException;

	public boolean addFriend(String token, String id) throws RemoteException;

	public String getFriendList(String token) throws RemoteException;

	public String getCommentList(String token, String group, long date, boolean status) throws RemoteException;

	public byte[] dowload(String token, String idFile) throws RemoteException;

	public boolean upload(String token, String name, byte[] data, String group) throws RemoteException;

	public boolean deleteMember(String token, String group, List<String> mem) throws RemoteException;

	public boolean deleteFriend(String token, List<String> mem) throws RemoteException;

	public boolean createGroup(String token, String name, List<String> member) throws RemoteException;

	public boolean addMember(String token, String group, String id) throws RemoteException;

	public boolean upComment(String token, String group, String nDung) throws RemoteException;

	public void outGroup(String token, String group) throws RemoteException;

	public void deleteGroup(String token, String group) throws RemoteException;

	public String getUserkey(String token, String group) throws RemoteException;

	public String chatMember(String token, String group) throws RemoteException;

	public boolean requestGroup(String token, String group) throws RemoteException;

	public String showRequest(String token, String group) throws RemoteException;

	public boolean acceptRequest(String token, String group, List<String> member) throws RemoteException;

	public boolean deleteRequest(String token, String group, List<String> member) throws RemoteException;

	public boolean changeName(String token, String NewName) throws RemoteException;

	public boolean changeMail(String token, String mail) throws RemoteException;

	public boolean setImage(String token, String img, byte[] data) throws RemoteException;

	public boolean checkLive(String token) throws RemoteException;

	public String getInfo(String token) throws RemoteException;

	public String getUserOnLimit(String token, String name) throws RemoteException;

	public boolean isAdmin(String token, String group) throws RemoteException;

	public void setAdmin(String token, String group, List<String> mem) throws RemoteException;

	public void delAdmin(String token, String group, List<String> mem) throws RemoteException;

	public String getAdminOnGroup(String token, String group) throws RemoteException;

	public String getNonAdminOnGroup(String token, String group) throws RemoteException;

	public String showFriendRq(String token) throws RemoteException;

	public boolean acceptFriendRequest(String token, List<String> member) throws RemoteException;

	public boolean deleteFriendRequest(String token, List<String> member) throws RemoteException;

	public boolean deleteComment(String token, String group, long date) throws RemoteException;

}
