package dccan.server.sql;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dccan.suport.Comment;

public class ResultToList<E> {
	Class<E> ob;

	public ResultToList(Class<E> obs) {
		ob = obs;
	}

	@SuppressWarnings("unchecked")
	public List<E> getListFromResult(ResultSet rs, String name) {
		ArrayList<E> res = new ArrayList<E>();
		String lop = ob.getName();
		try {
			rs.beforeFirst();
			while (rs.next()) {
				Object ed = rs.getObject(name);
				if (ed.getClass().getName().equals(lop))
					res.add((E) ed);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public ArrayList<E> progess(ResultSet rs) {
		try {
			ArrayList<E> res = new ArrayList<E>();
			Field[] fl = ob.getDeclaredFields();
			rs.beforeFirst();
			while (rs.next()) {
				E in = ob.newInstance();
				for (Field obf : fl) {
					obf.setAccessible(true);
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
