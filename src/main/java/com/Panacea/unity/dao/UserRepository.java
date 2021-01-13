package com.Panacea.unity.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Panacea.unity.bean.JpaUser;

/**
 * JPA框架的持久层 只需要继承JpaRepository就可以了
 * @author 夜未
 * @since 2020年12月11日
 */
public interface UserRepository  extends JpaRepository<JpaUser, Long>{


	/**
	 * 自定义的简单查询就是根据方法名来自动生成 SQL，主要的语法是findXXBy,readAXXBy,queryXXBy,
	 * countXXBy, getXXBy后面跟属性名称：
	 */
	JpaUser findByUserName(String userName);
	
	/**
	 * 也使用一些加一些关键字And 、 Or
	 */
	JpaUser findByUserNameOrAge(String username, int age);
	
	/**
	 * 修改、删除、统计也是类似语法
	 */
	Long deleteByAge(int age);
	Long countByUserName(String userName);
	
	
	//基本上 SQL 体系中的关键词都可以使用，例如： LIKE 、 IgnoreCase、 OrderBy。
	List<JpaUser> findByUserNameLike(String username);
	JpaUser findByUserNameIgnoreCase(String userName);
	List<JpaUser> findByUserNameOrderByAgeDesc(String email);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
