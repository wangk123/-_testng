package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpRequest {
	
	private static Logger log = Logger.getLogger(HttpRequest.class);
	
	/**
	 * post请求
	 * @param urlPath 请求路径
	 * @param params 参数
	 * @return Map<String, Object>  response、allHeaders[]、protocolVersion、statusCode、reasonPhrase
	 */
	public static Map<String, Object> post(String url, Map<String, Object> params){
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPost httpPost = postForm(url, params);
		
		Map<String, Object> response = invoke(httpClient, httpPost);
		
		httpClient.getConnectionManager().shutdown();
		
		return response;
	}
	
	/**
	 * get请求
	 * @param urlPath 请求路径
	 * @param params 参数
	 * @return Map<String, Object>  response、allHeaders[]、protocolVersion、statusCode、reasonPhrase
	 */
	public static Map<String, Object> get(String url, Map<String, Object> params){
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(uriForm(url, params));
		
		Map<String, Object> response = invoke(httpClient, httpGet);
		
		httpClient.getConnectionManager().shutdown();
		
		return response;
	}
	
	/**
	 * put请求
	 * @param urlPath 请求路径
	 * @param params 参数
	 * @return Map<String, Object>  response、allHeaders[]、protocolVersion、statusCode、reasonPhrase
	 */
	public static Map<String, Object> put(String url, Map<String, Object> params){
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPut httpPut = new HttpPut(uriForm(url, params));
		
		Map<String, Object> response = invoke(httpClient, httpPut);
		
		httpClient.getConnectionManager().shutdown();
		
		return response;
	}
	
	private static Map<String, Object> invoke(CloseableHttpClient httpClient, HttpUriRequest request){
		
		Map<String, Object> result = null;
		
		try {
			HttpResponse httpResponse = httpClient.execute(request);
			HttpEntity entity = httpResponse.getEntity();
			result = getAll(httpResponse);
			String response = EntityUtils.toString(entity, HTTP.UTF_8);
			result.put("response", response);
			log.info("Response: " + result.get("response"));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
		return result;
	}
	
	private static Map<String, Object> getAll(HttpResponse hr) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("allHeaders", hr.getAllHeaders());
		params.put("protocolVersion", hr.getProtocolVersion());
		params.put("statusCode", hr.getStatusLine().getStatusCode());
		params.put("reasonPhrase", hr.getStatusLine().getReasonPhrase());
		log.info("ResponseStatusInfo: " + hr.getStatusLine().toString());
		return params;
	}
	
	private static HttpPost postForm(String url, Map<String, Object> params) {
		HttpPost httpPost = new HttpPost(url);
		Map<String, Object> map = SignGenerated.signMap(params);
		httpPost.setEntity(entityForm(map));
		log.info("Request: " + url + "?" + paramsForm(map));
		return httpPost;
	}
	
	private static HttpEntity entityForm(Map<String, Object> params){
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		HttpEntity entity = null;
		for(String key : params.keySet()){
			nvps.add(new BasicNameValuePair(key, (String) params.get(key)));
		}
		try {
			entity = new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return entity;
	}
	
	private static String uriForm(String url, Map<String, Object> params){
		String uri = null;
		try {
			uri = url + "?" + paramsForm(SignGenerated.signMap(params));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} 
		log.info("Request: " + uri);
		return uri;
	}
	
	private static String paramsForm(Map<String, Object> params) {
		String str = "";
		try {
			str = EntityUtils.toString(entityForm(params));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return str;
	}
	
}
