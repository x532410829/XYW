package com.Panacea.unity.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import com.Panacea.unity.util.BaseUtil;
import com.Panacea.unity.util.Result;


public class JWTFilter extends BasicHttpAuthenticationFilter{
	
	private static final String TOKEN = "Authentication";
	
	/** 获取允许访问的url **/
	private String getAnonUrl(){
		StringBuffer result = new StringBuffer();
		result.append("/css/,");
		result.append("/js/,");
		result.append("/fonts/,");
		result.append("/img/,");
		result.append("/druid/,");
		result.append("/static/,");
		result.append("/user/,");

		return result.toString();
	}
	
	private Subject getSubject() {
		return SecurityUtils.getSubject();
	}
	
	private Session getSession() {
		return getSubject().getSession();
	}
	
	 @Override
	    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
	        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	        boolean match = false;
	        //校验访问路径是否要被拦截
	        for (String u : getAnonUrl().split(",")) {
	            if ( !BaseUtil.isEmpty(u) && httpServletRequest.getRequestURI().contains(u) ){//获取主目录下的uri
	                match = true;
	                break;
	            }
	        }
	        if (match) 
	        	return true;
	        if (isLoginAttempt(request, response)) {
	            return executeLogin(request, response);
	        }
	        return false;
	    }

	    @Override
	    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
	    	UsernamePasswordToken token = (UsernamePasswordToken)getSession().getAttribute(request.getRemoteAddr());
	        return token != null;
	    }

	    @Override
	    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
//	        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//	        String token = httpServletRequest.getHeader(TOKEN);
//	        JWTToken jwtToken = new JWTToken(token);
	        UsernamePasswordToken usertoken = (UsernamePasswordToken) getSession().getAttribute(request.getRemoteAddr());
	        try {
	            getSubject(request, response).login(usertoken);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	    }

	    /**
	     * 对跨域提供支持
	     */
//	    @Override
//	    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//	        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//	        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//	        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//	        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//	        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
//	        // 跨域时会首先发送一个 option请求，这里我们给 option请求直接返回正常状态
//	        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//	            httpServletResponse.setStatus(HttpStatus.OK.value());
//	            return false;
//	        }
//	        return super.preHandle(request, response);
//	    }

	    //登录超时
	    @Override
	    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
	        HttpServletResponse httpResponse = WebUtils.toHttp(response);
	        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
	        httpResponse.setCharacterEncoding("utf-8");
	        httpResponse.setContentType("application/json; charset=utf-8");
	        final String message = "未认证，请在前端系统进行认证";
	        try (PrintWriter out = httpResponse.getWriter()) {
	            String responseJson = "{\"message\":\"" + message + "\",\"errorType\":\""+Result.LOGIN_TIMEOUT+"\"}";
	            out.print(responseJson);
	        } catch (IOException e) {
	        	
	        }
	        return false;
	    }
}
