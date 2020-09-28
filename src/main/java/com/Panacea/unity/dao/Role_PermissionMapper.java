package com.Panacea.unity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.Panacea.unity.bean.Role_Permission;
import com.Panacea.unity.config.MyMapper;

/**
 * 角色对应权限的信息
 * @author 夜未
 * @since 2020年9月27日
 */
public interface Role_PermissionMapper extends MyMapper<Role_Permission>{

	@Select("SELECT role_id roleId ,permission_id permissionId FROM t_role_permission WHERE role_id=#{id} ")
	List<Role_Permission> selectByRoleId(@Param("id") Integer id);

}
