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
import com.Panacea.unity.config.CustomModularRealmAuthenticator;
import com.Panacea.unity.config.GlobalExceptionHandler;
import com.Panacea.unity.config.MyPhoneShiroRealm;
import com.Panacea.unity.config.MyShiroRealm;
import com.Panacea.unity.config.ShiroConfig;
import com.Panacea.unity.config.ShiroPhoneToken;
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
@RestController
@SuppressWarnings("all")//忽略警告信息
public class ShiroDemo extends BaseController{
	
	//pom文件添加shiro的依赖和 aop的依赖（没有AOP依赖会导致权限注解失效，无法鉴权）
	//添加shiro 缓存配置文件 EHcache.xml
	

	//用户的shiro登录,这些在UserControlle里面，含多个登录方式
	private UserController userController;
	
	//全局异常处理，这里需要处理shiro拦截的无权限的时候抛出来的异常统一处理
	private GlobalExceptionHandler global;
	
	@Autowired
	private IUserService userService;
	
	
	/**
	 * 关于Realm，是用于shiro登录和权限验证的处理类，当多个不同的登录接口时，就需要多个Realm,
	 * 比如正常的账号密码登录，就可以使用默认的MyShiroRealm实现登录和权限验证（因为有账号密码可以用）
	 * 如果是手机验证码或其它第三方登录，由于没有账号密码，可能只有个openId，所以就需要自定义自己
	 * 的Realm去实现登录验证，（这里demo使用MyPhoneShiroRealm 手机验证）；当使用多个
	 */
	
	/**
	 * 当多个Realm时需要创建这个验证器去管理，只有一个时不需要，而且ShiroConfig里面需要在securityManager
	 * 安全管理器里面设置使用验证器为自定义的这个验证器，再把所有的Realm设置进安全管理器去
	 */
	CustomModularRealmAuthenticator cus;
	
	/**
	 * 用户自定义的账号密码登录验证的配置文件，具体的看内容，该Realm对应使用的token类型为默认的类型
	 * UsernamePasswordToken来进行登录验证（看账号密码登录接口里的subject.login(usernamePasswordToken)）
	 * 每个Realm都会处理对应的Token来进行登录验证鉴权等；这个token可以自定义，看MyPhoneShiroRealm
	 */
	private MyShiroRealm m;
	
	/**
	 * 用户自定义的手机号登录验证的配置文件，这个Realm是专门处理手机号登录的，不需要密码，与其对应的
	 * Token是ShiroPhoneToken,token的具体属性可以按情况自定义，如第一第三方用户信息、openId等用于登录
	 * 和验证，具体实现方式看ShiroPhoneToken
	 */
	private MyPhoneShiroRealm phoneShiroRealm;
	/** MyPhoneShiroRealm对应的Token */
	private ShiroPhoneToken token;
	
	
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
