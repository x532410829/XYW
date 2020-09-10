package com.Panacea.demo;

import org.springframework.boot.web.servlet.ServletComponentScan;

import com.Panacea.PanaceaProjectApplication;
import com.Panacea.unity.config.DruidConfiguration;

/**
 * druid 数据库连接池的相关配置
 * @author 夜未
 * @since 2020年9月10日
 */
public class DruidDemo {
	
	
	/**
	 * 配置文件 application.properties 里配置连接池等druid信息
	 * 访问druid 控制面板  http://localhost:8080/druid/index.html
	 * 登录的账号密码在druid 的配置类里设置 
	 *
	  * druid 的配置类，复制里面内容就好了
	  */
	 DruidConfiguration druidConfiguration;
	 
	 /**
	  * 启动类需要添加注解 @ServletComponentScan 开启配置类组件扫描
	  */
	 PanaceaProjectApplication panaceaProjectApplication;
	 
}
