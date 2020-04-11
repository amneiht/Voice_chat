package dccan.server.sql;

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
import dccan.suport.GroupInfo;

public class Group {
	private final static String getGroup = "select idNhom , tenNhom from nhom ,tvNhom where nhom.idNhom = tvNhom.idNhom and idTV=?";

	/**
	 * lay thong tin cac nhom chat
	 * 
	 * @param id
	 * @return
	 */
	public static String GetGroup(String id) {
		String res = "false";
		try {
			Connection con = Info.getCon();
			PreparedStatement ps = con.prepareStatement(getGroup);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			List<GroupInfo> gp = new ResultToList<GroupInfo>(GroupInfo.class).progess(rs);
			res = new Gson().toJson(gp);
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	private static final String getMem = "select ten,nguoiDung from thongtin , tvNhom where ten = idTV and idNhom = ?";

	/**
	 * lay thong tin thanh vien
	 * 
	 * @param id
	 * @return
	 */
	public static String getMember(String id) {
		String res = "false";
		try {
			Connection con = Info.getCon();
			PreparedStatement ps = con.prepareStatement(getMem);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			ArrayList<Friend> ap = new ResultToList<Friend>(Friend.class).progess(rs);
			res = new Gson().toJson(ap);
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	private static final String addSql1 = "insert into nhom(idNhom,nguoiTao,tenNhom) values(?,?,?) ";
	private static final String addSql2 = "insert into tvNhom(idNhom,idTV) values(?,?) ";

	/**
	 * them moi thanh vien
	 * 
	 * @param id
	 *            id cua thanh vien
	 * @param group
	 *            ma nhom
	 * @return
	 */
	public static String addMember(String id, String group) {
		String res = "false";
		try {
			Connection con = Info.getCon();
			PreparedStatement ps = con.prepareStatement(addSql2);
			ps.setString(1, group);
			ps.setString(2, id);
			ps.executeUpdate();
			ps.close();
			con.close();
			res = "ok";
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		try {
			Connection con = Info.getCon();
			PreparedStatement ps = con.prepareStatement(addSql1);
			String nhom = RandomStringUtils.randomAlphanumeric(32);
			ps.setString(1, nhom);
			ps.setString(2, id);
			ps.setString(3, ten);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement(addSql2);
			ps.setString(1, nhom);
			ps.setString(2, id);
			ps.executeUpdate();
			for (String d : member) {
				ps.setString(2, d);
				ps.executeUpdate();
			}
			ps.close();
			con.close();
			res = "ok";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static String addGroup(String id, String[] member, String ten) {
		String res = "false";
		try {
			Connection con = Info.getCon();
			PreparedStatement ps = con.prepareStatement(addSql1);
			String nhom = RandomStringUtils.randomAlphanumeric(32);
			ps.setString(1, nhom);
			ps.setString(2, id);
			ps.setString(3, ten);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement(addSql2);
			ps.setString(1, nhom);
			ps.setString(2, id);
			ps.executeUpdate();
			for (String d : member) {
				ps.setString(2, d);
				ps.executeUpdate();
			}
			ps.close();
			con.close();
			res = "ok";
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public static String addFriend(String id1, String id2) {
		String res = "false";
		try {
			Connection con = Info.getCon();
			PreparedStatement ps = con.prepareStatement(checkF);
			ps.setString(1, id1);
			ps.setString(2, id2);
			ps.setString(4, id1);
			ps.setString(3, id2);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				ps.close();
				ps = con.prepareStatement(addF);
				ps.setString(1, id1);
				ps.setString(2, id2);
				ps.executeUpdate();

				res = "ok";
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
			con.close();

		} catch (Exception e) {

			System.err.println(e);
		}
		return res;
	}

	private static final String getFlist = "select ten,nguoiDung from banBe,thongtin where ( banBe.id1 = thongtin.ten or banBe.id2 = thongtin.ten)  and ( banBe.id1=? or banBe.id2=?)";

	/**
	 * lay danh sanh ban be
	 * 
	 * @param id
	 *            nguoi lay
	 * @return
	 */
	public static String getFrendList(String id) {

		try {
			Connection con = Info.getCon();
			PreparedStatement ps = con.prepareStatement(getFlist);
			ps.setString(1, id);
			ps.setString(2, id);
			ResultSet rs = ps.executeQuery();
			String gson = null;
			ArrayList<Friend> ap = new ResultToList<Friend>(Friend.class).progess(rs);
			gson = new Gson().toJson(ap);
			rs.close();
			ps.close();
			con.close();
			return gson;
		} catch (Exception e) {
			System.err.println(e);
			return "false";
		}
	}

	/**
	 * xoa 1 thanh viên khoi nhom
	 * 
	 * @param id
	 * @param group
	 * @return
	 */
	public static String deleteMember(String id, String group) {
		String sql = "delete from tvNhom where idNhom = ? and idTV = ?";

		try {
			Connection con = Info.getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, group);
			ps.setString(2, id);
			ps.executeUpdate();
			ps.close();
			con.close();
			return "true";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "false";
	}

	public static String deleteFriend(String id1, String id2) {
		String sql = "delete from banBe where (id1 = ? and id2 = ?) or (id1 = ? and id2 = ?)";

		try {
			Connection con = Info.getCon();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id1);
			ps.setString(2, id2);
			ps.setString(3, id2);
			ps.setString(4, id1);
			ps.executeUpdate();
			ps.close();
			con.close();
			return "true";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "false";
	}
}
