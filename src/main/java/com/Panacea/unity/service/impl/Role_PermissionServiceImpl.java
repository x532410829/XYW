package com.Panacea.unity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Panacea.unity.bean.Role_Permission;
import com.Panacea.unity.dao.Role_PermissionMapper;
import com.Panacea.unity.service.IRole_PermissionService;

/**
 * 角色对应权限的service
 * @author 夜未
 * @since 2020年9月27日
 */
@Service
public class Role_PermissionServiceImpl extends BaseService<Role_Permission> implements IRole_PermissionService {

	@Autowired
	private Role_PermissionMapper role_PermissionMapper;
	
	/**
	 * 根据角色ID获取所有角色和权限的对应关系
	 */
	@Override
	public List<Role_Permission> selectByRoleId(Integer id) {
		return role_PermissionMapper.selectByRoleId(id);
	}

	
}
