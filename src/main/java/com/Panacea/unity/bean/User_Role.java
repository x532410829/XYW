package com.Panacea.unity.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 用户-对应-角色表
 * @author 夜未
 * @since 2020年9月17日
 */
@Table(name="t_user_role")
@Data
public class User_Role {
	
    private Long userId;
	
	private Integer roleId;

}
