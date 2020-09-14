package com.Panacea.unity.config.MQ;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;
 
/**
 * 扇形交换机FanoutExchange的消费者，订阅的队列是fanout.A
 * @author 夜未
 * @since 2020年9月11日
 */
@Component
@RabbitListener(queues = "fanout.A")
public class FanoutReceiverA {
	 @RabbitHandler
	    public void process(Map testMessage) {
	        System.out.println("扇形交换机的FanoutReceiverA消费者收到消息  : " +testMessage.toString());
	    }
	 

}
