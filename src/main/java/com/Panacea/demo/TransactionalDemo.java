package com.Panacea.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Panacea.unity.bean.User;
import com.Panacea.unity.service.IUserService;
import com.Panacea.unity.service.impl.UserServiceImpl;
import com.Panacea.unity.util.BaseUtil;
import com.Panacea.unity.util.Result;

/**
 * 事务处理
 * @author 夜未
 * @since 2020年12月16日
 */
@RestController
@RequestMapping("transactional")
public class TransactionalDemo {

	
	@Autowired
	private IUserService userServiceImpl;
	
	
	/**
	 * 事务的隔离级别：是指若干个并发的事务之间的隔离程度
		1. @Transactional(isolation = Isolation.READ_UNCOMMITTED)：读取未提交数据(会出现脏读,不可重复读) 基本不使用
		2. @Transactional(isolation = Isolation.READ_COMMITTED)：读取已提交数据(会出现不可重复读和幻读)
		3. @Transactional(isolation = Isolation.REPEATABLE_READ)：可重复读(会出现幻读)
		4. @Transactional(isolation = Isolation.SERIALIZABLE)：串行化		
 		
 	   事务传播行为：如果在开始当前事务之前，一个事务上下文已经存在，此时有若干选项可以指定一个事务性方法的执行行为
 		1. @Transactional(propagation=Propagation.REQUIRED)
		   如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。这是默认值。
		 
		2. @Transactional(propagation=Propagation.REQUIRES_NEW)
		   创建一个新的事务，如果当前存在事务，则把当前事务挂起。
		 
		3. @Transactional(propagation=Propagation.SUPPORTS)
		   如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
		 
		4. @Transactional(propagation=Propagation.NOT_SUPPORTED)
		   以非事务方式运行，如果当前存在事务，则把当前事务挂起。
		 
		5. @Transactional(propagation=Propagation.NEVER)：
		   以非事务方式运行，如果当前存在事务，则抛出异常。
		 
		6. @Transactional(propagation=Propagation.MANDATORY)
		   如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
		   
		7. @Transactional(propagation=Propagation.NESTED)：
		   如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；
		   如果当前没有事务，则该取值等价于propagation=Propagation.REQUIRED
	 */
	
	/**
	  	嵌套事务：带有事务的方法去调用其它有事务的方法，此时执行的情况取决于配置的事务的传播属性。
	  
	  	事务回滚规则：默认配置下，运行时unchecked异常，就是抛出异常为RuntimeException子类会导
	  	致回滚（Errors也会）；而抛出checked异常则不会回滚
	  
	  	对于异常分两种：检查异常和非检查异常(运行时异常)
		检查异常 : 编译时被检测的异常 （throw后，方法有能力处理就try-catch处理，没能力处理就必
		须throws）。编译不通过，检查语法(其实就是throw和throws的配套使用)。
		运行时异常 : 编译时不被检查的异常(运行时异常。RuntimeException及其子类)。编译通过。
	 */
	
	
	
	
	
	
	
	
	/**
	 * 普通事务回滚1：模拟写入数据后发生异常的回滚操作
	 * @param user
	 * @return
	 */
	@RequestMapping("test1")
	@ResponseBody
	public Result test1(@RequestBody User user) {
		userServiceImpl.transactionalTest1(user);
		return BaseUtil.reFruitBean("执行成功", Result.SUCCESS, null);
	}
	
	@RequestMapping("test2")
	@ResponseBody
	public Result test2(@RequestBody User user) {
		userServiceImpl.transactionalTest2(user);
		return BaseUtil.reFruitBean("执行成功", Result.SUCCESS, null);
	}
	
	@RequestMapping("test3")
	@ResponseBody
	public Result test3(@RequestBody User user) {
		userServiceImpl.transactionalTest3(user);
		return BaseUtil.reFruitBean("执行成功", Result.SUCCESS, null);
	}
	
	@RequestMapping("test4")
	@ResponseBody
	public Result test4(@RequestBody User user) {
		userServiceImpl.transactionalTest4(user);
		return BaseUtil.reFruitBean("执行成功", Result.SUCCESS, null);
	}
	
	
	
	
	
	
	
	
	
	
	
}
