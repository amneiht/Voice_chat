package dccan.server.control.user;

public class ClearToken implements Runnable {
	long time = 10 * 60 * 1000;

	@Override
	public void run() {
		int i = 0;
		int h = 20;
		while (true) {
			try {
				Thread.sleep(time);
				i++;
				PassToken.clear();
				UserToken.clear();
				if (i > h) {
					i = 0;
					ListUser2.clear();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
