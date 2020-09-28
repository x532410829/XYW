package com.Panacea.unity.config;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Panacea.unity.util.BaseUtil;
import com.Panacea.unity.util.Result;

/**
 * 全局异常处理
 * @author 夜未
 * @since 2020年9月25日
 */
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

	/**
	 * 处理shiro拦截的无权限的错误
	 * @return
	 */
	@ExceptionHandler(value = AuthorizationException.class)
	@ResponseBody
	public Result handleAuthorizationException() {
		return BaseUtil.reFruitBean("暂无该操作权限，请联系管理员！", Result.PERMISSION_DENIED, null);
	}

}
