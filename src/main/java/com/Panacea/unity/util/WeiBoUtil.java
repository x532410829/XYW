package com.Panacea.unity.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.JFinal;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;

import cn.hutool.http.HttpUtil;

public class WeiBoUtil {
	
	private static String client_id="换成自己的key";
	private static String client_secret="换成自己的密钥";
	private static String redirect_uri="https://api.weibo.com/oauth2/default.html";
	private static String url="https://api.weibo.com/oauth2/access_token";
	

	/**(这个我还没测试过，其他工具方法是可以的，这个按理说也是没问题的)
	 * code请求得到accessToken
	 * @param code
	 * @return
	 */
	public JSONObject getAccessToken(String code) {
		Map<String, String> paras = new HashMap<String, String>();
		paras.put("client_id", client_id);//AppKey
		paras.put("client_secret", client_secret);//AppSecret
		paras.put("grant_type", "authorization_code");
		paras.put("code",code);
		paras.put("redirect_uri", redirect_uri);
		String responseString = HttpKit.get(url, paras);
		return JSON.parseObject(responseString);

//       返回值		
//       "access_token": "ACCESS_TOKEN",
//       "expires_in": 1234,
//       "remind_in":"798114",
//       "uid":"12341234" 
	}
	
	
	/**返回值具体的查看返回的内容
	 * access_token获取uid
	 * @param token
	 */
	public JSONObject getOpenId(String token) {
		String url = "https://api.weibo.com/oauth2/get_token_info";
		
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("access_token", token);//
		String responseString= HttpUtil.post(url, paras);   
		return JSON.parseObject(responseString);
		
	}
	
	/**
	 * 获取个人用户信息，返回值看具体内容
	 * @param token
	 * @param uid
	 */
	public  JSONObject getUserInfo(String token, String uid) {
	//	String url = "https://api.weibo.com/2/eps/user/info.json";
		String url = "https://api.weibo.com/2/users/show.json";
		Map<String, String> paras = new HashMap<String, String>();
		paras.put("access_token", token);
		paras.put("uid", uid);
		String responseString = HttpKit.get(url, paras);
		if (JFinal.me().getConstants().getDevMode()) {
			System.out.println(responseString);
		}
		if (StrKit.isBlank(responseString)) {
			return null;
		}
		return JSON.parseObject(responseString);

	}
}
