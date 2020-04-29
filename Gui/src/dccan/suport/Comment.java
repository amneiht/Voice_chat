package dccan.suport;

import java.sql.Timestamp;

public class Comment {
	private String idNhan, idGui, noiDung, idFile;
	//private String idNhan =null, idGui=null, noiDung=null, idFile=null, ngayGui=null;
	private Timestamp ngayGui ;
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

	public String getNgayGui() {
		return ngayGui.toString();
	}
}
