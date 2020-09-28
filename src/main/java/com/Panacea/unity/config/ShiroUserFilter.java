package com.Panacea.unity.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;

import com.Panacea.unity.util.Result;
import com.alibaba.fastjson.JSON;

/**
 * 自定义的拦截器，用来拦截未登录后返回JSON格式的信息
 * @author 夜未
 * @since 2020年9月24日
 */
public class ShiroUserFilter extends UserFilter{
	@Override
	protected void redirectToLogin(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
		// 返回json
		servletResponse.setContentType("application/json; charset=utf-8");

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("errorType", Result.AUTHENTICATION_FAILED);
		response.put("errorMessage", "未登录或者登录已失效");
		response.put("data", null);

		String json = JSON.toJSONString(response);

		servletResponse.getWriter().write(json);
	}
}
