package dccan.server.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dccan.server.sql.config.Info;

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
		try {
			Connection con = Info.getCon();
			String sql = "select nguoiDung from thongtin where ten = ? and matKhau = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, Info.getMD5(pass));
			ResultSet rs = ps.executeQuery();
			rs.beforeFirst();// dich con tro
			String id = null;
			if (rs.next()) {
				id = rs.getString(1);
			}
			ps.close();
			//con.close();
			return id;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
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
		try {
			Connection con = Info.getCon();
			String sql = "select matKhau from thongtin where ten = ? ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();
			rs.beforeFirst();// dich con tro
			if (rs.next()) {
				System.out.println("user :"+user+" da co tk");
				ps.close();
				//con.close();
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
			//con.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
}
