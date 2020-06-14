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

	public static boolean acceptRq(String group, String user, List<String> mem) {
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
		String res = null;
		try {

			PreparedStatement pps = con.prepareStatement(sql);
			pps.setString(1, group);
			ResultSet ps = pps.executeQuery();
			List<String> lp = stringListInPoint(ps, 1);
			ps.close();
			pps.close();
			res = gs.toJson(lp);
		} catch (SQLException e) {
			e.printStackTrace();

		}
		Info.give(con);
		return res;
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

			e.printStackTrace();

		}
		Info.give(con);
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

			e.printStackTrace();
		}
		Info.give(con);
		return res;
	}

	public static boolean checkpri(String group, String user) {
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
			e.printStackTrace();
		}
		Info.give(con);
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

			e.printStackTrace();
		}

		return res;
	}

	public static String showFriendRq(String user) {

		String sql = "select idTv from yeuCau where idNhom = ?";
		Connection con = Info.getCon();
		String res = null;
		try {

			PreparedStatement pps = con.prepareStatement(sql);
			pps.setString(1, user);
			ResultSet ps = pps.executeQuery();
			List<String> lp = stringListInPoint(ps, 1);
			ps.close();
			pps.close();
			res = gs.toJson(lp);
		} catch (SQLException e) {

			e.printStackTrace();

		}
		Info.give(con);
		return res;
	}

	static final String getFlist = "SELECT id2 as ten FROM banBe where id1 = ? UNION SELECT id1 as ten FROM banBe where id2 = ?";

	public static boolean acceptFriend(String user, List<String> member) {
		String sql = "insert into banBe(id1,id2) values(?,?)";
		String dsql = "delete from yeuCau where idNhom = ? and idTv = ?";
		Connection con = Info.getCon();
		boolean res = false;
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, user);
			List<String> lp = friendInList(con, user);
			for (String p : member) {
				int h = lp.indexOf(p);
				if (h < 0) {
					st.setString(2, p);
					st.executeUpdate();
				}
			}
			st.close();
			st = con.prepareStatement(dsql);
			st.setString(1, user);
			for (String p : member) {
				st.setString(2, p);
				st.executeUpdate();
			}
			st.close();
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Info.give(con);
		return res;
	}

	public static boolean deleteFriendRq(String user, List<String> member) {
		String dsql = "delete from yeuCau where idNhom = ? and idTv = ?";
		Connection con = Info.getCon();
		boolean res = false;
		try {
			PreparedStatement st = con.prepareStatement(dsql);
			st.setString(1, user);
			for (String p : member) {
				st.setString(2, p);
				st.executeUpdate();
			}
			st.close();
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Info.give(con);
		return res;
	}

	static private List<String> friendInList(Connection con, String user) {
		try {
			PreparedStatement st = con.prepareStatement(getFlist);
			st.setString(1, user);
			st.setString(2, user);
			ResultSet rs = st.executeQuery();
			List<String> res = new ResultToList<String>(String.class).getListFromResult(rs, "ten");
			st.close();
			rs.close();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static boolean addFriendRequest(String id, String user) {
		boolean res = false;
		String sql = "insert into yeuCau values (?,?)";
		Connection con = Info.getCon();
		try {
			List<String> lp = friendInList(con, user);
			int h = lp.indexOf(id);
			if (h < 0) {
				PreparedStatement pps = con.prepareStatement(sql);
				pps.setString(1, id);
				pps.setString(2, user);
				pps.executeUpdate();
				res = true;
				pps.close();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		Info.give(con);
		return res;
	}
}
