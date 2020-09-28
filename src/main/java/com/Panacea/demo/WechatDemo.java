package com.Panacea.demo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
//--------------微信支付相关-----------------
	
	/**
	 * 统一下单接口
	 * @param 
	 */
	public void WeChatPay() {
		weChatUtil.addOrder("具体的订单和支付信息");
	}
	
	/**
	 * 微信退款
	 * @throws Exception 
	 */
	public void refundWxpay() throws Exception {
		weChatUtil.refundWxpay("订单号", 10, 1, "其他订单信息");
	}
	
	/**
	 * 接收微信的回调通知，支付、退款这些
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/wxPayResult")
	@ResponseBody
	public void wxPayResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
		weChatUtil.wxPayResult(request,  response);
	}
	
	
	
	
	
	
	
	
}
