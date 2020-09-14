package com.Panacea.unity.config.MQ;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
/**
 * MyDirectQueue直连交换机的消费者；
 * 这里是只有单个消费者，如果创建多个消费者，则会以轮询的方式进行消息的消费，不会重复消费，
 * 就是消息会一次分配给所有的消费者，一个消息不会重复分配
 * @author 夜未
 * @since 2020年9月11日
 */
@Component
@RabbitListener(queues = "MyDirectQueue")//监听的队列名称 MyDirectQueue
public class DirectReceiver {
	@RabbitHandler
    public void process(Map testMessage) {
        System.out.println("直连交换机的DirectReceiver消费者收到消息  : " + testMessage.toString());
    }

}
