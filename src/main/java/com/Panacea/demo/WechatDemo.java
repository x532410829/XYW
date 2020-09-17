package com.Panacea.demo;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.Panacea.unity.bean.vo.WeixinVo;
import com.Panacea.unity.util.WeChatUtil;

/**
 * 微信第三方SDK相关
 * @author 夜未
 * @since 2020年9月16日
 */
public class WechatDemo {

	
	/**
	 * 微信的工具类，包含了配置
	 */
	private WeChatUtil weChatUtil;
	
	/**
	 * code获取accessToken和openId等参数，微信API基本都要用到accessToken
	 * @param code 前端调用APP，获取到的code
	 */
	@Test
	public void getToken(String code) {
		WeixinVo weixinVo=new WeixinVo();
		weixinVo.setCode(code);
		Map<String, String>map= weChatUtil.getAccessTokenByCode(weixinVo);
		System.out.println(map);
	}
	
	/**
	 * 获取到accessToken和openId后，可以再用这2参数获取用户的信息，可以用于做微信登录注册等
	 * @param token
	 * @param openId
	 */
	@Test
	public void getUserInfo(String token,String openId) {
		WeixinVo weixinVo=new WeixinVo();
		weixinVo.setAccessToken(token);
		weixinVo.setOpenid(openId);
		Map<String, String>map= weChatUtil.getUserInfo(weixinVo);
		System.out.println(map);
	}
	
	/**
	 * 刷新accessToken的有效期，accessToken的有效期一般30分钟，可以在每次调用接口前去获取token,
	 * 就不用去刷新token了
	 * @param refreshToken
	 */
	@Test
	public void refreshAccessToken(String refreshToken) {
		WeixinVo weixinVo=new WeixinVo();
		weixinVo.setRefreshToken(refreshToken);
		Map<String, String>map= weChatUtil.refreshAccessToken(weixinVo);
		System.out.println(map);
	}
	

	
	
}
