package bean;

public class Http {

	/**
	 * 域名
	 */
	private static final String DORM_QA = "http://dormappapi.59shangcheng.com";
	
	/**
	 * 接口路径
	 */
	private static String requestPath;
	
	/**
	 * 参数
	 */
	private static String param;

	public static String getRequestPath() {
		return requestPath;
	}

	public static void setRequestPath(String requestPath) {
		Http.requestPath = requestPath;
	}

	public static String getParam() {
		return param;
	}

	public static void setParam(String param) {
		Http.param = param;
	}

	public static String getDormQa() {
		return DORM_QA;
	}

	
	
}
