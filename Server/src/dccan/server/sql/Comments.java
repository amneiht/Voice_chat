package dccan.server.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gson.Gson;

import dccan.server.sql.config.Info;
import dccan.suport.Comment;

public class Comments {
	private static final String upsql = "insert into tinNhan(idNhan , idGui,noiDung ,ngayGui) values (?,?,?,?)";
	private static final String upFilesql = "insert into tinNhan(idNhan , idGui,noiDung ,ngayGui,idFile) values (?,?,?,?,?)";

	/**
	 * up file comnent len sql
	 * 
	 * @param idnhan
	 *            id nguoi nhan
	 * @param idGui
	 *            id nguoi gui
	 * @param noiDung
	 *            ten tep gui len
	 * @param idFile
	 *            id cua fle gui
	 */
	public static String upFcoment(String idnhan, String idGui, String noiDung, String idFile) {
		String res = "false";
		try {
			Connection con = Info.getCon();
			Long tm = System.currentTimeMillis();
			PreparedStatement ps = con.prepareStatement(upFilesql);
			String time = new Timestamp(tm).toString();
			ps.setString(1, idnhan);
			ps.setString(2, idGui);
			ps.setString(3, noiDung);
			ps.setString(4, time);
			ps.setString(5, idFile);
			ps.executeUpdate();
			ps.close();
			con.close();
			res = "ok";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * up tin nhan len csdl
	 * 
	 * @param idnhan
	 *            id nguoi nhan
	 * @param idGui
	 *            id nguoi gui
	 * @param noiDung
	 *            noi dung tin nhan
	 * @return
	 */
	public static String up(String idnhan, String idGui, String noiDung) {
		String res = "false";
		try {
			Connection con = Info.getCon();
			Long tm = System.currentTimeMillis();
			PreparedStatement ps = con.prepareStatement(upsql);
			String time = new Timestamp(tm).toString();
			ps.setString(1, idnhan);
			ps.setString(2, idGui);
			ps.setString(3, noiDung);
			ps.setString(4, time);
			ps.executeUpdate();
			ps.close();
			con.close();
			res = "ok";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return res;
	}

	private final static String getSql = "select * from tinNhan where idNhan = ? and ngayGui < ? limit 30";

	/**
	 * lay ve cac thong tin trong bang tin nhan
	 * 
	 * @param id
	 *            nguoi nhan
	 * @param time
	 *            thoi diem muon lay
	 * @return tra ve chuoi json
	 */
	public static String getChat(String id, String time) {
		try {
			Connection con = Info.getCon();
			PreparedStatement ps = con.prepareStatement(getSql);
			ps.setString(1, id);
			ps.setString(2, time);
			ResultSet rs = ps.executeQuery();
			ArrayList<Comment> ap = new ResultToList<Comment>(Comment.class).progess(rs);
			String gson = new Gson().toJson(ap);
			rs.close();
			ps.close();
			con.close();
			return gson;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "false";
	}
}
