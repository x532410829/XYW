package com.Panacea.unity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig{
//如果使用自定义的tomcat启动项目，就不需要下面的配置，用自带的tomcat启动就要打开下面的配置
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
    	System.out.println("ServerEndpointExporter被注入了");
        return new ServerEndpointExporter();
    }
    
 }
