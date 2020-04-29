package dccan.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Communication extends Remote {
	public boolean login(String user, String pass) throws RemoteException;

	public boolean register(String user, String pass, String hoten, String email) throws RemoteException;

	public String getMember(String group) throws RemoteException;

	public String getGroup() throws RemoteException;

	public boolean addFriend(String id) throws RemoteException;

	public String getFriendList() throws RemoteException;

	public String getCommentList(String group, String date, boolean status) throws RemoteException;

	public byte[] dowload(String idFile) throws RemoteException;

	public boolean upload(String name, byte[] data, String group) throws RemoteException;

	public boolean deleteMember(String group, String mem) throws RemoteException;

	public boolean createGroup(String name, List<String> member) throws RemoteException;

	public boolean addMember(String group, String id) throws RemoteException;

	public boolean upComment(String group, String nDung) throws RemoteException;

	public void outGroup(String group) throws RemoteException;

	public void deleteGroup(String group) throws RemoteException;

	public String getUserkey(String group) throws RemoteException;

	public String chatMember(String group) throws RemoteException;

	public boolean requestGroup(String group) throws RemoteException;

	public String showRequest(String group) throws RemoteException;

	public boolean acceptRequest(String group, List<String> member) throws RemoteException;

	public boolean deleteRequest(String group, List<String> member) throws RemoteException;
}
