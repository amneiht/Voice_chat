package dccan.server.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.gson.Gson;

import dccan.server.sql.config.Info;
import dccan.suport.Friend;
import dccan.suport.Group;

public class Groups {
	private final static String getGroup = "select nhom.idNhom as idNhom, tenNhom from nhom ,tvNhom where nhom.idNhom = tvNhom.idNhom and idTV=?";

	/**
	 * lay thong tin cac nhom chat
	 * 
	 * @param id
	 * @return
	 */
	public static String GetGroup(String id) {
		String res = "false";
		Connection con = Info.getCon();
		try {

			PreparedStatement ps = con.prepareStatement(getGroup);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			List<Group> gp = new ResultToList<Group>(Group.class).progess(rs);
			res = new Gson().toJson(gp);
			rs.close();
			ps.close();
			// con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Info.give(con);
		return res;
	}

	private static final String getMem = "select ten,nguoiDung ,idAnh ,email from thongtin , tvNhom where ten = idTV and idNhom = ?";

	public static boolean checkMember(String group, String user) {
		String sql = "select * from tvNhom where idNhom = ? and idTv=?";
		boolean res = false;
		Connection con = Info.getCon();
		try {

			PreparedStatement pps = con.prepareStatement(sql);
			pps.setString(1, group);
			pps.setString(2, user);
			ResultSet rs = pps.executeQuery();
			rs.beforeFirst();
			res = rs.next();
			rs.close();
			pps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		Info.give(con);
		return res;
	}

	/**
	 * lay thong tin thanh vien
	 * 
	 * @param id
	 * @return
	 */
	public static String getMember(String id) {
		String res = "false";
		Connection con = Info.getCon();
		try {

			PreparedStatement ps = con.prepareStatement(getMem);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			ArrayList<Friend> ap = new ResultToList<Friend>(Friend.class).progess(rs);
			res = new Gson().toJson(ap);
			rs.close();
			ps.close();
			// con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Info.give(con);
		return res;
	}

	private static final String addSql1 = "insert into nhom(idNhom,nguoiTao,tenNhom) values(?,?,?) ";
	private static final String addSql2 = "insert into tvNhom(idNhom,idTV,quyen,idAdd) values(?,?,?,?) ";

	/**
	 * them moi thanh vien
	 * 
	 * @param id
	 *            id cua thanh vien
	 * @param group
	 *            ma nhom
	 * @return
	 */
	public static String addMember(String id, String group, String user) {
		String res = "false";
		String sql = "call addMember(?,?,?)";
		Connection con = Info.getCon();
		try {

			CallableStatement cstm = con.prepareCall(sql);
			cstm.setString(1, group);
			cstm.setString(2, user);
			cstm.setString(3, id);
			cstm.executeUpdate();
			cstm.close();
			// con.close();
			res = "ok";
		} catch (Exception e) {
			e.printStackTrace();
		}
		Info.give(con);
		return res;
	}

	/**
	 * them nhom chat
	 * 
	 * @param id
	 *            id nguoi tao
	 * @param member
	 *            cac thanh vien
	 * @param ten
	 *            nhom
	 */
	public static String addGroup(String id, List<String> member, String ten) {
		String res = "false";
		Connection con = Info.getCon();
		try {

			PreparedStatement ps = con.prepareStatement(addSql1);
			String nhom = RandomStringUtils.randomAlphanumeric(32);
			ps.setString(1, nhom);
			ps.setString(2, id);
			ps.setString(3, ten);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement(addSql2);
			ps.setString(4, id);
			ps.setString(1, nhom);
			ps.setString(2, id);
			ps.setInt(3, 1);
			ps.executeUpdate();
			ps.setInt(3, 0);
			for (String d : member) {
				ps.setString(2, d);
				ps.executeUpdate();
			}
			ps.close();
			res = "ok";
		} catch (Exception e) {
			e.printStackTrace();
		}
		Info.give(con);
		return res;
	}

	public static boolean acceptMem(String id, String group, List<String> mem) {
		boolean res = false;
		Connection con = Info.getCon();
		try {
			String addSql2 = "insert into tvNhom(idNhom,idTV,quyen,idAdd) values(?,?,?,?) ";

			PreparedStatement ps = con.prepareStatement(addSql2);
			ps = con.prepareStatement(addSql2);
			ps.setString(1, group);
			ps.setString(4, id);
			ps.setInt(3, 0);
			for (String d : mem) {
				ps.setString(2, d);
				ps.executeUpdate();
			}
			ps.close();
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Info.give(con);
		return res;
	}

	private static final String addF = "insert into banBe(id1,id2) values(?,?)";
	private static final String checkF = "select id1 from banBe where (id1 = ? and id2 = ?) or (id1 = ? and id2 = ?) ";

	/**
	 * them 1 nguoi ban
	 * 
	 * @param id1
	 * @param id2
	 */
	public static boolean addFriend(String id1, String id2) {
		boolean res = false;
		Connection con = Info.getCon();
		try {

			PreparedStatement ps = con.prepareStatement(checkF);
			ps.setString(1, id1);
			ps.setString(2, id2);
			ps.setString(4, id1);
			ps.setString(3, id2);
			ResultSet rs = ps.executeQuery();
			rs.beforeFirst();
			if (!rs.next()) {
				ps.close();
				ps = con.prepareStatement(addF);
				ps.setString(1, id1);
				ps.setString(2, id2);
				ps.executeUpdate();
				res = true;
			} else {
				rs.close();
				ps.close();
				res = false;
			}
			rs.close();
			ps.close();

			ps = con.prepareStatement(addSql1);
			String nhom = RandomStringUtils.randomAlphanumeric(32);
			ps.setString(1, nhom);
			ps.setString(2, id1);
			ps.setString(3, id1 + "_" + id2);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement(addSql2);
			ps.setString(1, nhom);
			ps.setString(2, id1);
			ps.executeUpdate();
			ps.setString(1, nhom);
			ps.setString(2, id2);
			ps.executeUpdate();
			ps.close();
			// con.close();

		} catch (Exception e) {

			System.err.println(e);
		}
		Info.give(con);
		return res;
	}

	private static final String getFlist = "SELECT ten,nguoiDung ,idAnh , email FROM banBe , thongtin where id1 = ? and id2 = ten UNION SELECT ten,nguoiDung ,idAnh , email  FROM banBe , thongtin where id2 = ? and id1 = ten ";

	/**
	 * lay danh sanh ban be
	 * 
	 * @param id
	 *            nguoi lay
	 * @return
	 */
	public static String getFrendList(String id) {
		Connection con = Info.getCon();
		String gson = null;
		try {

			PreparedStatement ps = con.prepareStatement(getFlist);
			ps.setString(1, id);
			ps.setString(2, id);
			ResultSet rs = ps.executeQuery();

			ArrayList<Friend> ap = new ResultToList<Friend>(Friend.class).progess(rs);
			gson = new Gson().toJson(ap);
			rs.close();
			ps.close();
		} catch (Exception e) {
			System.err.println(e);

		}
		Info.give(con);
		return gson;
	}

	/**
	 * xoa 1 thanh viên khoi nhom
	 * 
	 * @param id
	 * @param group
	 * @return
	 */
	public static boolean deleteMember(String group, List<String> mem, String user) {
		boolean res = false;
		String sql = "call deleteMember(?,?,?)";
		Connection con = Info.getCon();
		try {

			CallableStatement cstm = con.prepareCall(sql);
			cstm.setString(1, group);
			cstm.setString(2, user);
			for (String id : mem) {
				cstm.setString(3, id);
				cstm.executeUpdate();
			}
			cstm.close();
			// con.close();
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Info.give(con);
		return res;
	}

	public static boolean deleteFriend(String id1, List<String> mem) {
		String sql = "delete from banBe where (id1 = ? and id2 = ?) or (id1 = ? and id2 = ?)";
		Connection con = Info.getCon();
		boolean res = false;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id1);
			ps.setString(4, id1);
			for (String id2 : mem) {

				ps.setString(2, id2);
				ps.setString(3, id2);
				ps.executeUpdate();
			}
			ps.close();
			res = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Info.give(con);
		return res;
	}

	public static void outGroup(String group, String user) {
		String sql = " delete from tvNhom where idNhom = ? and idTV = ?";
		Connection con = Info.getCon();
		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, group);
			ps.setString(2, user);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Info.give(con);
	}

	public static void deleteGroup(String group, String user) {
		String sql = "call deleteGroup(?,?)";
		Connection con = Info.getCon();
		try {

			CallableStatement cstm = con.prepareCall(sql);
			cstm.setString(1, group);
			cstm.setString(2, user);
			System.out.println(cstm.toString());
			cstm.executeUpdate();
			cstm.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Info.give(con);
	}
}
