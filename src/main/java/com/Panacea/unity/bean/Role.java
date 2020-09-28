package com.Panacea.unity.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 角色表
 * @author 夜未
 * @since 2020年9月17日
 */
@Table(name="t_role")
@Data
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1553766952050256417L;
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id; // 编号
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    private String description; // 角色描述,UI界面显示使用
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户

    //角色 -- 权限关系：多对多关系;
    private List<Permission> permissions;

    // 用户 - 角色关系定义;
    private List<User> userInfos;// 一个角色对应多个用户
}
