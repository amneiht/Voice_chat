package dccan.suport;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GetList {
	static final Type group = new TypeToken<List<Group>>() {
	}.getType();
	static final Type cmt = new TypeToken<List<Comment>>() {
	}.getType();
	static final Type User = new TypeToken<List<Friend>>() {
	}.getType();
	static final Gson gson = new Gson();
	static final Type listString = new TypeToken<List<String>>() {
	}.getType();

	public static List<Group> groups(String s) {
		return gson.fromJson(s, group);
	}
	public static List<String> listString(String s) {
		return gson.fromJson(s, listString);
	}
	public static List<Comment> cmts(String s) {
		return gson.fromJson(s, cmt);
	}

	public static List<Friend> friend(String s) {
		return gson.fromJson(s, User);
	}

	public static List<Friend> member(String s) {
		return gson.fromJson(s, User);
	}
}
