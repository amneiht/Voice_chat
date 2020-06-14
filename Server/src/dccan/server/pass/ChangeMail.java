package dccan.server.pass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.RandomStringUtils;

import dccan.mail.SendEmail;
import dccan.server.control.user.Member;
import dccan.server.control.user.PassToken;
import dccan.server.sql.User;
import dccan.server.sql.config.Info;

public class ChangeMail {

	public static boolean confrim(String id) {
		Member user = PassToken.getUserByToken(id);
		if (user == null)
			return false;
		return User.changMail(user.getId(), user.getMail());
		
	}
	public static boolean addMailToken(String user,String Nmail)
	{
		if (!PassToken.check(user))
			return false;
		String token = RandomStringUtils.randomNumeric(6);
		PassToken.addNew(token, user , Nmail);
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
			SendEmail.sendEmailToken(mail, token);
		return true ;
	}

}
