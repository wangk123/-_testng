package util;

import java.util.Map;

public class SpliceParam {
	
	
	/**
	 * 拼接参数
	 * @param key
	 * @param value
	 * @return
	 */
	public static String sParam(String key,String value) {

		String result  = key + "=" + value + "&";
		
		return result;
	}
	
	/**
	 * 拼接参数
	 * @param param Map对象
	 * @return
	 */
	public static String sParam(Map<String, Object> param){
		
		String result = "";
		
		for(Object key : param.keySet()){
			Object value = param.get(key);
			result += key + "=" + value + "&";
		}
		
		return result;
	}

}
