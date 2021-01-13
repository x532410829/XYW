package com.Panacea.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Panacea.unity.bean.JpaUser;
import com.Panacea.unity.dao.UserRepository;
import com.Panacea.unity.util.BaseUtil;
import com.Panacea.unity.util.Result;

/**
 * JPA框架的使用demo
 * @author 夜未
 * @since 2020年12月11日
 */
@RestController
@RequestMapping("jpa")
public class JPADemo {
	
	/**
	 * 1、导一个JPA的包，查看pom文件；注意，如果和mybatis一起用，可能会有jar包冲突，解决办法是找到
	 * 报错提示的jar包名字，在项目的maven的jar包目录下找到它，右键剔除它就可以了，jar包冲突都可以
	 * 这样解决的；
	 * 
	 * 2、JPA的配置文件查看 application.yml 文件，其他的配置没有了
	 */
	
	/**
	 * JPA的实体类配置，详细信息看实体类里面写的
	 */
	JpaUser jpaUser;
	
	/**
	 * JPA的持久层示例，正常开发的话要有个service层，我这边直接省略了，因为service层不需要配置什么
	 * 持久层的详细信息看 UserRepository
	 */
	@Autowired
	UserRepository userRepository;
	
	
	
	
	
	
	/**
	 * 添加数据的实例
	 * @param user
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public Result  addJPAUser(@RequestBody JpaUser user) {
//因为设置了user的userName不重复，所以要判断userName是否已存在，如果存在还直接保存的话会抛异常哦
		JpaUser user1=new JpaUser();
		user1.setUserName(user.getUserName());
 		Example<JpaUser>example=Example.of(user1);//把user1封装成Example,就可以按userName查询
		if(userRepository.exists(example)) {//查询userName是否已存在
		user=userRepository.save(user);
		}else {
			return BaseUtil.reFruitBean("名字已存在", Result.SUCCESS, user);
		}
		//返回的user为数据库中的user，包括自动生成的id
		return BaseUtil.reFruitBean("成功", Result.SUCCESS, user);
	}
	
	/**
	 * 删除的实例
	 * @param user
	 * @return
	 */
	@RequestMapping("del")
	@ResponseBody
	public Result  delJPAUser(@RequestBody JpaUser user) {
		//删除数据是需要配合existsById判断数据是否存在，如果数据不存在去调用删除的话会抛异常
		if(userRepository.existsById(user.getId())) {
			userRepository.deleteById(user.getId());
		}else {
			return BaseUtil.reFruitBean("数据不存在", Result.SUCCESS, null);
		}
		return BaseUtil.reFruitBean("成功", Result.SUCCESS, null);
	}

}
