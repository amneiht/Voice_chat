package app.lang;

import java.util.ResourceBundle;

public class Resouce {
	static String lang = "vn";
	static ResourceBundle rb = ResourceBundle.getBundle("app.lang.vn");
	public static void setLang(String lg)
	{
		lang = lg ;
		
	}
}
