package com.Panacea.unity.config.MQ;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * 主题交换机TopicExchange的消费者，这里订阅的是topic.man的队列
 * @author 夜未
 * @since 2020年9月11日
 */
@Component
@RabbitListener(queues = "topic.man")
public class TopicManReceiver {
	@RabbitHandler
    public void process(Map testMessage) {
        System.out.println("主题交换机的TopicManReceiver消费者收到消息  : " + testMessage.toString());
    }
}
