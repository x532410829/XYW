package com.Panacea.unity.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * Shiro配置类
 * @author 夜未
 * @since 2020年9月24日
 */
@Configuration
public class ShiroConfig {
	
	/*
	 * 
	anon:例子/admins/**=anon 没有参数，表示可以匿名使用。

	authc:例如/admins/user/**=authc表示需要认证(登录)才能使用，没有参数

	roles(角色)：例子/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。

	perms（权限）：例子/admins/user/**=perms[user:add:*],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。

	rest：例子/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。

	port：例子/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString

	是你访问的url里的？后面的参数。

	authcBasic：例如/admins/user/**=authcBasic没有参数表示httpBasic认证

	ssl:例子/admins/user/**=ssl没有参数，表示安全的url请求，协议为https

	user:例如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查

	*/
	
	
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		
System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		LinkedHashMap<String, Filter> filters = new LinkedHashMap<>();
    //    filters.put("jwt", new JWTFilter());
        filters.put("authc", new ShiroUserFilter());
        shiroFilterFactoryBean.setFilters(filters);
		
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		//shiroFilterFactoryBean.setLoginUrl("/shiro/login");
		// 登录成功后要跳转的链接
//		shiroFilterFactoryBean.setSuccessUrl("/index");
//		//未授权界面;
//		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		
		//配置拦截器.
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//		filterChainDefinitionMap.put("/**", "jwt");
		
//		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/user/**", "anon");
		filterChainDefinitionMap.put("/shiro/**", "anon");
//		//配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
//		//<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public SecurityManager securityManager(){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());	
//      securityManager.setCacheManager(getEhCacheManager());
//      securityManager.setSessionManager(sessionManager());
		return securityManager;
	}
	
	/**
	 * 管理bean生命周期
	 * @return
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
	
//	@Bean
//	public EhCacheManager getEhCacheManager() {
//	    EhCacheManager em = new EhCacheManager();
//	    em.setCacheManagerConfigFile("classpath:config/Ehcache.xml");
//	    return em;
//	}
//	@Bean
//	public SessionManager sessionManager() {
//		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//		//设置session监听器
//		Collection<SessionListener> listeners = new ArrayList<SessionListener>();
//		listeners.add(new ShiroSessionListener());
//		sessionManager.setSessionListeners(listeners);
//		sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);//30分钟
//		//设置普通的sessiondao
//		//sessionManager.setSessionDAO(sessionDAO());
//		return sessionManager;
//	}
	
	/**
	 * 配置自己的 Realm
	 * @return
	 */
	@Bean
	public MyShiroRealm myShiroRealm(){
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		return myShiroRealm;
	}
	
	
/*
 * 权限校验的注解
 * 
 * @RequiresAuthentication
 * 验证用户是否登录，等同于方法subject.isAuthenticated() 结果为true时。
 * 
 * @RequiresUser
 * 验证用户是否被记忆，user有两种含义：
 * 一种是成功登录的（subject.isAuthenticated() 结果为true）；
 * 另外一种是被记忆的（subject.isRemembered()结果为true）。
 * 
 * @RequiresGuest
 * 验证是否是一个guest的请求，与@RequiresUser完全相反。
 * 换言之，RequiresUser == !RequiresGuest。
 * 此时subject.getPrincipal() 结果为null.
 * 
 * @RequiresRoles
 * 例如：@RequiresRoles("aRoleName");
 * void someMethod();
 * 如果subject中有aRoleName角色才可以访问方法。如果没有这个权限则会抛异常AuthorizationException。
 * 
 * @RequiresPermissions
 * 例如： @RequiresPermissions({"file:read", "write:aFile.txt"} ) voidsomeMethod(); 
 * 要求subject中必须同时含有file:read和write:aFile.txt的权限才能执行方法someMethod()。
 * 否则抛出异常AuthorizationException。
 */
	/**
	 *  开启shiro aop注解支持. 使以下注解能够生效 
	 *  使用代理方式;所以需要开启代码支持;
	 * 需要认证 {@link org.apache.shiro.authz.annotation.RequiresAuthentication RequiresAuthentication}
     * 需要用户 {@link org.apache.shiro.authz.annotation.RequiresUser RequiresUser}
     * 需要访客 {@link org.apache.shiro.authz.annotation.RequiresGuest RequiresGuest}
     * 需要角色 {@link org.apache.shiro.authz.annotation.RequiresRoles RequiresRoles}
     * 需要权限 {@link org.apache.shiro.authz.annotation.RequiresPermissions RequiresPermissions}
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

//	@Bean(name="simpleMappingExceptionResolver")
//	public SimpleMappingExceptionResolver
//	createSimpleMappingExceptionResolver() {
//		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
//		Properties mappings = new Properties();
//		mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
//		mappings.setProperty("UnauthorizedException","403");
//		r.setExceptionMappings(mappings);  // None by default
//		r.setDefaultErrorView("error");    // No default
//		r.setExceptionAttribute("ex");     // Default is "exception"
//		//r.setWarnLogCategory("example.MvcLogger");     // No default
//		return r;
//	}

}
