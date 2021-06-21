package com.Panacea.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Panacea.demo.base.BaseController;
import com.Panacea.unity.bean.Role;
import com.Panacea.unity.bean.User;
import com.Panacea.unity.bean.User_Role;
import com.Panacea.unity.config.ShiroPhoneToken;
import com.Panacea.unity.service.IRoleService;
import com.Panacea.unity.service.IUserService;
import com.Panacea.unity.service.IUser_RoleService;
import com.Panacea.unity.util.BaseUtil;
import com.Panacea.unity.util.EncryptUtil;
import com.Panacea.unity.util.Result;
import com.alibaba.druid.util.StringUtils;

/**
 * 用户的操作，shiro登录注册这些
 * @author 夜未
 * @since 2020年9月25日
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController{

	/**
	 * 用户的service
	 */
	@Autowired
	private IUserService userService;
 
	/**
	 * 角色service
	 */
	@Autowired
	private IRoleService roleServiceImpl;
	
	/**
	 * 用户关联角色
	 */
	@Autowired
	private IUser_RoleService user_RoleService;
	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("addUser")
	@ResponseBody
	public Result addUser(@RequestBody User user) {
		
		boolean f = userService.checkUserName(user.getUserName());
		if(f) {
			return BaseUtil.reFruitBean("账号已存在", Result.PARAMETER_ERROR, null);
		}
		user.setCreateTime(new Date());
		user.setState((byte) 0);
		// 生成随机的盐字符串
		String salt=new SecureRandomNumberGenerator().nextBytes().toHex();
		user.setSalt(salt);
		// 密码加密
		user.setPassWord(EncryptUtil.encrypt(salt, user.getPassWord()));

		userService.saveSelective(user);
		return BaseUtil.reFruitBean("返回提示信息:成功", Result.SUCCESS, null);
	}
	
	/**
	 * 账号密码登录
	 * @param user
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Result login(@RequestBody User user) {
		if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassWord())) {
			return BaseUtil.reFruitBean("账号或密码为空！", Result.PARAMETER_ERROR, null);
		}

		// 用户认证信息
		Subject subject = SecurityUtils.getSubject();
		
		User user1 = userService.findByUsername(user.getUserName());
		if(user1!=null) {
			//密码加密，加密后与数据库的密码比较
			user.setPassWord(EncryptUtil.encrypt(user1.getSalt(), user.getPassWord()));
		}else {
			return BaseUtil.reFruitBean("账号不存在", Result.PARAMETER_ERROR, null);
		}
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUserName(), user.getPassWord());
		try {
			/** 使用UsernamePasswordToken进行验证的话，调用的是MyShiroRealm去验证，这里可以捕获异常，
			 * 然后返回对应信息*/
			subject.login(usernamePasswordToken);

			getSession().setAttribute("user", user1);
			List<User_Role> ur=user_RoleService.selectByUserId(user1.getId());
			List<Role>list=new ArrayList<Role>();
			for (User_Role u : ur) {
				Role role= roleServiceImpl.selectByKey(u.getRoleId());
				if(role!=null) {
					list.add(role);
				}
			}
			//保存角色列表
			getSession().setAttribute("role", list);
			
		} catch (UnknownAccountException e) {
			return BaseUtil.reFruitBean("用户名不存在！", Result.PARAMETER_ERROR, null);
		} catch (LockedAccountException e) {
			e.printStackTrace();
			return BaseUtil.reFruitBean("账号锁定", Result.PARAMETER_ERROR, null);
		}catch (AuthenticationException e) {
			return BaseUtil.reFruitBean("账号或密码错误！", Result.PARAMETER_ERROR, null);
		} catch (AuthorizationException e) {
			return BaseUtil.reFruitBean("没有权限", Result.PARAMETER_ERROR, null);
		}
		return BaseUtil.reFruitBean("登录成功", Result.SUCCESS, null);
	}
	
	/**
	 * 手机号登录
	 * @param user
	 * @return
	 */
	@RequestMapping("/loginByPhone")
	@ResponseBody
	public Result loginByPhone(@RequestBody User user) {
		//手机验证码登录，这里不写详细的登录逻辑了，具体的按情况实现
		//1、验证手机号和验证码是否正确，正确就下一步
		//2、调用Shiro登录
		Subject subject = SecurityUtils.getSubject();
		//构造一个手机号登录的Token,这个自定义参数，看情况实现
		ShiroPhoneToken token=new ShiroPhoneToken(user.getPhone());
		subject.login(token);
		//登录完成，这里登录和账号密码登录就一样的了，tyr catch异常就知道有没有登录成功了
		
		return BaseUtil.reFruitBean("登录成功", Result.SUCCESS, null);
	}
	
	
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping("/loginOut")
	@ResponseBody
	public Result loginOut() {
		//shiro登出清除缓存
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return BaseUtil.reFruitBean("退出登录成功", Result.SUCCESS, null);
	}
	

	
	
	

}
