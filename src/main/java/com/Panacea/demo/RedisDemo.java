package com.Panacea.demo;
/**
 * Redis工具Demo；首先pom文件导包，然后配置文件写入相关配置（配置内容查看application.properties文件）
 * @author 夜未
 * @since 2020年9月14日
 */

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Panacea.unity.bean.User;
import com.Panacea.unity.config.RedisConfig;
import com.Panacea.unity.service.IUserService;
import com.Panacea.unity.service.impl.UserServiceImpl;
import com.Panacea.unity.util.BaseUtil;
import com.Panacea.unity.util.RedisUtil;
import com.Panacea.unity.util.Result;
@RestController
@RequestMapping("redis")
public class RedisDemo {
	
	
	/**
	 *  Redis的配置类
	 */
	RedisConfig redisConfig;
	
	/**
	 * Redis的工具类，直接注入后就可以使用了
	 */
	@Autowired
	RedisUtil redisUtil;
	

	/**
	 * 测试放入普通缓存，缓存的key如果需要建立一个指定的目录来存放，就在key前面添加 "目录名:"
	 * 如“Token:abcd”;"Token:efgh";就可以建立一个Token的文件夹目录，下面包含"abcd"和"efgh"这2个KEY
	 * 缓存数据时可以调用指定方法来设置缓存时间，具体看工具类
	 * @param key
	 * @param value
	 * @return
	 */
	@RequestMapping("put")
	@ResponseBody
	public Result testRedisPut(String key,String value) {
		redisUtil.set(key, value);
		return BaseUtil.reFruitBean("成功", Result.SUCCESS, null);
	}
	
	/**
	 * 测试放入集合List数据
	 */
	@Autowired
	IUserService userServiceImpl;
	/**
	 * list需要序列化成String类型，再调用ISet方法放入redis中，不要用set方法，虽然也能缓存进去，
	 * 但是格式不一样，应该使用专门缓存List的方法
	 */
	@RequestMapping("putList")
	@ResponseBody
	public Result putList(String key) {
		List<User>list=userServiceImpl.selectAll();
		redisUtil.lSet(key, list.toString());
		return BaseUtil.reFruitBean("成功", Result.SUCCESS, null);
	}
	
	

}
