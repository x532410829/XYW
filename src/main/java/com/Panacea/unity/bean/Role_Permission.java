package com.Panacea.unity.bean;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 角色-对应-权限表
 * @author 夜未
 * @since 2020年9月17日
 */
@Table(name="t_role_permission")
@Data
public class Role_Permission {

	
    private Integer roleId;//主键.
	
	
	private Integer permissionId;
}
