package com.Panacea.unity.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.Panacea.unity.bean.Permission;
import com.Panacea.unity.bean.Role;
import com.Panacea.unity.bean.User;
import com.Panacea.unity.service.IUserService;

/**
 * 手机号登录的自定义Realm
 * @author 夜未
 * @since 2021年1月21日
 */
public class MyPhoneShiroRealm   extends AuthorizingRealm{
	   public static final String REALM_NAME = "phone_realm";

	 	@Autowired
	    private IUserService userService;
 	
 	 @Override
 	 public String getName() {
 	     return REALM_NAME;
 	 }
 	
 	/**
 	    * 检查是否支持该Realm
 	    * 一定要重写support()方法，在后面的身份验证器中会用到
 	    * @param token
 	    * @return
 	    */
 	   @Override
 	   public boolean supports(AuthenticationToken token) {
 	       return token instanceof ShiroPhoneToken;
 	   }
 	 
 	/**
 	 * 权限配置类,当需要验证用户权限时会进入到这里，例如方法加了@RequiresRoles("admin")
 	 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//这里不实现逻辑，因为已经会触发MyShiroRealm里面的权限认证了,这里再实现的话，也会触发一次，正常没必要
    return null;	
    }

    /**认证配置类
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
//System.out.println("手机号登录的realm");
		ShiroPhoneToken token = null;
    	if(authenticationToken instanceof ShiroPhoneToken){
            token = (ShiroPhoneToken) authenticationToken;
        }else{
            return null;
        }

    	
        //获取用户的输入的账号.
        String phone = (String)token.getPrincipal();
        if(phone==null) {
        	throw new UnknownAccountException("账号不存在！");
        }
//System.out.println(token.getCredentials());
        //通过username从数据库中查找 User对象
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User user = userService.findByPhone(phone);
        if(user == null){
        	throw new UnknownAccountException("账号不存在！");
        }
        if(user.getState()==2) {//账号锁定
			throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
       SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(),phone,
    		   getName());
       
        return authenticationInfo;
    }
}
