package com.Panacea.unity.service;

import java.util.List;

import com.Panacea.unity.bean.User;
import com.Panacea.unity.bean.vo.UserVo;

/**
 * User 的service
 * @author 夜未
 * @since 2020年9月10日
 */
public interface IUserService extends IService<User> {

	/**
	 * 更新用户信息
	 * @param user
	 */
	void updateUser(User user);
	
	/**
	 * 添加用户信息
	 * @param user
	 */
	void addUser(User user);
	
	/**
	 * 自定义的service方法
	 * 按时间条件查询user列表
	 * @param userVo
	 * @return
	 */
	List<User> selectByTime(UserVo userVo);

	/**
	 * 按用户名查询用户
	 * @param username
	 * @return
	 */
	User findByUsername(String username);
	
	/**
	 * 根据手机号查询用户
	 * @param phone
	 * @return
	 */
	User findByPhone(String phone);

	/**
	 * 检测用户名是否存在
	 * @param userName
	 * @return
	 */
	boolean checkUserName(String userName);
	
	/**
	 * 事务回滚测试1
	 */
	void transactionalTest1(User record);
	
	/**
	 * 事务回滚测试2
	 * @param record
	 */
	void transactionalTest2(User record);

	/**
	 * 事务回滚测试3
	 * @param record
	 */
	void transactionalTest3(User record);
	
	/**
	 * 事务回滚测试4
	 * @param record
	 */
	void transactionalTest4(User record);

	
	/**
	 * 事务回滚测试5
	 * @param record
	 */
	void transactionalTest5(User record);
	
	
	
}
