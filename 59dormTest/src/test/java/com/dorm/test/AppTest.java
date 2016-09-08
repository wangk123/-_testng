package com.dorm.test;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import bean.Http;
import bean.User;
import util.Assertion;
import util.HttpRequest;
import util.JsonShemaValidator;
import util.SpliceParam;


@Listeners({util.AssertionListener.class})
public class AppTest
{
  
	
	public void protocolURL(){
		
		Http.setRequestPath("/creditcard/agreement/url");
		
		String response = HttpRequest.get(Http.getDormQa(), Http.getRequestPath(), SpliceParam.sParam("token", User.getToken()));
		
		JsonShemaValidator.validatorSchema("ProtocolURL.json", response);
		
	}
	
	@SuppressWarnings("deprecation")
	@BeforeSuite
	public void login(){
		
		
		
		User.setUsername("dorm7008");
		User.setPassword("e10adc3949ba59abbe56e057f20f883e");
		
		Http.setRequestPath("/user/login");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("token", User.getDeviceToken());
		param.put("username", User.getUsername());
		param.put("password", User.getPassword());
		
		Http.setParam(SpliceParam.sParam(param));
		
		String response = HttpRequest.post("http://dormappapi.59shangcheng.com", Http.getRequestPath(), Http.getParam());
				
		String uname = "";
		
		JSONObject object = new JSONObject(response);
		
		JSONObject data = (JSONObject) object.get("data");
		
		Assertion.verifyEquals(uname, data.get("uname"), "uname校验");
		
		// 将普通字符串转换成application/x-www-from-urlencoded字符串
		User.setToken(URLEncoder.encode(object.getString("token")));
		
		System.out.println(URLEncoder.encode(object.getString("token")));
		
	}

}
