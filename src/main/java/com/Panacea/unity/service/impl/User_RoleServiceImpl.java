package com.Panacea.unity.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Panacea.unity.bean.Role;
import com.Panacea.unity.bean.User_Role;
import com.Panacea.unity.service.IUser_RoleService;

/**
 * 用户关联角色表
 * @author 夜未
 * @since 2020年9月25日
 */
@Service
public class User_RoleServiceImpl extends BaseService<User_Role> implements IUser_RoleService {

	
	
	
	@Override
	public List<User_Role> selectByUserId(Long id) {
		User_Role ur=new User_Role();
		ur.setUserId(id);
		List<User_Role> list=select(ur);
		return list;
	}

}
