package dccan.server.control.chat;

import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoomMap {
	public Map<Long, Room> lp = new HashMap<Long, Room>();
	public Map<String, Long> lr = new HashMap<String, Long>();

	public void send(long id, DatagramPacket ps) {
		Room rm = lp.get(id);
		if (rm == null)
			return;
		rm.addPacket(ps);
	}

	/**
	 * kiem tra co nhom chat hay khong
	 * 
	 * @param d
	 *            id nhom
	 * @return dung hoac sai
	 */
	public boolean checkall(String d) {
		Set<Long> ld = lp.keySet();
		for (long id : ld) {
			if (lp.get(id).group.equals(d))
				return true;
		}
		return false;

	}

	// tang thoi gian song cua user
	/**
	 * tang thoi gian song cua user
	 * 
	 * @param id
	 *            ma nhom
	 * @param user
	 *            d cua user
	 */
	public void live(long id, long user) {
		Room rm = lp.get(id);
		if (rm == null)
			return;
		rm.live(user);
	}

	/**
	 * xoa 1 client ra khoi danh sanh
	 * 
	 * @param id
	 * @param user
	 */
	public void bye(long id, long user) {
		Room rm = lp.get(id);
		if (rm == null)
			return;
		rm.bye(user);
	}

	public boolean checkUserKey(long gp, String user) {
		Room rm = lp.get(gp);
		return rm.checkUserKey(user);
	}

	public void addClient(long id, Client f) {
		Room rm = lp.get(id);
		if (rm == null)
			return;
		rm.addClient(f);
	}

	/**
	 * lay roomid , chua co thi tao moi
	 * 
	 * @param d
	 * @return
	 */
	public long getRoomId(String d) {
		Object ps = lr.get(d);
		if (ps != null)
			return (long) ps;
		return -1;
	}

	public String createUserKey(String gp) {
		Object ps = lr.get(gp);
		if (ps == null)
			return null;
		long id = (long) ps;
		try {
			Room rs = lp.get(id);
			return rs.createUserKey();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String getGroupKey(String gp) {
		Object ps = lr.get(gp);
		if (ps == null)
			return null;

		long res = (long) (Math.random() * 0xffffffffL);// 4 bit
		try {
			lr.put(gp, res);
			Room rs = new Room(gp);
			lp.put(res, rs);
			return rs.getKey();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
