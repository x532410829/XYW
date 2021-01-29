package com.Panacea.unity.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.Panacea.demo.JWTDemo;
import com.Panacea.demo.WebSocketDemoTwo;
import com.auth0.jwt.exceptions.JWTDecodeException;
/**
 * WebSocket 示例2的配置
 * @author 夜未
 * @since 2020年12月28日
 */
@Configuration
@EnableWebSocket
@SuppressWarnings("unchecked")
public class MyWebSocketConfig implements WebSocketConfigurer   {


	  @Override
	    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		//设置连接路径和处理器，注意别被shiro拦截了
	       registry.addHandler(new WebSocketDemoTwo(), "/ws/demo/{token}")
	                .setAllowedOrigins("*")//设置跨域请求
	                .addInterceptors(new MyWebSocketInterceptor());//设置自定义的拦截器
	    }

	    /**
	     * 自定义拦截器拦截WebSocket请求
	     */
	    class MyWebSocketInterceptor implements HandshakeInterceptor {

	        //前置拦截一般用来注册用户信息，绑定 WebSocketSession
	        @Override
	        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
	                                       WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
	            System.out.println("握手前置拦截~~");

	            if (!(request instanceof ServletServerHttpRequest)) 
	            	return false;
	            
	            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
	           //通过map的映射拿到token的值
				Map<String, ?> map = (Map<String, ?>) servletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
	            
				Object jwt=map.get("token");//URL里面的参数
System.out.println("收到请求的JWT:"+jwt);
				//验证Token是否有效
				if(jwt==null) {//正常不会为null，因为连接地址/{token}这里不可以为空的情况下请求过来
System.out.println("Token参数为空......");
					return false;
				}
				//验证Token有效性
				try {
					String data=JWTDemo.getData(jwt.toString());
		            attributes.put("data", data);//验证成功，可以获取到用户信息再去做校验
				} catch (JWTDecodeException e) {
					System.out.println("验证失败，返回false不给握手连接");
					e.printStackTrace();
					 return false;
				}
	//这边可以设置一些数据到attributes里面，也就是session里面，之后可以在session里面拿到
	            String userName = "小朋友";
	            attributes.put("userName", userName);
	            return true;
	        }

	        @Override
	        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
	               WebSocketHandler wsHandler, Exception exception) {
System.out.println("握手后置拦截~~");
	        }
	    }
	
	
	
	
}
