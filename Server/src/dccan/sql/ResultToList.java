package dccan.sql;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;

import dccan.suport.Comment;

public class ResultToList<E> {
	Class<E> ob;

	public ResultToList(Class<E> obs) {
		ob =obs;
		
	}

	public ArrayList<E> progess(ResultSet rs) {
		try {
			ArrayList<E> res = new ArrayList<E>();
			Field[] fl = ob.getDeclaredFields();
			rs.beforeFirst();
			while (rs.next()) {
				E in =  ob.newInstance();
				for (Field obf : fl) {
					obf.set(in, rs.getObject(obf.getName()));
				}
				res.add(in);
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<E> stringProgess(ResultSet rs, Class<E> ob) {
		// co the co loi
		ArrayList<E> res = new ArrayList<E>();
		Field[] fl = Comment.class.getDeclaredFields();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				E in = ob.newInstance();
				for (Field obf : fl) {
					obf.set(in, rs.getString(obf.getName()));
				}
				res.add(in);
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return res;
		}
	}
}
