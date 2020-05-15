package dccan.server.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import dccan.server.sql.config.Info;
import dccan.suport.Friend;

public class User {
	/**
	 * lay id cua nguoi dung
	 * 
	 * @param user
	 *            ten dang nhap
	 * @param pass
	 *            mat khau
	 * @return ten hien thi cua nguoi dung , null neu khong co
	 */
	public static String login(String user, String pass) {
		Connection con = Info.getCon();
		String id = null;
		try {

			String sql = "select nguoiDung from thongtin where ten = ? and matKhau = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, Info.getMD5(pass));
			ResultSet rs = ps.executeQuery();
			rs.beforeFirst();// dich con tro
			if (rs.next()) {
				id = rs.getString(1);
			}
			ps.close();
			// con.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
		Info.give(con);
		return id;
	}

	/**
	 * tao moi 1 tai khoan
	 * 
	 * @param user
	 *            ten dang nhap
	 * @param pass
	 *            matkhau
	 * @param email
	 *            dia chi email
	 * @param hoten
	 *            ho ten
	 * @return true neu thanh cong , false con lai
	 */
	public static boolean register(String user, String pass, String email, String hoten) {
		Connection con = Info.getCon();
		try {

			String sql = "select matKhau from thongtin where ten = ? ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();
			rs.beforeFirst();// dich con tro
			if (rs.next()) {
				System.out.println("user :" + user + " da co tk");
				ps.close();
				// con.close();
				Info.give(con);
				return false; // da co tai khoan
			}
			ps.close();
			sql = "insert into thongtin(ten , matKhau,email,nguoiDung) values(?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, Info.getMD5(pass));
			ps.setString(3, email);
			ps.setString(4, hoten);
			ps.executeUpdate();
			ps.close();
			// con.close();
			Info.give(con);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Info.give(con);
			return false;
		}
	}

	public static boolean changName(String user, String newName) {
		boolean res = false;
		Connection con = Info.getCon();
		String sql = "update thongtin set nguoiDung = ? where ten = ? ";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, newName);
			ps.setString(2, user);
			ps.executeUpdate();
			ps.close();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Info.give(con);
		return res;
	}

	public static boolean setImg(String user, String id) {
		boolean res = false;
		Connection con = Info.getCon();
		String sql = "update thongtin set idAnh = ? where ten = ? ";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, user);
			ps.executeUpdate();
			ps.close();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Info.give(con);
		return res;
	}

	public static String getInfo(String user) {
		Connection con = Info.getCon();
		String sql = "select nguoiDung , ten , idAnh ,email from thongtin where ten = ?";
		String res = null;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();
			List<Friend> lp = new ResultToList<Friend>(Friend.class).progess(rs);
			res = new Gson().toJson(lp);
			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		Info.give(con);
		return res;
	}

	public static boolean changMail(String user, String mail) {
		boolean res = false;
		Connection con = Info.getCon();
		String sql = "update thongtin set email = ? where ten = ? ";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, mail);
			ps.setString(2, user);
			ps.executeUpdate();
			ps.close();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Info.give(con);
		return res;
	}

	public static String getLimitName(String name) {
		String res = null;
		Connection con = Info.getCon();
		String sql = "select  ten from thongtin where ten like ? limit 10";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			name = name + "%";
			ps.setString(1, name);
			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();
			List<String> lp = new ResultToList<String>(String.class).getListFromResult(rs, "ten");
			res = new Gson().toJson(lp);
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Info.give(con);
		return res;
	}
}
