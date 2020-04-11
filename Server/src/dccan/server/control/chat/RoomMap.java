package dccan.server.control.chat;

import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoomMap {
	public Map<Long, Room> lp = new HashMap<Long, Room>();

	public void send(long id, DatagramPacket ps) {
		Room rm = lp.get(id);
		if (rm == null)
			return;
		rm.addPacket(ps);
	}
	/**
	 * kiem tra co nhom chat hay khong
	 * @param d id nhom
	 * @return dung hoac sai
	 */
	public boolean check(String d)
	
	{
		Set<Long> ld = lp.keySet();
		for (long id : ld) {
			if (lp.get(id).group.equals(d))
				return true ;
		}
		return false ;
		
	}
	// tang thoi gian song cua user
	/**
	 * tang thoi gian song cua user
	 * @param id ma nhom
	 * @param user d cua user
	 */
	public void live(long id, long user) {
		Room rm = lp.get(id);
		if (rm == null)
			return;
		rm.live(user);
	}
	/**
	 * xoa 1 client ra khoi danh sanh
	 * @param id
	 * @param user
	 */
	public void bye(long id, long user) {
		Room rm = lp.get(id);
		if (rm == null)
			return;
		rm.bye(user);
	}
	public void addClient(long id, Client f) {
		Room rm = lp.get(id);
		if (rm == null)
			return;
		rm.addClient(f);
	}

	/**
	 * lay roomid , chua co thi tao moi
	 * @param d
	 * @return
	 */
	public long getRoomId(String d) {
		Set<Long> ld = lp.keySet();
		for (long id : ld) {
			if (lp.get(id).group.equals(d))
				return id;
		}

		Long res = System.currentTimeMillis() & 0xffffffff;
		try {
			Room rs = new Room(d);
			new Thread(rs).start();
			lp.put(res, rs);
			return res;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}

}
