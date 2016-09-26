package bean;

public class User {
	
	/**
	 * 用户名
	 */
	private static String username;
	
	/**
	 * 密码
	 */
	private static String password;
	
	/**
	 * token
	 */
	private static String token;
	
	/**
	 * 设备token
	 */
	private static final String DEVICE_TOKEN = "IT7mAo4h5RrgRHTkVoJ4xSmXp8wEDWzI//SYSO9h0K0=";

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		User.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		User.password = password;
	}

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		User.token = token;
	}

	public static String getDeviceToken() {
		return DEVICE_TOKEN;
	}
	
}
