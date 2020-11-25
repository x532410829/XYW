package com.Panacea.demo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Panacea.unity.util.Result;

/**
 * 日志
 * @author 夜未
 * @since 2020年9月28日
 */
@RestController
@RequestMapping("log")
public class LogDemo {

	//创建日志的工具，工厂模式创建
	 Logger logger = LoggerFactory.getLogger(getClass());
	
	 //application.yml 配置类添加日志对应配置
	 //logback-spring.xml 配置类配置重要的配置，如日志等级，生成方式，最大日志数，
	 //最大容量等，具体查看配置文件里面的注释
	 
	 
	 @Test
	 public void testLog() {
        logger.trace("Trace 日志...");
        logger.debug("Debug 日志...");
        logger.info("Info 日志...");
        logger.warn("Warn 日志...");
        logger.error("Error 日志...");
	   }
	 
	 
	 
	 /**
	  * 测试用
	  * @param num
	  * @return
	  */
	 @RequestMapping("num")
	 public int log(int num) {		 
		 return 10/num;
	 }
	 
	 
}
