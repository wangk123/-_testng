package util;

public class SpliceURI {
	
	
	/**
	 * 拼接URL
	 * @param url
	 * @param path
	 * @param parameter
	 */
	public static String sUri(String url,String path,String parameter){
		
		String uri = url + path + "?" + parameter;
		
		return uri;
		
	}

}
