package com.Panacea.unity.service;

import java.util.List;

import com.Panacea.unity.bean.Role;
import com.Panacea.unity.bean.User_Role;

/**
 * 用户对应角色的关联表
 * @author 夜未
 * @since 2020年9月25日
 */
public interface IUser_RoleService extends IService<User_Role>{

	/**
	 * 查询用户ID关联的所有角色
	 * @param id
	 * @return
	 */
	List<User_Role> selectByUserId(Long id);

}
