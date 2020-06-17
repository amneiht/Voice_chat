package dccan.suport;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GetList {
	static final Gson gson = new Gson();

	static final Type group = new TypeToken<List<Group>>() {
	}.getType();
	static final Type cmt = new TypeToken<List<Comment>>() {
	}.getType();
	static final Type User = new TypeToken<List<Friend>>() {
	}.getType();
	static final Type listString = new TypeToken<List<String>>() {
	}.getType();
	static final Type friend = new TypeToken<Friend>() {
	}.getType();
	static final Type chat = new TypeToken<List<Member>>() {
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

	public static List<Friend> friendList(String s) {
		return gson.fromJson(s, User);
	}

	public static List<Friend> member(String s) {
		return gson.fromJson(s, User);
	}

	public static Friend toFriend(String s) {
		return gson.fromJson(s, friend);
	}

	public static List<Member> chatMember(String s) {
		return gson.fromJson(s, chat);
	}
}
