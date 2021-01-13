package com.Panacea.unity.config.MQ;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 延时队列的实现
 * @author 夜未
 * @since 2020年12月1日
 */
@Configuration
public class DelayQueueConfig {

	  /**
	   * 创建延时消息的交换机
	   * @return
	   */
	  @Bean
	  public CustomExchange delayExchange() {
	    Map<String, Object> args = new HashMap<>();
	    args.put("x-delayed-type", "direct");
	    //注意 CustomExchange 的类型必须是 x-delayed-message            设置持久化
	    return new CustomExchange("my_delay_exchange", "x-delayed-message",true, false,args);
	  }
	 
	  /**
	   * 创建延时队列
	   * @return
	   */
	  @Bean
	  public Queue queue() {
	    Queue queue = new Queue("MyDelayQueue", true);//设置持久化
	    return queue;
	  }
	  
	  /**
	   * 绑定交换机和队列
	   * @return
	   */
	  @Bean
	  public Binding binding() {
	    return BindingBuilder.bind(queue()).to(delayExchange()).with("MyDelayQueue").noargs();
	  }
	
}
