package com.Panacea.unity.config;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.Panacea.unity.bean.Permission;
import com.Panacea.unity.bean.Role;
import com.Panacea.unity.bean.User;
import com.Panacea.unity.service.IUserService;

/**
 * 自定义的Realm，主要用来验证登录、权限这些
 * @author 夜未
 * @since 2020年9月24日
 */
public class MyShiroRealm extends AuthorizingRealm {
	
	 	@Autowired
	    private IUserService userService;
	 	
	 	/** 多个Realm时需要这个
	 	 * 检查登录时 login(Token) 传过来的token是否支持该Realm
	 	 */
 	   @Override
 	   public boolean supports(AuthenticationToken token) {
 	       return token instanceof UsernamePasswordToken;
 	   }
	 	
 	   	/**注意：这里权限认证不区分Realm，所以要确保下面能获取到userName,可以在所有的登录认证时
 	   	 * 查询用户信息再放userName进去，如手机号登录认证那里就是这样
	 	 * 权限配置类,当需要验证用户权限时会进入到这里，例如方法加了@RequiresRoles("admin")
	 	 */
	    @Override
	    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
	        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
	        String username  = (String) principals.getPrimaryPrincipal();
	        //获取用户信息
	        User user=userService.findByUsername(username);
	        //保存不重复的角色列表
	        Set<String> roleSet = new HashSet<String>();
	        //不重复的权限列表
	        Set<String> permissionSet = new HashSet<String>();
	        for(Role role:user.getRoleList()){
	        	roleSet.add(role.getRole());
	            for(Permission p:role.getPermissions()){
	            	permissionSet.add(p.getPermission());
	            }
	        }
	        authorizationInfo.addRoles(roleSet);
	        authorizationInfo.addStringPermissions(permissionSet);
	        return authorizationInfo;
	    }

	    /**认证配置类
	     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
	     */
	    @Override
	    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
	            throws AuthenticationException {
	        //获取用户的输入的账号.
	        String username = (String)token.getPrincipal();
	        if(username==null) {
	        	throw new UnknownAccountException("账号不存在！");
	        }
	        System.out.println(token.getCredentials());
	        //通过username从数据库中查找 User对象，如果找到，没找到.
	        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
	        User user = userService.findByUsername(username);
//System.out.println("----->>User="+user);
	        if(user == null){
	        	throw new UnknownAccountException("用户名或密码错误！");
	        }
	        if(user.getState()==2) {
	        	throw new LockedAccountException("账号已被锁定,请联系管理员！");
	        }
	        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
	        		user.getUserName(), //用户名
	        		user.getPassWord(), //密码
	                getName()  //realm name
	        );
	        return authenticationInfo;
	    }
}
