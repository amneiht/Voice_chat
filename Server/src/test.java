import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import dccan.server.rmi.RemoteObj;
import dccan.suport.Comment;
import dccan.suport.GetList;
import dccan.suport.Group;

public class test {
	public static void main(String[] args) {
		try {
			RemoteObj rmi = new RemoteObj();
			
				boolean res = rmi.login("dccan", "1");
				System.out.println(res);
				String d=rmi.getGroup();
				List<Group> dp = GetList.groups(d);
				Group hs = dp.get(0);
				String cmt = rmi.getCommentList(hs.getIdNhom(), new Timestamp(System.currentTimeMillis()).toString(), false);
				List<Comment> cs = GetList.cmts(cmt);
				for(Comment cd : cs)
				{
					System.out.println(cd.getNoiDung());
					System.out.println(cd.getIdGui());
					System.out.println(cd.getNgayGui());
				}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
