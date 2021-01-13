package com.Panacea.unity.config.MQ;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 延时消息的消费者
 * @author 夜未
 * @since 2020年12月1日
 */
@Component
@RabbitListener(queues = "MyDelayQueue")//可以写在方法上
public class DelayMessageReceiver {
	
	
	 @RabbitHandler
	 public void receive(String msg) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    System.out.println("延时消息接收时间:"+sdf.format(new Date())+"----接收到的消息:"+msg);
	  }
	
}
