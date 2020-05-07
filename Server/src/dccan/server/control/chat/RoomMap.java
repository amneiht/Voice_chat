package dccan.server.control.chat;

import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoomMap implements Runnable {
	public Map<Long, Room> lp = new HashMap<Long, Room>();
	public Map<String, Long> lr = new HashMap<String, Long>();
	boolean run = true;

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

	public String checkUserKey(long gp, String user) {
		Room rm = lp.get(gp);
		return rm.checkUserKey(user);
	}

	public String checkUserKey(long gp, byte[] user) {
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

	public String createUserKey(String gp, String user) {
		System.out.println("user key +"+user);
		Object ps = lr.get(gp);
		if (ps == null)
			return null;
		long id = (long) ps;
		try {
			Room rs = lp.get(id);
			return rs.createUserKey(user);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String getGroupKey(String gp) {
		synchronized (lr) {

			Object ps = lr.get(gp);
			if (ps != null) {
				long id = (long) ps;
				Room rs = lp.get(id);
				return rs.getKey();
			}
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

	@Override
	public void run() {
		System.out.println("remove run");
		while (run) {
			try {
				Thread.sleep(10000);
				synchronized (lr) {
					Set<String> sp = lr.keySet();
					for (String name : sp) {
						long id = lr.get(name);
						Room rm = lp.get(id);
						rm.clear();
						if (rm.end) {
							lp.remove(id);
							lr.remove(name);
							System.out.println("remove room on room map");
						}
					}
				}

			} catch (Exception e) {
			}
		}

	}
}
