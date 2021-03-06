package dccan.server.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import dccan.server.sercu.AES;
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
		Connection con = Info.getCon();
		try {
			Long tm = System.currentTimeMillis();
			PreparedStatement ps = con.prepareStatement(upFilesql);
			ps.setString(1, idnhan);
			ps.setString(2, idGui);
			ps.setString(3, StringEnCrypt(idnhan, noiDung));
			ps.setLong(4, tm);
			ps.setString(5, idFile);
			ps.executeUpdate();
			ps.close();
			res = "ok";
		} catch (Exception e) {
			e.printStackTrace();
		}
		Info.give(con);
		return res;
	}

	public static void StringDeCrypt(String group, List<Comment> ap) {
		for (Comment cmt : ap) {
			cmt.setNoiDung(AES.deCrypt(cmt.getNoiDung()));
		}
	}

	public static String StringEnCrypt(String group, String noiDung) {
		return AES.enCrypt(noiDung);
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
		Connection con = Info.getCon();
		try {
			PreparedStatement ps = con.prepareStatement(upsql);
			long time = getTime();
			ps.setString(1, idnhan);
			ps.setString(2, idGui);
			ps.setString(3, StringEnCrypt(idnhan, noiDung));
			ps.setLong(4, time);
			ps.executeUpdate();
			ps.close();
			// con.close();
			res = "ok";

		} catch (Exception e) {
			e.printStackTrace();
		}
		Info.give(con);
		return res;
	}

	private static long getTime() {
		return System.currentTimeMillis();
	}

	private final static String getSql = "select * from tinNhan where idNhan = ? and ngayGui < ?  order by ngayGui DESC limit 20";

	/**
	 * lay ve cac thong tin trong bang tin nhan
	 * 
	 * @param id
	 *            nguoi nhan
	 * @param time
	 *            thoi diem muon lay
	 * @return tra ve chuoi json
	 */
	public static String getOldChat(String id, long time) {
		Connection con = Info.getCon();
		String gson = null;
		try {
			PreparedStatement ps = con.prepareStatement(getSql);
			ps.setString(1, id);
			ps.setLong(2, time);
			ResultSet rs = ps.executeQuery();
			ArrayList<Comment> ap = new ResultToList<Comment>(Comment.class).progess(rs);
			StringDeCrypt(id, ap);
			gson = new Gson().toJson(ap);
			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Info.give(con);
		return gson;
	}

	private final static String getNSql = "select * from tinNhan where idNhan = ? and ngayGui > ? order by ngayGui ASC limit 30";

	/**
	 * lay ve cac thong tin trong bang tin nhan theo tg moi hon
	 * 
	 * @param id
	 *            nguoi nhan
	 * @param time
	 *            thoi diem muon lay
	 * @return tra ve chuoi json
	 */
	public static String getNewChat(String id, long time) {
		Connection con = Info.getCon();

		try {

			PreparedStatement ps = con.prepareStatement(getNSql);
			ps.setString(1, id);
			ps.setLong(2, time);
			ResultSet rs = ps.executeQuery();
			ArrayList<Comment> ap = new ResultToList<Comment>(Comment.class).progess(rs);
			StringDeCrypt(id, ap);
			String gson = new Gson().toJson(ap);
			rs.close();
			ps.close();
			Info.give(con);
			// con.close();
			Info.give(con);
			return gson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Info.give(con);
		return "false";
	}

	public static boolean delCommand(String user, String group, long date) {
		Connection con = Info.getCon();
		boolean res = false;
		String sql = "delete from tinNhan where idNhan = ? and ngayGui = ? and idGui =? ";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, group);
			ps.setLong(2, date);
			ps.setString(3, user);
			ps.executeUpdate();
			ps.close();
			res = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		Info.give(con);
		return res;
	}
}
