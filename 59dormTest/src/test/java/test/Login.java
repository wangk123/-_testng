package test;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import bean.User;
import util.Const;
import util.HttpRequest;
import util.JsonShemaValidator;
import util.RedisClient;

public class Login {

//	/**
//	 * 正常登录--账号密码登录
//	 */
//	@Test (groups = {"loginSuccess"}, priority = 2)
//	void login(){
//		User.setUsername("dorm7008");
//		User.setPassword("e10adc3949ba59abbe56e057f20f883e");
//		Http.setRequestPath("/user/login");
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("username", User.getUsername());
//		param.put("password", User.getPassword());
//		param.put("token", User.getDeviceToken());
//		Http.setParam(SpliceParam.sParam(param));
//		String response = HttpRequest.post(Http.getDormQa(), Http.getRequestPath(), Http.getParam());
//		JsonShemaValidator.validatorSchema("LoginSuccess.json", response);
//		JSONObject json = new JSONObject(response);
//		JSONObject data = json.getJSONObject("data");
//		Assertion.verifyEquals(data.get("uid"), 7008, "uid verify");
//		Assertion.verifyEquals(data.get("uname"), "dorm7008", "uname verify");
//		Assertion.verifyEquals(data.get("phone"), "11111111111", "phone verify");
//		User.setToken(URLEncoder.encode(json.getString("token")));
//	}
//	
	/**
	 * 获取验证码
	 */
	@Test (groups = {"loginSuccess"}, priority = 0, description = "获取验证码")
	void getCode(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("token", User.getDeviceToken());
		params.put("phone", "11111111111");
		Map<String, Object> result = HttpRequest.get(Const.DORM_QA + "/user/phone/getcode", params);
		JSONObject jsonResponse = new JSONObject(result.get("response").toString());
		JsonShemaValidator.validatorSchema("SendVerifyCode.json", jsonResponse);
		Assert.assertEquals(jsonResponse.get("msg"), "验证码已发至11111111111,请及时确认", "message verify");
	}
	
	/**
	 * 正常登录--手机号验证码登录
	 */
	@Test (groups = {"loginSuccess"}, priority = 1, description = "正常登录--手机号验证码登录")
	void phoneLogin(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", "11111111111");
		params.put("code", new RedisClient().jedis.get("dorm_login_by_pohone_number_11111111111"));
		params.put("token", User.getDeviceToken());
		Map<String, Object> result = HttpRequest.post(Const.DORM_QA + "/user/phone/login", params);
		JSONObject jsonResponse = new JSONObject(result.get("response").toString());
		Assert.assertTrue(JsonShemaValidator.validatorSchema("LoginSuccess.json", jsonResponse));
		JSONObject data = jsonResponse.getJSONObject("data");
		Assert.assertEquals(data.get("uid"), 7008);
		Assert.assertEquals(data.get("uname"), "dorm7008");
		Assert.assertEquals(data.get("phone"), "11111111111");
		User.setToken(URLEncoder.encode(jsonResponse.getString("token")));
	}
	
	/**
	 * 异常登录---密码错误
	 */
	@Test (groups = {"loginError"}, priority = 1, description = "异常登录---密码错误")
	void accountLoginError(){
		User.setUsername("dorm7008");
		User.setPassword("e10adc3949ba59abbe56e057f20f8831");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", User.getUsername());
		params.put("password", User.getPassword());
		params.put("token", User.getDeviceToken());
		Map<String, Object> result = HttpRequest.post(Const.DORM_QA + "/user/login", params);
		JSONObject jsonResponse = new JSONObject(result.get("response").toString());
		Assert.assertTrue(JsonShemaValidator.validatorSchema("LoginFailed.json", jsonResponse));
		Assert.assertEquals(jsonResponse.get("msg"), "用户名或密码错误");
	}
	
	/**
	 * 异常登录---验证码错误
	 */
	@Test (groups = {"loginError"}, priority = 0, description = "异常登录---验证码错误")
	void verifyCodeError(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", "11111111111");
		params.put("token", User.getDeviceToken());
		//发送验证码
//		HttpRequest.get(Const.DORM_QA + "/user/phone/getcode", params);
		params.put("code", "123456");
		//手机号验证码登录
		Map<String, Object> result = HttpRequest.post(Const.DORM_QA + "/user/phone/login", params);
		JSONObject jsonResponse = new JSONObject(result.get("response").toString());
		Assert.assertTrue(JsonShemaValidator.validatorSchema("VerifyCodeError.json", jsonResponse));
		Assert.assertEquals(jsonResponse.get("msg"), "验证码有误");
	}
}
