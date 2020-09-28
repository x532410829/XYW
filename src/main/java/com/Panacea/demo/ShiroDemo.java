package com.Panacea.demo;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Panacea.demo.base.BaseController;
import com.Panacea.unity.bean.User;
import com.Panacea.unity.config.GlobalExceptionHandler;
import com.Panacea.unity.config.MyShiroRealm;
import com.Panacea.unity.config.ShiroConfig;
import com.Panacea.unity.config.ShiroUserFilter;
import com.Panacea.unity.service.IUserService;
import com.Panacea.unity.util.BaseUtil;
import com.Panacea.unity.util.Result;



/**
 * Shiro框架Demo
 * @author 夜未
 * @since 2020年9月17日
 */
@RequestMapping("shiro")//这个路径在shiro的拦截器中放开了
@RestController//定义一个controller
@SuppressWarnings("all")//忽略警告信息
public class ShiroDemo extends BaseController{

	//用户的shiro登录这些在UserControlle
	private UserController userController;
	
	//全局异常处理，这里需要处理shiro拦截的无权限的时候抛出来的异常统一处理
	private GlobalExceptionHandler global;
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 用户自定义的配置文件，具体的看内容
	 */
	private MyShiroRealm m;
	
	/**
	 * shiro的配置文件，具体的看内容
	 */
	private ShiroConfig s;
	
	/**
	 * 用户自定义的shiro拦截器，定义了未登录时拦截返回JSON数据，具体看内容
	 */
	private ShiroUserFilter sf;
	
	
	
	/**
	 * 不需要任何权限,路径拦截已经是放开了的
	 * @param id
	 * @return
	 */
	@RequestMapping("info")
	@ResponseBody
	public Result getUserInfo(Long id) {
		User user=userService.selectByKey(id);
		return BaseUtil.reFruitBean("查询成功", Result.SUCCESS, user);
	}
	
	/**
	 * 验证用户是否登录,一般不用这个验证，直接在ShiroConfig设置拦截地址就可以拦截未登录
	 * @param id
	 * @return
	 */
	@RequestMapping("info0")
	@ResponseBody
	@RequiresAuthentication//拦截登录的注解
	public Result getUserInfo0(Long id) {
		User user=userService.selectByKey(id);
		return BaseUtil.reFruitBean("查询成功", Result.SUCCESS, user);
	}
	
	/**
	 * 验证角色权限
	 * @param id
	 * @return
	 */
	@RequestMapping("info1")
	@ResponseBody
	@RequiresRoles("admin")//需要 admin 角色的权限
	public Result getUserInfo1(Long id) {
		User user=userService.selectByKey(id);
		return BaseUtil.reFruitBean("查询成功", Result.SUCCESS, user);
	}
	
	/**
	 * 验证指定权限
	 * @param id
	 * @return
	 */
	@RequestMapping("info2")
	@ResponseBody
	@RequiresPermissions({"user:list", "user:info"} )//需要user:list和user:info这2个权限
	public Result getUserInfo2(Long id) {
		User user=userService.selectByKey(id);
		return BaseUtil.reFruitBean("查询成功", Result.SUCCESS, user);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
