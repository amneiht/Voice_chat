package dccan.suport;

import java.sql.Timestamp;

public class Comment {
	private String idNhan, idGui, noiDung, idFile;
	private long ngayGui;

	public Comment(boolean d) {
		idNhan = null;
		idGui = null;
		noiDung = null;
		if (d) {
			ngayGui = 0;
		} else {
			ngayGui = Long.MAX_VALUE;
		}
	}

	public Comment() {

	}

	public String getIdNhan() {
		return idNhan;
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

	public long lgetNgayGui() {
		return ngayGui;
	}

	public String getNgayGui() {
		return new Timestamp(ngayGui).toString();
	}
}
