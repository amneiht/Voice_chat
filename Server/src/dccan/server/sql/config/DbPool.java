package dccan.server.sql.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class DbPool implements Runnable {
	private int min = 5, max = 8;
	private String user, pass, url;
	private LinkedList<Connection> ls = new LinkedList<Connection>();
	private int list;
	boolean run = true;

	public void setUser(String user) {
		this.user = user;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setMinConnection(int b) {
		min = b;
	}

	public void setMaxConnection(int b) {
		max = b;
	}

	public void init() {
		try {
			for (int i = 0; i < min; i++) {
				ls.add(makeCon());
			}
			list = min - 1;
			new Thread(this).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void give(Connection con) {
		ls.add(con);
	}

	public synchronized Connection getConnection() {
		Connection con = null;
		try {
			while (ls.size() < 1) {
				
				if (list < max) {
					ls.add(makeCon());
					list++;
					break;
				}
				wait();

			}
			con = ls.remove(0);
			if (con.isClosed())
				con = makeCon();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	private Connection makeCon() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	@Override
	public void run() {
		while (run) {
			try {
				Thread.sleep(10000);
				synchronized (ls) {
					int s = ls.size();
					for (int i = s - 1; i > -1; i--) {
						if (ls.get(i).isClosed()) {
							ls.remove(i);
							list = list - 1;
						}
					}
					if (list < min - 1) {
						for (int i = list; i < min; i++) {
							ls.add(makeCon());
						}
						list = min - 1;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
