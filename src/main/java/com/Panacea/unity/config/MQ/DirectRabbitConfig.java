package com.Panacea.unity.config.MQ;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 直连交换机和队列的配置
 * @author 夜未
 * @since 2020年9月11日
 */
@Configuration
public class DirectRabbitConfig {

	//创建一个队列 起名：MyDirectQueue
    @Bean
    public Queue MyDirectQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("MyDirectQueue",true,true,false);
 
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue("MyDirectQueue",true);
    }
 
    //创建Direct交换机 起名：MyDirectExchange
    @Bean
    DirectExchange MyDirectExchange() {
      //  return new DirectExchange("MyDirectExchange",true,true);
        return new DirectExchange("MyDirectExchange",true,false);
    }
 
    //绑定  将队列和交换机绑定, 并设置用于匹配键：MyDirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(MyDirectQueue()).to(MyDirectExchange()).with("MyDirectRouting");
    }
 
 
 
    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }

}
