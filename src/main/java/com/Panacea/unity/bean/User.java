package com.Panacea.unity.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.Panacea.unity.bean.vo.UserVo;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Table(name="t_user")//对应数据库表名称
@Data //lombok 注解后就不需要自己写get/set方法等了
public class User implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5677752284526253848L;


	@Id
	@GeneratedValue(generator = "JDBC")
	private Long id;
	
	
	@Column(name = "user_name") //对应数据库字段
	private String userName;
	
	private String nickName;//昵称
	
	private String phone;//手机号
	
	@Column(name = "pass_word")
	private String passWord;
	
	private String salt;//加密密码的盐
	
//用户状态,0:创建未认证（比如没有激活，没有输入验证码等,待验证的用户 , 1:正常状态,2：用户被锁定.
	 private byte state;
	
//	@Column(name = "create_time")
	//加上日期解析注解，否则数据库拿到的时间可能会差8小时
	@JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
//	@DateTimeFormat(pattern="yyyy-MM-dd HH-mm-ss")
	private Date createTime;

    private List<Role> roleList;// 一个用户具有多个角色
	
	
	@Transient //忽略的字段，不和数据库的字段匹配
	private String address;
	   
	
	/**
	 * 基础构造方法
	 */
	public User() {
		super();
	}
	public User(Long id,String name,String passWord) {
		super();
		this.id=id;
		this.userName=name;
		this.passWord=passWord;
		this.createTime=new Date();
	}
	
	/**
	 * 辅助类转User类
	 * @param userVo
	 */
	public User(UserVo userVo) {
		super();
		this.id=userVo.getId();
		this.passWord=userVo.getPassWord();
		this.userName=userVo.getUserName();
	}
}
