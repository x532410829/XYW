package com.Panacea.unity.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xmlpull.v1.XmlPullParserException;

import com.Panacea.unity.bean.vo.WeixinVo;
import com.jfinal.kit.HttpKit;




/**
 * 微信相关(第三方登录、下单支付)
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
	    * 商户号需要去商户平台申请好，微信支付需要商户号
	    */
	   private static String mch_id = "换成自己的商户号";
	   /**
	    * 微信支付需要用它生成签名
	    */
	   private static String key = "换成自己的商户的支付密匙key";
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
		  
		  
		  
		  /**
		   * 微信统一下单接口
		   * @param object 参数看需要传递，也可以直接复制下面的代码放到自己的下单接口里面
		   * @return
		   */
		  public Result addOrder(Object object) {
			  try {
				Map<String, String> map = new HashMap<String, String>();
				// 获取随机串
				String nonceStr = UUID.randomUUID().toString().substring(0, 32);
				String body = "***商铺下单";// 商品信息，可以自己起最好写英文
				// 密匙,商户平台的支付API密匙，注意是商户平台，不是微信平台
			//	long timestamp = System.currentTimeMillis() / 1000;
				map.put("appid", appid);//公众账号ID
				map.put("mch_id", mch_id);//商户号
				map.put("nonce_str", nonceStr);//随机字符串
				map.put("body", body);//商品描述
				map.put("out_trade_no", "订单号根据具体的需求自己生成");//商户订单号
				map.put("total_fee", "注意这里的金额为分，可能需要换算一下单位");//订单总金额，单位为分
				map.put("spbill_create_ip", "请求的ip地址，可以是：request.getRemoteAddr()");
				
				// 这里写支付成功后回调的地址，微信会以XML形式返回数据，回调的接口自己另外写相关逻辑
				map.put("notify_url", "http://111.111.1.1:8080/test/wxPayResult");
			//	JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付
			//	不同trade_type决定了调起支付的方式，请根据支付产品正确上传
				map.put("trade_type", "NATIVE");//这里设置扫码支付，下单后会返回二维码地址
			
				// 这里传入Map集合和key商户支付密匙生成签名
				String paySign = WeiXinUtil.getPayCustomSign(map, key);
				//签名
				map.put("sign", paySign);
				// 将map转为XML格式
				String xml = WeiXinUtil.ArrayToXml(map);
				// 统一下单地址，固定就这个地址
				String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
				// 沙箱地址
				//String url = "https://api.mch.weixin.qq.com/sandboxnew/pay/unifiedorder";
				// 调用统一下单,并传输参数，得到返回值
				String xmlStr = HttpKit.post(url, xml);

				Map<String, String> map2 = WeiXinUtil.doXMLParse(xmlStr);
				//返回结果看微信开发文档：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1
				
				//返回的状态码：SUCCESS/FAIL
				String return_code=(String) map2.get("return_code");
				//返回为FAIL时，会有错误信息
				String return_msg=(String) map2.get("return_msg"); 
				//返回的业务结果：SUCCESS/FAIL，当状态码和业务结果都为SUCCESS时下单成功
				String result_code=(String) map2.get("result_code"); 
				//此外还可以拿到其他的许多参数信息，如商户号、appid、sign等，具体的看开发文档	
				
				//下单成功
				//当trade_type=NATIVE时有返回，扫码支付下单成功后会得到二维码URL，可以转为二维码图片
				String urlCode = (String) map2.get("code_url"); 
					return BaseUtil.reFruitBean("下单成功", Result.SUCCESS, urlCode);
				} catch (XmlPullParserException e) {
					e.printStackTrace();
					return BaseUtil.reFruitBean("XML解析失败", Result.PARAMETER_ERROR, null);
				} catch (IOException e) {
					e.printStackTrace();
					return BaseUtil.reFruitBean("XML解析失败", Result.PARAMETER_ERROR, null);
				} catch (Exception e) {
					  e.printStackTrace();
					  return BaseUtil.reFruitBean("下单失败", Result.UNKOWN_ERROR, null);
				  }
		  }
		  
			/**
			 * 接收订单微信支付通知，基本上回调的接口都这样，只是返回的东西不一样
			 * 退款通知返回参数的详情看文档：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_16&index=10
			 * @param request
			 * @param response
			 * @throws Exception
			 */
			public void wxPayResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
				String resXml = WeiXinUtil.getWeiXinResponse(request);// 字节流 转换成 字符流
				try {
					Map<String, String> resMap = WeiXinUtil.doXMLParse(resXml);
					
					if (resMap != null) {
						String return_code = resMap.get("return_code");
						if ("FAIL".equals(return_code)) {
							String return_msg = "";
							return_msg = resMap.get("return_msg");
							System.out.println("支付调用失败，返回信息："+return_msg);
						} else if ("SUCCESS".equals(return_code)) {
							String result_code = resMap.get("result_code");
							if (result_code.equals("SUCCESS")) {
								String sign = resMap.get("sign");
								resMap.remove("sign");
								String result = WeiXinUtil.getPayCustomSign(resMap, key);
								if (sign.equals(result)) {
	// 支付成功可以拿到很多参数，如签名、订单号、商户号	、appid、商品ID、openid这些；
	//具体的查看对应回调的返回参数的开发文档，如：https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_4								
									String out_trade_no = resMap.get("out_trade_no");//订单号
									//拿到订单号就可以写自己的支付成功后对订单的操作逻辑了
									} else {
									System.out.println("=====签名不正确，微信支付失败=====");
								}
							} else {
								System.out.println("=====微信支付失败=====");
							}
						} else {
							System.out.println("支付失败，返回值错误");
						}
					}else {
						System.out.println("=====数据为空=====");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		  
			/**
			 * 微信申请退款
			 * @param out_trade_no 订单号
			 * @param total_fee 总金额
			 * @param refund_fee 申请退款金额
			 * @param refundBean 订单的其他详情,按具体情况换成自己的订单实体类来传递必要的参数
			 * @return
			 * @throws Exception
			 */
			public String refundWxpay(String out_trade_no, Integer total_fee, Integer refund_fee,Object refundBean) throws Exception {
System.out.println("申请____退款：订单号：" + out_trade_no + "总金额：" + total_fee + "申请退款金额：" + refund_fee);
				Map<String, String> map = new HashMap<String, String>();
				map.put("appid", appid);//appid
				map.put("mch_id", mch_id);//商户号
				//随机字符串，不长于32位
				map.put("nonce_str", UUID.randomUUID().toString().substring(0, 32));
				
				//商户订单号,也可以是transaction_id；微信生成的订单号，在支付通知中有返回；2选1即可
				map.put("out_trade_no", out_trade_no);
				//订单总金额，单位为分，只能为整数
				map.put("total_fee", String.valueOf((int)total_fee));
				//退款总金额，订单总金额，单位为分，只能为整数
				map.put("refund_fee", String.valueOf((int)refund_fee));
				//生成一个商户退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔
				String refundOrderNumber = BaseUtil.setOrderNumber("XYZ");
				map.put("out_refund_no", refundOrderNumber);
				//退款结果通知的回调地址
				map.put("notify_url", "换成你的回调地址如：https://xyz123456.com/notify/");
				//生成签名
				String sign = WeiXinUtil.getPayCustomSign(map, key);
				map.put("sign", sign);
				//换成xml格式参数
				String xml = WeiXinUtil.ArrayToXml(map);
				try {
					//调用微信退款API,得到返回值的JSON数据，按实际情况解析JSON
					String responJSON = WeiXinUtil.doRefund(mch_id, "https://api.mch.weixin.qq.com/secapi/pay/refund", xml);
				//返回值具体信息查看文档：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_4	
					
					
					return responJSON;
					
				} catch (Exception e) {
					return null;
				}
			}
		  
		  

}
