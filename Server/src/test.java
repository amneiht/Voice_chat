import java.lang.reflect.Field;
import java.util.ArrayList;

import dccan.suport.Comment;

public class test {
	public static void main(String[] args) {
		Class<?> ob = Comment.class;
		try {
			Object is = ob.newInstance();
			ArrayList<Object> res = new ArrayList<Object>();
			Field[] fl = ob.getDeclaredFields();
			for (Field obf : fl) {
				obf.set(is, "tts");
			}
			res.add(is);
			Comment pk = (Comment) is;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
