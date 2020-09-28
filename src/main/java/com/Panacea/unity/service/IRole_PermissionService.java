package com.Panacea.unity.service;

import java.util.List;


import com.Panacea.unity.bean.Role_Permission;

/**
 * 角色对应权限的service
 * @author 夜未
 * @since 2020年9月27日
 */
public interface IRole_PermissionService extends IService<Role_Permission>{

	
	List<Role_Permission> selectByRoleId(Integer id);

}
