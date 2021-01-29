package com.Panacea.demo;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.Panacea.unity.config.MyWebSocketConfig;
import com.Panacea.unity.service.IUserService;

/** 加入socket连接时握手请求的JWT认证的websocket连接，必须认证通过才可以连接socket
 * WebSocket 示例2；
 * @author 夜未
 * @since 2020年12月28日
 */
@Component
public class WebSocketDemoTwo implements WebSocketHandler{

	
	/**
	 * 与示例1一样，添加pom依赖，只需添加一个配置文件类 MyWebSocketConfig
	 */
	MyWebSocketConfig myWebSocketConfig;
	
	
	//注意WebSocketDemoTwo中不可以直接使用@Autowired注入属性，会为空；应该使用以下方法注入
	static IUserService userService;
	@Autowired
	public void setUserService(IUserService userService1) {
		WebSocketDemoTwo.userService=userService1;
	}
	
	/**
	 * 保存连接信息的Map
	 */
    private static final Map<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();

	
    /**
     * 客户端连接成功时调用
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userName = session.getAttributes().get("userName").toString();
        SESSIONS.put(userName, session);
        System.out.println(String.format("成功建立连接~ userName: %s", userName));
    }

    /**
     * 收到客户端的消息
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String msg = message.getPayload().toString();
        System.out.println("收到消息："+msg);
    }

    /**
     * 连接出错时调用
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("连接出错");
        if (session.isOpen()) {
            session.close();
        }
    }

    /**
     * 连接关闭时调用
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("连接已关闭,status:" + closeStatus);
    }

    /**
     * 是否支持处理部分消息，暂时不清楚什么用处
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 指定发消息
     *
     * @param message
     */
    public static void sendMessage(String userName, String message) {
        WebSocketSession webSocketSession = SESSIONS.get(userName);
        if (webSocketSession == null || !webSocketSession.isOpen()) return;

        try {
            webSocketSession.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     *
     * @param message
     */
    public static void fanoutMessage(String message) {
        SESSIONS.keySet().forEach(us -> sendMessage(us, message));
    }
	

}
