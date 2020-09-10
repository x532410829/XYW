package com.Panacea.demo;
/**
 * Mybatis和通用mapper配置使用
 * @author 夜未
 * @since 2020年9月10日
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Panacea.PanaceaProjectApplication;
import com.Panacea.unity.bean.User;
import com.Panacea.unity.bean.vo.UserVo;
import com.Panacea.unity.config.MyMapper;
import com.Panacea.unity.service.IUserService;
import com.Panacea.unity.util.BaseUtil;
import com.Panacea.unity.util.Result;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * Mybatis 和通用mapper的使用
 * @author 夜未
 * @since 2020年9月10日
 */
@RestController
@RequestMapping("mybaties")
public class MybatisDemo {
	
	
	/**导包，查看pom文件
	 * 通用mapper的配置类，在配置文件 application.properties 里配置该类的路径等
	 */
	 MyMapper<User> mapper;
	
	 /**
	  * 启动类添加注解 @MapperScan({"com.Panacea.unity.dao","com.Panacea.* .dao"})
	  * 对mapper类的扫描，多个路径时用 , 分隔，
	  * 导包要导 tk.mybatis.spring.annotation.MapperScan;不能导Mybaties的包
	  */
	 PanaceaProjectApplication panaceaProjectApplication;
	 
	 
	 /**
	  * 注入service 调用service的方法
	  * IService为通用service的方法，BaseService实现了这个接口类，自己的service类继承它就可以使用
	  * 通用的service方法了
	  */
	 @Autowired
	 private IUserService userService;
	 
	 
	 /**
	  * demo 添加一条user数据
	  * 这里使用的是基础的通用mapper方法，不需要额外自己写service方法
	  * @param user
	  * @return
	  */
	 @RequestMapping("addUser")
	 @ResponseBody
	 public Result addUser(@RequestBody User user) {
		 user.setCreateTime(new Date());
		 userService.saveSelective(user);
		 return BaseUtil.reFruitBean("返回提示信息", Result.SUCCESS, null);
	 }
	 
	 /**
	  * 使用自定义的service方法，进行分页查询
	  * @param userVo
	  * @return
	  */
	 @RequestMapping("findUser")
	 @ResponseBody
	 public Result findUser(@RequestBody UserVo userVo) {
		//带分页查询，分页的语句写在了service里面
		List<User>list= userService.selectByTime(userVo);
		 //转成分页格式的信息再返回前端
		PageInfo<User> info=new PageInfo<User>(list);
		 return BaseUtil.reFruitBean("返回提示信息成功", Result.SUCCESS, info);
	 }
	 
	 /**
	  * 复杂查询语句的使用，基本能满足日常的一些复杂条件查询了
	  * @param userVo
	  * @return
	  */
	 public Result findUserByeExample(@RequestBody UserVo userVo) {

		 User user=new User(userVo);
		 List<Long> ids=new ArrayList<Long>();//集合作为 andIn 条件的参数
		 
		 //复杂查询可以使用example来查询
			Example example=new Example(User.class);
			example.createCriteria().andCondition("user_name ==", userVo.getUserName())//条件查询
			.andCondition("create_time >=", userVo.getCreateTime())//时间条件查询
			.andCondition("create_time <=", userVo.getCreateTime())//时间条件查询
			.andIn("id",ids)//条件查询在集合ids里面的id
			.andEqualTo(user)//看方法注释
			.andAllEqualTo(user)//看方法注释，其他还有一些条件查询就不一一列举出来了
			;
			
			//可以配合分页插件一起使用
		 List<User>list= userService.selectByExample(example);
		 //转成分页格式的信息再返回前端
		PageInfo<User> info=new PageInfo<User>(list);
		 return BaseUtil.reFruitBean("返回提示信息成功", Result.SUCCESS, info);
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 

}
