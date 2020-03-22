package dccan.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.gson.Gson;

import dccan.sql.config.Info;
import dccan.suport.Friend;

public class Group {
	private static final String addSql1 = "insert into nhom(idNhom,nguoiTao,tenNhom) values(?,?,?) ";
	private static final String addSql2 = "insert into banBe(id1,id2) values(?,?) ";
/**
 * them nhom chat
 * @param id id nguo tao
 * @param member cac thanh vien
 * @param ten nhom
 */
	public static void addGroup(String id, ArrayList<String> member,String ten) {
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
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static final String addF = "insert into banBe(id1,id2) values(?,?)";
	/**
	 * them 1 nguoi ban
	 * @param id1
	 * @param id2
	 */
	public static void addFriend(String id1,String id2)
	{
		try {
			Connection con = Info.getCon();
			PreparedStatement ps = con.prepareStatement(addF);
			ps.setString(1,id1);
			ps.setString(2, id2);
			ps.executeUpdate();
			ps.close();
			con.close();
			
		}catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
	}
	private static final String getFlist="select ten,nguoiDung from banBe,thongtin where ( banBe.id1 = thongtin.ten or banBe.id2 = thongtin.ten)  and ( banBe.id1=? or banBe.id2=?)";
	/**
	 * lay danh sanh ban be
	 * @param id nguoi lay
	 * @return
	 */
	public static String getFrendList(String id)
	{
		
		try {
			Connection con = Info.getCon();
			PreparedStatement ps = con.prepareStatement(getFlist);
			ps.setString(1, id);
			ps.setString(2, id);
			ResultSet rs = ps.executeQuery();
			String gson = null ;
			ArrayList<Friend> ap = new ResultToList<Friend>(Friend.class).progess(rs);
			gson = new Gson().toJson(ap);
			rs.close();
			ps.close();
			con.close();
			return gson ;
		}catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
			return null ;
		}
	}
}

