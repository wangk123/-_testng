package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SignGenerated {
	
//	public static void main(String[] args) {
//		Map<String, String> params = new HashMap<String, String>();
//		Map<String, String> params1 = new HashMap<String, String>();
//		params1 = signMap(params);
//		for(String key : params1.keySet()){
//			System.out.println(key + ":" + params1.get(key));
//		}
//		
//	}
	
	public static Map<String, Object> signMap(Map<String, Object> params) {
        params.put("time", String.valueOf((int) ((new Date()).getTime() / 1000)));
        params.put("app_version", "2.4.0");
        params.put("device_type", "1");
        params.put("protocol_version", "1.1.0");
        int len = params.size();

        Set<String> key = params.keySet();
        String[] paramNames = new String[len];
        Iterator<String> iterator = key.iterator();
        int j = 0;
        while (iterator.hasNext()) {
            paramNames[j] = iterator.next();
            j++;
        }
        Arrays.sort(paramNames);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append(paramNames[i] + "=" + params.get(paramNames[i]) + "&");
        }
        sb.append(Const.S_NET_PARAMS_SIGN_KEY);
//        sb.append(Const.S_NET_PARAMS_SIGN_KEY);
        String md5 = md5s(sb.toString());
        params.put("sign", md5);
//        Gson gson = new Gson();
//        DLog.e(TAG,gson.toJson(params));
//        System.out.println("sign:" + params.get("sign"));
        return params;
    }

    private static String md5s(String plainText) {
        String str = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes("utf-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            str = "";
        } catch (UnsupportedEncodingException e) {
            str = "";
        }

        return str;
    }
}
