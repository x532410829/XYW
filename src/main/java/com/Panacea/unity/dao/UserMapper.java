package com.Panacea.unity.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.Panacea.unity.bean.User;
import com.Panacea.unity.config.MyMapper;
/**
 * User实体类对应的mapper
 * @author 夜未
 * @since 2020年9月10日
 */
public interface UserMapper extends MyMapper<User> {

	@Select("SELECT id,user_name userName,nick_name nickName,pass_word passWord,salt,state, create_time createTime FROM t_user WHERE create_time <=#{createTime}")
	List<User> selectByTime(@Param("createTime")Date createTime);

	@Insert("INSERT INTO t_user(user_name,pass_word,create_time) VALUES(#{userName}, #{passWord}, #{createTime})")
	void insertUser(User user);

	@Update("UPDATE t_user SET user_name=#{userName},pass_word=#{passWord} WHERE id =#{id}")
	void updateUser(User user);

	@Delete("DELETE FROM t_user WHERE id =#{id}")
	void deleteUser(@Param("id")Long id);
	
	@Select("SELECT id,user_name userName,nick_name nickName,pass_word passWord,salt,state, create_time createTime FROM t_user WHERE user_name =#{username}")
	User selectByUserName(@Param("username")String username);
	
//	如果有枚举类型，需要转换成对应的字段
//	@Select("SELECT * FROM users")
//	@Results({
//		@Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
//		@Result(property = "nickName", column = "nick_name")
//	})
//	List<User> getAll();
}
