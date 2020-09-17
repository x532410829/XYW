package com.Panacea.demo;
/**
 * 支付宝相关Demo
 * @author 夜未
 * @since 2020年9月17日
 */

import org.junit.jupiter.api.Test;

import com.Panacea.unity.util.AlipayUtil;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
/**
 * 支付宝SDK相关工具，
 * @author 夜未
 * @since 2020年9月17日
 */
public class AlipayDemo {
	
	/**
	 * 支付宝的工具类和配置信息
	 */
	private AlipayUtil alipayUtil;

	/**
	 * code获取accessToken
	 * @param code
	 */
	@Test
	public void getAccessToken(String code) {
		AlipaySystemOauthTokenResponse res=	alipayUtil.getAccessToken(code);
		System.out.println(res);
		String token=res.getAccessToken();
		System.out.println(token);
	}
	
	/**
	 * 前端APP获取请求得到code的地址
	 */
	@Test
	public void geuCodeUrl() {
		try {
			String url= alipayUtil.getUrl();
			System.out.println(url);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("生成失败");
		}
	}
	
	/**
	 * 获取支付宝用户的信息
	 * @param token
	 */
	@Test
	public void getUserInfo(String token) {
		AlipayUserInfoShareResponse res= alipayUtil.getUserInfo(token);
		System.out.println(res);
	}
	
	
	
	
	
}
