package com.Panacea.unity.config;

import java.io.Serializable;

import org.apache.shiro.authc.AuthenticationToken;

import lombok.Data;

/**
 * 手机号登录用的token
 * @author 夜未
 * @since 2021年1月21日
 */
@Data
public class ShiroPhoneToken implements AuthenticationToken , Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7961067247764323100L;
	
	// 手机号码
    private String phone;
    private boolean rememberMe;
    private String host;
    
	@Override
	public Object getPrincipal() {
		return phone;
	}
	@Override
	public Object getCredentials() {
		return phone;
	}
	
	public ShiroPhoneToken() {super();}
	 
    public ShiroPhoneToken(String phone) { this(phone, false, null); }
 
    public ShiroPhoneToken(String phone, boolean rememberMe, String host) {
        this.phone = phone;
        this.rememberMe = rememberMe;
        this.host = host;
    }
}
