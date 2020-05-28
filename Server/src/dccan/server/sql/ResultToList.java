package dccan.server.sql;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
			Field[] fl1 = ob.getDeclaredFields();
			rs.beforeFirst();
			ResultSetMetaData rsm = rs.getMetaData();
			ArrayList<String> lp = new ArrayList<String>();
			int col = rsm.getColumnCount();
			for (int i = 1; i <= col; i++) {
				lp.add(rsm.getColumnName(i));
			}
			List<Field> fl = new LinkedList<Field>();
			for (Field obf : fl1) {
				obf.setAccessible(true);
				int h = lp.indexOf(obf.getName());
				if (h > -1)
					fl.add(obf);
			}

			while (rs.next()) {
				E in = ob.newInstance();
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

}
