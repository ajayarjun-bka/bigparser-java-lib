package base;
import base.BigParser;
public class Auth {
	public static String login(String emailId, String password)
	{
		String authId = BigParser.login(emailId, password);
		return authId;
	}
}
