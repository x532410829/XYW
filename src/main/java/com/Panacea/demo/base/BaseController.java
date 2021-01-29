package com.Panacea.demo.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.Panacea.unity.bean.User;

/**
 * 
 * @author 夜未
 * @since 2020年9月25日
 */
public class BaseController {


	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取当前User信息
	 * @return
	 */
	protected User getCurrentUser() {
		return (User) getSubject().getPrincipal();
	}

	/**
	 * 获取session
	 * @return
	 */
	public static Session getSession() {
		return getSubject().getSession();
	}

	/**
	 * 判断当前session是否有效
	 * @param flag
	 * @return
	 */
	protected Session getSession(Boolean flag) {
		return getSubject().getSession(flag);
	}

	/**
	 * 利用shiro的token登录
	 * @param token
	 */
	protected void login(AuthenticationToken token) {
		getSubject().login(token);
	}
	
	
}
