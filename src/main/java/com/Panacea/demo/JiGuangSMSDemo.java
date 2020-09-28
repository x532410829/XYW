package com.Panacea.demo;

import cn.jsms.api.SendSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;

/**
 * 极光短信推送工具，直接导包后就可以用了
 * @author 夜未
 * @since 2020年9月24日
 */
public class JiGuangSMSDemo {
	
	/**
	 * 去极光官网查看自己的密钥
	 */
    private static final String appkey = "换成自己的";
    private static final String masterSecret = "换成自己的";
    

	
	/**  
	 * 模板ID，就是编辑的短信内容模板对应的ID，模板需要去极光官网创建，审核通过后得到一个模板ID
	 * 发送验证码
	 * @param phone 手机号
	 * @param code 验证码，用数字
	 * @return 失败返回null 
	 */
    public static SendSMSResult sendTemplateSMS(String phone,String code)  {
	    SMSClient client = new SMSClient(masterSecret, appkey);
        SMSPayload payload = SMSPayload.newBuilder()
                .setMobileNumber(phone)
                .setTempId(1)//模板ID，
                .addTempPara("code", code)
                .build();
            SendSMSResult res;
			try {
				res = client.sendTemplateSMS(payload);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
            return res;
    }


}
