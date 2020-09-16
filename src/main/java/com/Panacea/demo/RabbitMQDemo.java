package com.Panacea.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Panacea.unity.bean.User;
import com.Panacea.unity.config.MQ.DirectRabbitConfig;
import com.Panacea.unity.config.MQ.DirectReceiver;
import com.Panacea.unity.config.MQ.FanoutRabbitConfig;
import com.Panacea.unity.config.MQ.FanoutReceiverA;
import com.Panacea.unity.config.MQ.FanoutReceiverB;
import com.Panacea.unity.config.MQ.FanoutReceiverC;
import com.Panacea.unity.config.MQ.TopicManReceiver;
import com.Panacea.unity.config.MQ.TopicRabbitConfig;
import com.Panacea.unity.config.MQ.TopicTotalReceiver;
import com.Panacea.unity.util.BaseUtil;
import com.Panacea.unity.util.Result;

/**
 * RabbitMQ 消息订阅发布demo，
 * 首先pom文件导包，然后写配置文件，配置内容查看application.properties文件；
 * 这里配置MQ的交换机有3种，每一种都单独配置一个配置类来和对应的队列绑定，配置和绑定成功后，可以在
 * MQ的控制面板里面看到对应的交换机的队列的情况
 * @author 夜未
 * @since 2020年9月11日
 */
@RestController
@RequestMapping("mq")
public class RabbitMQDemo {
	
	/**
	 * 使用RabbitTemplate,这提供了消息的接收/发送等等方法
	 */
	@Autowired
    RabbitTemplate rabbitTemplate;  
	
	
	
//=====================================直连型交换机===============================================================	
	/** Direct Exchange 配置类 
	 *  直连型交换机，根据消息携带的路由键将消息投递给对应队列。
	 *  大致流程，有一个队列绑定到一个直连交换机上，同时赋予一个路由键 routing key 。
	 *	然后当一个消息携带着路由值为X，这个消息通过生产者发送给交换机时，交换机就会根据这个路由值X去寻找
	 *	绑定值也是X的队列
	 */
	 DirectRabbitConfig directRabbitConfig;
	
	 /**
	  * 这里测试发送消息，实际消息的发送看情况处理
	  */
	 	@RequestMapping("/sendDirectMessage")
	    public Result sendDirectMessage() {
	    	String messageId = String.valueOf(UUID.randomUUID());
	        String messageData = "Direct Exchange 发送消息, hello!";
	        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	        Map<String,Object> map=new HashMap<>();
	        map.put("messageId",messageId);
	        map.put("messageData",messageData);
	        map.put("createTime",createTime);
	        System.out.println("推送直连交换机的消息");
	        //将消息携带绑定键值：MyDirectRouting 发送到交换机MyDirectExchange
	        rabbitTemplate.convertAndSend("MyDirectExchange", "MyDirectRouting", map);
	        return BaseUtil.reFruitBean("发送成功", Result.SUCCESS,null);
	    }
	    
	    /**
	     * 消息发送出去了，要创建这个消费者来接收消息，实际的消息处理看情况来实现对应的处理
	     */
	    DirectReceiver directReceiver;
	    
	    
	    
	
	
//=====================================扇型交换机===============================================================	
	
		/**
		 * 	FanoutExchange
		 *	扇型交换机，这个交换机没有路由键概念，就算你绑了路由键也是无视的。 这个交换机在接收到消
		 *	息后，会直接转发到绑定到它上面的所有队列。
		 * 
		 */
	     FanoutRabbitConfig fanoutRabbitConfig;
	     
	     //创建ABC 3个消费者，用于订阅扇形交换机绑定的队列的消息
	     FanoutReceiverA fanoutReceiverA;
	     FanoutReceiverB fanoutReceiverB;
	     FanoutReceiverC fanoutReceiverC;
	    
	     /**
	      * 测试发送消息
	      * @return
	      */
	     @RequestMapping("/sendFanoutMessage")
	     public Result sendFanoutMessage() {
	         String messageId = String.valueOf(UUID.randomUUID());
	         String messageData = "欧耶，我是发送的消息呦";
	         String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	         Map<String, Object> map = new HashMap<>();
	         map.put("messageId", messageId);
	         map.put("messageData", messageData);
	         map.put("createTime", createTime);
	         rabbitTemplate.convertAndSend("fanoutExchange", null, map);
		     System.out.println("推送扇形交换机的消息");
		     return BaseUtil.reFruitBean("发送成功", Result.SUCCESS,null);
		      }
	    
	    
	
//=====================================主题交换机===============================================================	
		/**
		 * Topic Exchange配置类
		 * 主题交换机，这个交换机其实跟直连交换机流程差不多，但是它的特点就是在它的路由键和绑定键
		 * 之间是 有规则的。 简单地介绍下规则：
		 * (星号) 用来表示一个单词 (必须出现的) # (井号) 用来表示任意数量（零个或多个）单词, 
		 * 通配的绑定键是跟队列进行绑定的，举个小例子 队列Q1
		 * 绑定键为 *.TT.* 队列Q2绑定键为 TT.# 如果一条消息携带的路由键为 A.TT.B，那么队列Q1将会收到；
		 * 如果一条消息携带的路由键为TT.AA.BB，那么队列Q2将会收到；
		 */
		 TopicRabbitConfig topicRabbitConfig;
	    
	    
	 	/**
	 	 * 主题交换机的消费者：订阅的队列为topic.man；队列对应交换机的绑定键为topic.man
	 	 */
	 	TopicManReceiver topicManReceiver;
	 	/**
	 	 * 主题交换机的消费者：订阅的队列为topic.woman；队列对应交换机的绑定键为topic.#
	 	 */
	 	TopicTotalReceiver topicTotalReceiver;
	 
	 	
	    /**
	     * 测试模拟消息推送到topicExchange交换机，绑定键为topic.man ；上面创建了2个消费者，
	     * 分别订阅了topic.man和topic.woman 2个队列；交换机绑定的队列中有2种绑定键；一个为
	     * topic.man另一个为topic.# ；由于这里的绑定键为topic.man，所以2个消费者都符合条件，
	     * 都可以接受到消息
	     * @return
	     */
	 	@RequestMapping("/sendTopicMessage1")
	    public Result sendTopicMessage1() {
	 		String messageId = String.valueOf(UUID.randomUUID());
	        String messageData = "啦啦啦，我是发过来的消息哦";
	        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	        Map<String, Object> manMap = new HashMap<>();
	        manMap.put("messageId", messageId);
	        manMap.put("messageData", messageData);
	        manMap.put("createTime", createTime);
	        rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap);
	        System.out.println("推送主题交换机的消息");
	        return BaseUtil.reFruitBean("发送成功", Result.SUCCESS,null);
	 	}
	    
	    /**
	     * 同上测试，这里绑定键为topic.woman；符合该绑定键的交换机只有绑定键为topic.#的交换机
	     * 对应的队列；所以这里发送的消息只有一个消费者TopicTotalReceiver能收到
	     * @return
	     */
	 	@RequestMapping("/sendTopicMessage2")
	    public Result sendTopicMessage2() {
	        String messageId = String.valueOf(UUID.randomUUID());
	        String messageData = "啦啦啦，我是发过来的消息";
	        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	        Map<String, Object> womanMap = new HashMap<>();
	        womanMap.put("messageId", messageId);
	        womanMap.put("messageData", messageData);
	        womanMap.put("createTime", createTime);
	        System.out.println("推送主题交换机的消息");
	        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap);
	        return BaseUtil.reFruitBean("发送成功", Result.SUCCESS,null);
	    }

	

}
