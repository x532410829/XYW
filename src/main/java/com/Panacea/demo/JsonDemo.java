package com.Panacea.demo;

import java.io.IOException;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.Panacea.unity.bean.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * 各种json解析工具的使用
 * @author 夜未
 * @since 2020年9月16日
 */
public class JsonDemo {
	
//--------------------------- fastjson --------------------------------------------------------	
	/**导包 fastjson
	 * 1：由实体类生成Json字符串
	 */
	@Test //如果无法导入Test包，就去pom文件把Test包的域限制<scope>注释掉
	public void EntityToJson1(){
		User user=new User(null,"xxx","121212");
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("userName", user.getUserName());
	    jsonObject.put("passWord",user.getPassWord());
	    jsonObject.put("createTime", user.getCreateTime());
	    System.out.println(jsonObject.toString());
	}
	/**
	 * 由Json字符串生成实体类
	 */
	@Test
	public void JsonToEntity1(){
	    String jsonString = "{\"userName\":\"哈哈\",\"passWord\":\"123123\",\"createTime\":\"1600226705421\"}";
	    JSONObject jsonObject =JSONObject.parseObject(jsonString);
	    User user=new User();
	    user.setUserName(jsonObject.get("userName").toString());
	    user.setPassWord(jsonObject.get("passWord").toString());
	    user.setCreateTime(new Date(Long.valueOf(jsonObject.get("createTime").toString())));
	    System.out.println(user);
	}
	
	/**
	 * 由实体类生成Json字符串
	 */
	@Test
	public void EntityToJson(){
		User user=new User(1L,"xxx","121212");	
	    Object jsonString = JSON.toJSON(user);
	    System.out.println(jsonString.toString());
	}
	
	/**
	 * 解析JSON生成实体类
	 */
	@Test
	public void JsonToEntity(){
	    String jsonString = "{\"userName\":\"哈哈\",\"passWord\":\"123123\",\"id\":\"1\"}";
	    User user = JSON.parseObject(jsonString, User.class);
	    System.out.println(user.toString());
	}
	
///////////////////////////////// Jackson ///////////////////////////////////////////////////////////	
	/**
	 *  2：利用Jackson方式：
	 *	由实体类生成Json字符串
	 * @throws JsonProcessingException
	 */
	@Test
	public void EntityToJson2() throws JsonProcessingException {
		User user=new User(1L,"xxx","121212");
	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonString = objectMapper.writeValueAsString(user);
	    System.out.println(jsonString);
	}
	/**
	 * 解析JSON字符串生成实体
	 * @throws IOException
	 */
	@Test
	public void JsonToEntity2() throws IOException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonString = "{\"userName\":\"哈哈\",\"passWord\":\"123123\",\"id\":\"1\"}";
	    User person = objectMapper.readValue(jsonString, User.class);
	    System.out.println(person);
	}
	
//--------------------------- GSON -----------------------------------------------------------------	
	/**
	 * 3:利用GSON方式：
	 * 由实体类生成Json字符串：
	 */
	@Test
	public void EntityToJson3(){
		User user=new User(1L,"xxx","121212");	
		Gson gson = new Gson();
	    String jsonString = gson.toJson(user);
	    System.out.println(jsonString);
	}
	/**
	 * 解析JSON生成实体类
	 */
	@Test
	public void JsonToEntity3(){
	    String jsonString = "{\"userName\":\"哈哈\",\"passWord\":\"123123\",\"id\":\"1\"}";
	    Gson gson = new Gson();
	    User user = gson.fromJson(jsonString, User.class);
	    System.out.println(user.toString());
	}
	
	
	

	
	
	
	
	
	
	
	

}
