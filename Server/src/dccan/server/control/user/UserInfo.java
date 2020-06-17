package dccan.server.control.user;

public class UserInfo {
	long time;
	private String pass, email, nguoiDung, ten;

	public UserInfo(String user, String pass, String hoten, String email) {
		this.pass = pass;
		this.nguoiDung = hoten;
		this.email = email;
		this.ten = user;
		time = System.currentTimeMillis();
	}

	public long getTime() {
		return time;
	}

	public String getPass() {
		return pass;
	}

	public String getEmail() {
		return email;
	}

	public String getNguoiDung() {
		return nguoiDung;
	}

	public String getTen() {
		return ten;
	}

}
