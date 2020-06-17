package dccan.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Rmt extends Remote {
	public String login(String user, String pass) throws RemoteException;

	public void register(String user, String pass, String hoten, String email) throws RemoteException;

	public boolean confirmRegister(String au) throws RemoteException ;

	public String getMember( String group) throws RemoteException;

	public String getGroup() throws RemoteException;

	public boolean addFriend( String id) throws RemoteException;

	public String getFriendList() throws RemoteException;

	public String getCommentList( String group, long date, boolean status) throws RemoteException;

	public byte[] dowload( String idFile) throws RemoteException;

	public boolean upload( String name, byte[] data, String group) throws RemoteException;

	public boolean deleteMember( String group, List<String> mem) throws RemoteException;

	public boolean deleteFriend( List<String> mem) throws RemoteException;

	public boolean createGroup( String name, List<String> member) throws RemoteException;

	public boolean addMember( String group, String id) throws RemoteException;

	public boolean addMember( String group, List<String> id) throws RemoteException;

	public boolean logout() throws RemoteException;

	public boolean upComment( String group, String nDung) throws RemoteException;

	public String showFriendInfo( String s) throws RemoteException;

	public void outGroup( String group) throws RemoteException;

	public void deleteGroup( String group) throws RemoteException;

	public String getUserkey( String group) throws RemoteException;

	public String chatMember( String group) throws RemoteException;

	public boolean requestGroup( String group) throws RemoteException;

	public String showRequest( String group) throws RemoteException;

	public boolean acceptRequest( String group, List<String> member) throws RemoteException;

	public boolean deleteRequest( String group, List<String> member) throws RemoteException;

	public boolean changeName( String NewName) throws RemoteException;

	public boolean changeGroupName( String group, String NewName) throws RemoteException;

	public boolean newPassword(String au, String pass) throws RemoteException;

	public void resetPass(String user) throws RemoteException;

	public boolean changeMail( String mail) throws RemoteException;

	public boolean confirmChangeMail(String id) throws RemoteException;

	public boolean setImage( String img, byte[] data) throws RemoteException;

	public boolean checkLive() throws RemoteException;

	public String getInfo() throws RemoteException;

	public String getUserOnLimit( String name) throws RemoteException;

	public boolean isAdmin( String group) throws RemoteException;

	public void setAdmin( String group, List<String> mem) throws RemoteException;

	public void delAdmin( String group, List<String> mem) throws RemoteException;

	public String getAdminOnGroup( String group) throws RemoteException;

	public String getNonAdminOnGroup( String group) throws RemoteException;

	public String showFriendRq() throws RemoteException;

	public boolean acceptFriendRequest( List<String> member) throws RemoteException;

	public boolean deleteFriendRequest( List<String> member) throws RemoteException;

	public boolean deleteComment( String group, long date) throws RemoteException;

}
