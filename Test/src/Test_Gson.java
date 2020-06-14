import java.net.InetAddress;
import java.net.UnknownHostException;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Test_Gson {
	public static void main(String[] args) throws UnknownHostException {
		InetAddress inet = InetAddress.getByName("google.com");
		Gson gp = new Gson();
		String lp = new Gson().toJson(inet);
		Type ts = new TypeToken<InetAddress>() {
		}.getType();
		InetAddress is = gp.fromJson(lp, ts);
		System.out.println(is.getHostName());
		System.out.println(inet.getHostName());
	}
}
