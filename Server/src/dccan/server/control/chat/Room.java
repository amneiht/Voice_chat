package dccan.server.control.chat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

public class Room {
	public static long timeout = 5 * 1000;// 5 s
	private String key;
	protected List<String> user = new LinkedList<String>();
	protected Map<String, String> map = new HashMap<String, String>();

	public Room(String d) throws SocketException {
		sc = new DatagramSocket();
		group = d;
		key = RandomStringUtils.randomAscii(24);
	}

	public synchronized String createUserKey(String mem) {
		String res = RandomStringUtils.randomAscii(8);
		map.put(res, mem);
		return res;
	}

	public String checkUserKey(byte[] in) {
		String test = new String(in);
		String res = null;
		synchronized (map) {
			res = map.remove(test);
		}
		System.out.println(test);
		System.out.println(res);
		if (res == null)
			return res;
		return checkListUser(res);
	}

	/**
	 * tac dung : chi cho phep 1 user cua moi tai khoan ket noi vao
	 * 
	 * @param res
	 * @return
	 */
	private String checkListUser(String res) {
		
		
		for (Client cl : mem) {
			if (cl.user.equals(res))
				return null;
		}
		return res;
	}

	public String checkUserKey(String test) {
		String res = null;
		synchronized (map) {
			res = map.remove(test);
		}
		if (res == null)
			return res;
		return checkListUser(res);
	}

	public String getKey() {
		return key;
	}

	DatagramSocket sc;
	protected String group;
	// long rtp; // ma tham so rtp
	List<DatagramPacket> list = new LinkedList<DatagramPacket>(); // khoi tao de dang , phu hop thay doi
	protected List<Client> mem = new LinkedList<Client>();// tru nhap de dang

	public List<Client> getMem() {
		return mem;
	}

	boolean end = false;

	public void live(long p) {
		for (Client c : mem) {
			if (c.id == p) {
				c.live = System.currentTimeMillis();
				return;
			}
		}
	}

	public void bye(long id) {
		for (int i = 0; i < mem.size(); i++) {
			if (mem.get(i).id == id) {
				mem.remove(i);
				return;
			}
		}
	}

	public void clear() {
		long now = System.currentTimeMillis();
		for (int i = mem.size() - 1; i > -1; i--) {
			if (now - mem.get(i).live > timeout) {
				mem.remove(i);
			}
		}
		if (mem.size() == 0)
			end = true;
	}

	public void addClient(Client s) {
		mem.add(s);
	}

	public void addPacket(DatagramPacket dp) {
		list.add(dp);
	}
}
