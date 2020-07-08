package dccan.server.control.chat;

import java.net.SocketException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.RandomStringUtils;

public class Room {
	public static long timeout = 10 * 1000;// 5 s
	private String key;
	protected List<String> user = new LinkedList<String>();
	protected Map<String, String> map = new HashMap<String, String>();
	Cipher cipher;

	private void initDecrypt() {
		String subkey = key.substring(0, 8);
		SecretKeySpec skeySpec = new SecretKeySpec(subkey.getBytes(), "DES");

		try {
			cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Room(String d) throws SocketException {
		group = d;
		key = RandomStringUtils.randomAscii(24);
		initDecrypt();
	}

	private String getInetFromData(byte[] dta) {
		try {
			byte[] en = cipher.doFinal(dta);
			String lps = new String(en);
			System.out.println(lps);
			String[] ls = lps.split(":");
			return ls[1];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
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
		Collection<Client> mem2 = mem.values();
		for (Client cl : mem2) {
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

	// DatagramSocket sc;
	protected String group;
	// long rtp; // ma tham so rtp
	// protected List<Client> mem = new ArrayList<Client>();// tru nhap de dang
	protected Map<Long, Client> mem = new HashMap<Long, Client>();

	public List<Client> getMem() {

		return new LinkedList<Client>(mem.values());
	}

	boolean end = false;

	public void live(long p, Flagment fg, byte[] key) {
		String ip = getInetFromData(key);
		if (ip != null)
			return;
		Client c = mem.get(p);
		if (c != null) {
			System.out.println(c.ina.getAddress().toString().trim());
			if (c.ina.getAddress().toString().trim().equals(ip)) {
				c.live = System.currentTimeMillis();
				c.port = fg.port;
			}
		}
	}
	public void live(long p, Flagment fg) {
		Client c = mem.get(p);
		if (c != null) {
				c.live = System.currentTimeMillis();
				c.port = fg.port;
		}
	}
	public void bye(long id) {
		mem.remove(id);
	}

	public void clear() {
		long now = System.currentTimeMillis();
		synchronized (mem) {
			Iterator<Map.Entry<Long, Client>> ms = mem.entrySet().iterator();
			for (; ms.hasNext();) {
				Map.Entry<Long, Client> x = ms.next();
				if (now - x.getValue().live > timeout)
					ms.remove();
			}
			end = mem.size() == 0;
		}

	}

	public void addClient(Client s) {
		synchronized (mem) {
			mem.put(s.id, s);
		}
	}
}
