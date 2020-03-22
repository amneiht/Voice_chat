package dccan.sql.file;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dccan.sql.config.Info;

public class SFile {

	public static String createFileId(String user) {
		String date = new Date(System.currentTimeMillis()).toString();
		int ran = (int) (Math.random() * 10000000);
		String out = user + date + ran;
		return Info.getMD5(out);
	}

	public static boolean insert(String id, InputStream in, String tenfile) {
		String sql = "insert into tepTin( idFile , ngayGui , data , tenFile ) values (?,?,?,?)";
		Connection con = Info.getCon();
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			Date dt = new Date(System.currentTimeMillis());
			pstm.setString(1, id);
			pstm.setDate(2, dt);
			pstm.setString(4, tenfile);
			int d = 0;
			if (in != null) {
				pstm.setBlob(3, in);
				d = pstm.executeUpdate();
			}
			pstm.close();
			con.close();
			return d > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
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
			con.close();
			return in;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
