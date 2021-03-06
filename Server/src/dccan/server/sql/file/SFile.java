package dccan.server.sql.file;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dccan.server.sql.config.Info;

public class SFile {

	public static String createFileId(String user) {
		String date = new Date(System.currentTimeMillis()).toString();
		int ran = (int) (Math.random() * 10000000);
		String out = user + date + ran;
		return Info.getMD5(out);
	}

	public static String insert(String id, InputStream in, String tenfile) {
		String res = "false";
		String sql = "insert into tepTin( idFile , ngayGui , data , tenFile ) values (?,?,?,?)";

		try {
			Connection con = Info.getCon();
			PreparedStatement pstm = con.prepareStatement(sql);
			Date dt = new Date(System.currentTimeMillis());
			pstm.setString(1, id);
			pstm.setDate(2, dt);
			pstm.setString(4, tenfile);
			if (in != null) {
				pstm.setBlob(3, in);
				pstm.executeUpdate();
				res = "ok";
			}
			pstm.close();
			// con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return res;
	}

	public static InputStream downloadFile(String id) {
		try {
			InputStream in = null;
			String sql = "select data from tepTin where idFile = ?";
			Connection con = Info.getCon();
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, id);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				Blob bl = rs.getBlob("data");
				in = bl.getBinaryStream();
			}
			rs.close();
			pstm.close();
			// con.close();
			return in;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
