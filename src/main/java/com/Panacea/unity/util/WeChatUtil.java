package com.Panacea.unity.util;

import java.util.HashMap;
import java.util.Map;

import com.Panacea.unity.bean.vo.WeixinVo;
import com.jfinal.kit.HttpKit;

/**
 * 微信相关(第三方登录、)
 * @author 夜未
 * @since 2020年9月16日
 */
public class WeChatUtil {
	
	   /**
	    * appid和secret要去自己的微信开发者账号里面申请得到后查看
	    */
	   private static  String appid = "换成自己的appid";
	   private static  String secret = "换成自己的密钥";
	   /**
	    * 认证类型固定写这个
	    */
	   private static  String grant_type = "authorization_code";
	   
	   
	   
	   	 /**第三方登录时，前端拿到code来获取token\openid等
		   * 用户授权登录用code获取access_token、openid等
		   * @param code
		   * @return 
		   */
		  public Map<String, String> getAccessTokenByCode(WeixinVo weixinVo) {

		    String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret="
		        + secret + "&code=" + weixinVo.getCode() + "&grant_type=" +grant_type;
		    // 用code调用微信接口换取用户openid和session_key
		    String wechatsession = HttpKit.get(url);
		    Map<String, String> map = new HashMap<String, String>();
		    System.out.println("微信授权返回参数：" + wechatsession);
		    // 授权成功
		    if ((Integer) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("errcode") == null) {
		      map.put("access_token",
		          (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("access_token"));
		      map.put("openid", (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("openid"));
//		      map.put("expires_in",  com.alibaba.fastjson.JSONObject.parseObject(wechatsession).getInteger("expires_in").toString());
		      map.put("refresh_token", (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("refresh_token"));
		      map.put("scope", (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("scope"));
		      // 授权失败
		    } else if ((Integer) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("errcode") != null) {
		      map.put("errcode", com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("errcode").toString());
		    }
		    return map;
		  }
		  
		  
		  /**第三方登录时用来获取用户信息
		   * 根据access_token和openid获取微信用户信息
		   * @param openid 普通用户标识，对公众帐号是唯一的
		   * @param access_token 调用接口凭证
		   * @return
		   */
		  public Map<String, String> getUserInfo(WeixinVo weixinVo) {

		    String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + weixinVo.getAccessToken() + "&openid="
		        + weixinVo.getOpenid() +"&lang=zh_CN";
		    // 用code调用微信接口换取用户openid和session_key
		    String wechatsession = HttpKit.get(url);
		    Map<String, String> map = new HashMap<String, String>();
		    System.out.println("微信授权返回参数：" + wechatsession);

		    // 授权成功
		    if ((Integer) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("errcode") == null) {
		      //昵称
		      map.put("nickname",
		          (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("nickname"));
		      map.put("openid", (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("openid"));
		      //性别 1 为男性，2 为女性
		      map.put("sex", (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("sex").toString());
		      //普通用户个人资料填写的省份
		      map.put("province", (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("province"));
		      //普通用户个人资料填写的城市
		      map.put("city", (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("city"));
		      //国家
		      map.put("country", (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("country"));
		      //用户头像，最后一个数值代表正方形头像大小（有 0、46、64、96、132 数值可选，0 代表 640*640 正方形头像），用户没有头像时该项为空
		      map.put("headimgurl", (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("headimgurl"));
		      //用户特权信息，json 数组
		    //  map.put("privilege", (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("privilege"));
		      map.put("unionid", (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("unionid"));
		      // 授权失败
		    } else if ((Integer) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("errcode") != null) {
		      map.put("errcode", com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("errcode").toString());
		    }
		    return map;
		  }
		  
		  
		  /**
		   * 刷新access_token
		   * @param appid
		   * @param refreshToken
		   * @return
		   */
		  public Map<String, String> refreshAccessToken(WeixinVo weixinVo) {
		    String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + appid + "&secret="
		        + secret +  "&grant_type=refresh_token"+"&refresh_token=" + weixinVo.getRefreshToken();

		    // 用code调用微信接口换取用户openid和session_key
		    String wechatsession = HttpKit.get(url);
		    Map<String, String> map = new HashMap<String, String>();
		    System.out.println("微信授权返回参数：" + wechatsession);
		    // 授权成功
		    if ((Integer) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("errcode") == null) {

		      map.put("access_token",
		          (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("access_token"));
		      map.put("openid", (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("openid"));
//		      map.put("expires_in", com.alibaba.fastjson.JSONObject.parseObject(wechatsession).getInteger("expires_in").toString());
		      map.put("refresh_token", (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("refresh_token"));
		      map.put("scope", (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("scope"));
		      // 授权失败
		    } else if ((Integer) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("errcode") != null) {
		      map.put("errcode", com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("errcode").toString());
		    }
		    return map;
		  }
		  
		  /**
		   * 校验access_token
		   * @param accessToken
		   * @param openid
		   * @return
		   */
		  public Map<String, String> checkAccessToken(WeixinVo weixinVo) {
		    String url = "https://api.weixin.qq.com/sns/auth?access_token=" + weixinVo.getAccessToken() + "&openid="
		        + weixinVo.getOpenid();

		    // 用code调用微信接口换取用户openid和session_key
		    String wechatsession = HttpKit.get(url);
		    Map<String, String> map = new HashMap<String, String>();
		    System.out.println("微信授权返回参数：" + wechatsession);
		    // 授权成功
		    if ((Integer) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("errcode") == null) {
		      map.put("errmsg",
		          (String) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("errmsg"));
		      // 授权失败
		    } else if ((Integer) com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("errcode") != null) {
		      map.put("errcode", com.alibaba.fastjson.JSONObject.parseObject(wechatsession).get("errcode").toString());
		    }
		    return map;
		  }

}
