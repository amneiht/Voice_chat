package dccan.server.pass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import dccan.mail.SendEmail;
import dccan.server.control.user.ListUser2;
import dccan.server.control.user.Member;
import dccan.server.control.user.PassToken;
import dccan.server.sql.config.Info;

public class ChangePass {
	public static void main(String[] args) {
		sendToken("can");
	}

	public static void sendToken(String user) {
		if (!PassToken.check(user))
			return;
		String token = RandomStringUtils.randomNumeric(6);
		PassToken.addNew(token, user);
		Connection con = Info.getCon();
		String sql = "select email from thongtin where ten = ?";
		String mail = null;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user);
			ResultSet rs = ps.executeQuery();
			rs.first();
			mail = rs.getNString("email");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Info.give(con);
		if (mail != null)
			SendEmail.sendPassWordToken(mail, token);
	}

	public static boolean changePass(String token, String pass) {
		String user = PassToken.getUserByToken(token).getId();
		if (user == null)
			return false;
		Iterator<Map.Entry<String, Member>> mp = ListUser2.op.entrySet().iterator();
		for (; mp.hasNext();) {
			Map.Entry<String, Member> x = mp.next();
			if (x.getValue().getId().equals(user)) {
				mp.remove();
			}
		}
		String sql = "update thongtin set matKhau = ? where ten = ?";
		Connection con = Info.getCon();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, Info.getMD5(pass));
			ps.setString(2, user);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Info.give(con);
		return true;

	}

}
