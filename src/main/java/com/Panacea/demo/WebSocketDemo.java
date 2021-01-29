package com.Panacea.demo;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Panacea.unity.config.WebSocketConfig;
import com.Panacea.unity.service.IUserService;

/**WebSocket的实现有几种方式，该项目列举2种主要的，下面是其中一个，日常的简单使用就用这个，
 * 还有另外一种加入JWT认证的，见示例： WebSocketDemoTwo 
 * WebSocket 示例1：服务端demo
 * @author 夜未
 * @since 2020年12月18日
 */
//连接地址记得带端口和项目名如：ws://192.168.1.101:8088/JOJO/websocket/123
//注意别被shiro拦截了
@ServerEndpoint(value ="/websocket/{sid}")
@Component
public class WebSocketDemo {
	
	/**
	 * 添加pom文件依赖，只需要添加下面这一个配置(如果是使用自定义的tomcat启动的话就不需要加)
	 */
	WebSocketConfig webSocketConfig;
	
	//注意WebSocketDemoTwo中不可以直接使用@Autowired注入属性，会为空；应该使用以下方法注入
	static IUserService userService;
	@Autowired
	public void setUserService(IUserService userService1) {
		WebSocketDemoTwo.userService=userService1;
	}
	
	

		//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	    private static int onlineCount = 0;
	    
	    /**
	     * 用来存放每个客户端对应的MyWebSocket对象。它是线程安全的无序的集合，可以将它理解成线程
	     * 安全的HashSet;它具有以下特性：
	     * 	1. 它最适合于具有以下特征的应用程序：Set 大小通常保持很小，只读操作远多于可变操作，需要在遍历期间防止线程间的冲突。
			2. 它是线程安全的。
			3. 因为通常需要复制整个基础数组，所以可变操作（add()、set() 和 remove() 等等）的开销很大。
			4. 迭代器支持hasNext(), next()等不可变操作，但不支持可变 remove()等 操作。
			5. 使用迭代器进行遍历的速度很快，并且不会与其他线程发生冲突。在构造迭代器时，迭代器依赖于不变的数组快照。
	     */
	    private static CopyOnWriteArraySet<WebSocketDemo> webSocketSet = new CopyOnWriteArraySet<WebSocketDemo>();
	 
	    /**与某个客户端的连接会话，需要通过它来给客户端发送数据 */
	    private Session session;
	 
	    //接收连接时带的sid参数
	    private String sid="";
	    
	    @PostConstruct
	    public void init() {
	    	System.out.println("websocket初始化加载.......");
	    }
    
		/**
		 * 连接建立成功调用的方法
		 * @param session
		 * @param sid
		 */
		@OnOpen
	    public void onOpen(Session session, @PathParam("sid") String sid) {
	        this.session = session;
	        webSocketSet.add(this);     //加入set中
	        addOnlineCount();           //在线数加1
	        System.out.println("有新窗口开始监听，pid:"+sid+",当前在线人数为" + getOnlineCount());
	        this.sid=sid;
	        try {
	            sendMessage("连接成功");
	        } catch (IOException e) {
	        	System.out.println("websocket IO异常");
	        }
	    }
	
		
		/**
		 * 连接关闭调用的方法
		 */
		@OnClose
	    public void onClose() {
	        webSocketSet.remove(this);  //从set中删除
	        subOnlineCount();           //在线数减1
	        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	 	}
	
		/**
	     * 收到客户端消息后调用的方法
	     *
	     * @param message 客户端发送过来的消息*/
	    @OnMessage
	    public void onMessage(String message, Session session) {
	       System.out.println("收到来自窗口"+sid+"的信息:"+message);
	        //群发消息
	        for (WebSocketDemo item : webSocketSet) {
	            try {
	                item.sendMessage(message);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	
	    @OnError
	    public void onError(Session session, Throwable error) {
	        System.out.println("发生错误");
	        error.printStackTrace();
	    }
	    
	    /**
	     * 实现服务器主动推送
	     */
	    public void sendMessage(String message) throws IOException {
	        this.session.getBasicRemote().sendText(message);
	    }
	
	    
	    public static void sendInfo(String message,@PathParam("sid") String sid) throws IOException {
	        System.out.println("推送消息到窗口"+sid+"，推送内容:"+message);
	        for (WebSocketDemo item : webSocketSet) {
	            try {
	                //这里可以设定只推送给这个sid的，为null则全部推送
	                if(sid==null) {
	                    item.sendMessage(message);
	                }else if(item.sid.equals(sid)){
	                    item.sendMessage(message);
	                }
	            } catch (IOException e) {
	                continue;
	            }
	        }
	    }
	    
	    
	    
	    
	    
	    
	    
	
    /**
     * 获取在线人数
     * @return
     */
	 public static synchronized int getOnlineCount() {
	        return onlineCount;
	    }
	
	 /**
	  * 添加一个在线人数
	  */
	public static synchronized void addOnlineCount() {
		WebSocketDemo.onlineCount++;
	}
	
	/**
	 * 移除一个在线人数
	 */
	public static synchronized void subOnlineCount() {
		WebSocketDemo.onlineCount--;
	}
	
	/**
	 * 获取在线用户的set列表
	 * @return
	 */
	public static CopyOnWriteArraySet<WebSocketDemo> getWebSocketSet() {
	    return webSocketSet;
	}
	
}
