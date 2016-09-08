package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpRequest {
	
	private static Logger log = Logger.getLogger(HttpRequest.class);
		
	/**
	 * get请求
	 * @param url 域名
	 * @param requestPath 请求路径 
	 * @param parameter 请求参数  key=value&key1=value1
	 * @return
	 */
	public static String get(String url,String requestPath,String parameter){
		
		String result = "";
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		String uri = SpliceURI.sUri(url, requestPath, parameter);
		
		try {
			
			StringBuilder sb = new StringBuilder(uri);
			
			HttpGet httpget = new HttpGet(sb.toString());
			
			HttpResponse response = httpClient.execute(httpget);
			
			HttpEntity entity = response.getEntity();
			
			result = EntityUtils.toString(entity,"utf-8");
			
			log.info("接口：" + requestPath + "\n" + "Request: " + sb.toString() + "\n" + "Response: " + result);
			
		} catch (ClientProtocolException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		} finally {
			
			try {
				
				httpClient.close();
				
			} catch (IOException e) {
				
				log.error(e.getMessage(),e);
			}
			
		}
		
		return result;
		
	}
	
	/**
	 * post请求
	 * @param url 域名
	 * @param requestPath 请求路径
	 * @param parameter 请求参数
	 * @return
	 */
	public static String post(String url,String requestPath,String parameter){
		String result = "";
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		String uri = SpliceURI.sUri(url, requestPath, parameter);
		
		try {
			
			StringBuilder sb = new StringBuilder(uri);
			
			HttpPost httpPost = new HttpPost(sb.toString());
		
			HttpResponse response = httpClient.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
			
			result = EntityUtils.toString(entity,"utf-8");
			
			log.info("接口：" + requestPath + "\n" + "Request: " + sb.toString() + "\n" + "Response: " + result);
			
		} catch (ClientProtocolException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				log.error(e.getMessage(),e);
			}
		}
		
		return result;
	}
	
	/**
	 * 向指定 URL 发送GET方法的请求
	 * @param requestPath  请求路径
	 * @param parameter  请求参数
	 * @return Response Result
	 */
	public static String sendGet(String URL,String requestPath,String parameter){
		
		
		
		
		String result = "";
		BufferedReader in = null;
		
		try {
			
			//拼接URL字串
			String getURL = URL + requestPath + "?" + parameter;
			
			URL realUrl = new URL(getURL);
			
			URLConnection connection = realUrl.openConnection();
			
			connection.connect();
			
//			Map<String, List<String>> map = connection.getHeaderFields();
			
//			for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
			
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String line;
			
			while ((line = in.readLine()) != null){
				result += line;
			}
			
		} catch (Exception e) {
			System.out.println("发送Get请求出现异常" + e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(in != null){
					in.close();
				}
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		
		return result;
		
	}
	
}
