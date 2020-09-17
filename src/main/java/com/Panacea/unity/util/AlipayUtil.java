package com.Panacea.unity.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;

/**
 * 支付宝工具（可以用于APP第三方登录、）
 * @author 夜未
 * @since 2020年9月17日
 */
public class AlipayUtil {

		//应用ID
		private static String APP_ID="换成自己应用的ID";
		
		//应用私钥，用支付宝开放平台开发助手生成，上传到支付宝应用设置那里
		private static String APP_PRIVATE_KEY="工具生成，换成自己的就行了";
		
		//支付宝公钥，在应用设置里可以查看得到
		private static String ALIPAY_private_KEY="到支付宝应用设置里面得到";
		
		//支付宝固定地址
		private static String serviceURL="https://openapi.alipay.com/gateway.do";
		private static String CHARSET="utf-8";
		/**
		 * 构造一个客户端
		 */
		private static AlipayClient alipayClient = new DefaultAlipayClient(serviceURL, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_private_KEY, "RSA2");

		
		/**支付宝授权登录
		 * 授权码code换取授权令牌token
		 * @param code 授权的凭证(未授权就传这个)
		 * @param accessToken 授权后的token（已近授权过了就传这个）
		 * @return
		 */
		public AlipaySystemOauthTokenResponse  getAccessToken(String code) {
		//	String code="前端给过来的code";
			
			//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.user.userinfo.share
		// AlipayUserUserinfoShareRequest request = new AlipayUserUserinfoShareRequest();
		// 授权类接口执行API调用时需要带上accessToken
		// AlipayUserUserinfoShareResponse response= alipayClient.execute(request,"accessToken"); 
			
				AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
				request.setCode(code);
				//值为authorization_code时，代表用code换取；值为refresh_token时，代表刷新refresh_token;这里没用到刷新
				request.setGrantType("authorization_code");				

			    AlipaySystemOauthTokenResponse response;
				try {
					response = alipayClient.execute(request);
					 return response;
				} catch (AlipayApiException e) {
					e.printStackTrace();
					return null;
				}
			   
			 }
			        
			        
		/**
		 * 用accessToken获取支付宝用户的个人信息	        
		 * @param accessToken
		 * @return
		 */
		public AlipayUserInfoShareResponse getUserInfo(String accessToken) {

        //获取用户信息
        AlipayUserInfoShareRequest appRequest=new AlipayUserInfoShareRequest();
		try {
			/**
			 * 获取到的用户信息包括支付宝用户的userId，昵称等，具体查看返回值
			 */
			AlipayUserInfoShareResponse appResponse= alipayClient.execute(appRequest,accessToken);
			return appResponse;
		} catch (AlipayApiException e) {
			e.printStackTrace();
			return null;
		}
		}
		
		
		

		
		
		
		
		/**
		 * 这个是给前端去获取Code的请求地址，需要由后端进行生成再给前端用，
		 * 前端用这个地址就可以唤起支付宝APP来得到Code
		 * @return
		 * @throws UnsupportedEncodingException 
		 * @throws AlipayApiException 
		 * @throws Exception
		 */
		public String getUrl() throws Exception {
			// 生成授权的参数
		        String sign = "";
		        Long userId1 = new Date().getTime();
		        String pid = "账号信息里面的pid，换成自己的Pid";
		        String appid = APP_ID;
		        String privateKey =APP_PRIVATE_KEY;
		        StringBuffer stringBuffer = new StringBuffer();
		        stringBuffer.append("apiname=com.alipay.account.auth");
		        stringBuffer.append("&app_id=");
		        stringBuffer.append(appid);
		        stringBuffer.append("&app_name=mc");
		        stringBuffer.append("&auth_type=AUTHACCOUNT");
		        stringBuffer.append("&biz_type=openservice");
		        stringBuffer.append("&method=alipay.open.auth.sdk.code.get");
		        stringBuffer.append("&pid=");
		        stringBuffer.append(pid);
		        stringBuffer.append("&product_id=APP_FAST_LOGIN");
		        stringBuffer.append("&scope=kuaijie");
		        stringBuffer.append("&sign_type=RSA2");
		        stringBuffer.append("&target_id=");
		        stringBuffer.append(userId1);
		        sign = AlipaySignature.rsa256Sign(stringBuffer.toString(), privateKey, "UTF-8");
		        stringBuffer.append("&sign="+ URLEncoder.encode(sign, "UTF-8"));
		       
		        return stringBuffer.toString();
		  }


}
