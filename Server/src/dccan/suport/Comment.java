package dccan.suport;

import java.sql.Timestamp;

public class Comment {
	private String idNhan, idGui, noiDung, idFile;
	private long ngayGui;

	public String getIdNhan() {
		return idNhan;
	}

	public void setNoiDung(String s) {
		noiDung = s;
	}

	public String getIdGui() {
		return idGui;
	}

	public String getNoiDung() {
		return noiDung;
	}

	public String getIdFile() {
		return idFile;
	}

	public String getNgayGui() {
		return new Timestamp(ngayGui).toString();
	}
}
