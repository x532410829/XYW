package com.Panacea.unity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Panacea.unity.bean.Role;
import com.Panacea.unity.dao.RoleMapper;
import com.Panacea.unity.service.IRoleService;

/**
 * 角色service
 * @author 夜未
 * @since 2020年9月25日
 */
@Service
public class RoleServiceImpl extends BaseService<Role> implements IRoleService{
	
	@Autowired
	private  RoleMapper roleMapper;
	

}
