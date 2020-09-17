package com.Panacea.unity.bean.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 接收微信传过来的参数
 * @author 夜未
 * @since 2020年9月16日
 */
@Data
public class WeixinVo implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7727597432120425687L;
	
	private String code;					//临时登录凭证
	private String accessToken;					//正规的凭证
	private String refreshToken;					//刷新凭证
	private String secret;					//小程序appsecret
	private String grantType;				//授权类型
	private String appid;					//小程序唯一标识	
	private String encryptedData;			//包括敏感数据在内的完整用户信息的加密数据
	private String iv;						//偏移量
	private String sessionKey;				//加密秘钥
	
	private String money;					//订单金额
	private String openid;					//微信id
	private String orderNum;				//订单号
	private String totalOrdernum;			//总订单号
	private String orderRefundNum;	//退款订单单号
	
	private String unionid;					//同一平台下唯一标识

}
