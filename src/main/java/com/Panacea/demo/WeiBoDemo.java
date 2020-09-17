package com.Panacea.demo;
/**
 * 微博SDK
 * @author 夜未
 * @since 2020年9月17日
 */

import org.junit.jupiter.api.Test;

import com.Panacea.unity.util.WeiBoUtil;
import com.alibaba.fastjson.JSONObject;

public class WeiBoDemo {
	
	
	/**
	 * 微博工具配置类
	 */
	private WeiBoUtil weiBoUtil;
	
	/**
	 * code获取accessToken
	 * @param code
	 */
	@Test
	public void getToken(String code) {
		JSONObject json=weiBoUtil.getAccessToken(code);
		System.out.println(json);
	}
	
	/**
	 * 获取用户信息
	 * @param token
	 * @param uid
	 */
	@Test
	public void getUserInfo(String token,String uid) {
		JSONObject json=weiBoUtil.getUserInfo(token, uid);
		System.out.println(json);
	}
	
	/**
	 * 获取用户的UID
	 * @param token
	 */
	@Test
	public void getUid(String token) {
		JSONObject json=weiBoUtil.getOpenId(token);
		System.out.println(json);
	}
	
	
	
	
	
	
	
}
