package dccan.server.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import dccan.server.sql.config.Info;

public class Requests {
	static Gson gs = new Gson();
	public static boolean acceptRq(String group, String user, List<String> mem)
	{
		if (!checkpri(group, user))
			return false;
		deleteRq(group, user, mem);
		return Groups.acceptMem(user, group, mem);
	}
	public static String showRq(String group, String user) {
		if (!checkpri(group, user))
			return null;
		String sql = "select idTv from yeuCau where idNhom = ?";
		Connection con = Info.getCon();
		try {
			PreparedStatement pps = con.prepareStatement(sql);
			pps.setString(1, group);
			ResultSet ps = pps.executeQuery();
			List<String> lp = stringListInPoint(ps, 1);
			ps.close();
			pps.close();
			return gs.toJson(lp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static boolean deleteRq(String group, String user, List<String> mem) {
		if (!checkpri(group, user))
			return false;
		boolean res = false;
		String sql = "delete from yeuCau where idNhom = ? and idTv = ?";
		Connection con = Info.getCon();
		try {
			PreparedStatement pps = con.prepareStatement(sql);
			pps.setString(1, group);
			for (String m : mem) {
				pps.setString(2, m);
				pps.executeUpdate();
			}
			pps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return res;
	}

	public static boolean addRequest(String group, String user) {
		boolean res = false;
		String sql = "insert into yeuCau values (?,?)";
		Connection con = Info.getCon();
		try {
			PreparedStatement pps = con.prepareStatement(sql);
			pps.setString(1, group);
			pps.setString(2, user);
			int s = pps.executeUpdate();
			res = s == 1;
			pps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	static boolean checkpri(String group, String user) {
		boolean res = false;
		String sql = "select quyen from tvNhom where idNhom = ? and idTv = ?";
		Connection con = Info.getCon();
		try {
			PreparedStatement pps = con.prepareStatement(sql);
			pps.setString(1, group);
			pps.setString(2, user);
			ResultSet ps = pps.executeQuery();
			boolean d = ps.first();
			if (d) {
				int h = ps.getInt(1);
				res = h > 0;
			}
			ps.close();
			pps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	static List<String> stringListInPoint(ResultSet rs, int p) {
		List<String> res = new LinkedList<String>();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				res.add(rs.getString(p));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}
}
