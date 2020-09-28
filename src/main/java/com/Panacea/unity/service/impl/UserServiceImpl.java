package com.Panacea.unity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Panacea.unity.bean.Permission;
import com.Panacea.unity.bean.Role;
import com.Panacea.unity.bean.Role_Permission;
import com.Panacea.unity.bean.User;
import com.Panacea.unity.bean.User_Role;
import com.Panacea.unity.bean.vo.UserVo;
import com.Panacea.unity.dao.UserMapper;
import com.Panacea.unity.service.IPermissionService;
import com.Panacea.unity.service.IRoleService;
import com.Panacea.unity.service.IRole_PermissionService;
import com.Panacea.unity.service.IUserService;
import com.Panacea.unity.service.IUser_RoleService;
import com.github.pagehelper.PageHelper;

/**
 * User的service
 * @author 夜未
 * @since 2020年9月10日
 */
@Service
public class UserServiceImpl extends BaseService<User> implements IUserService{

	@Autowired
	private UserMapper userMapper;

	/**
	 * 用户对应角色信息
	 */
	@Autowired
	private IUser_RoleService user_RoleServiceImpl;
	
	/**
	 * 角色信息
	 */
	@Autowired
	private IRoleService roleServiceImpl;
	
	/**
	 * 权限信息
	 */
	@Autowired
	private IPermissionService permissionServiceImpl;
	
	/**
	 * 角色对应权限列表
	 */
	@Autowired
	private IRole_PermissionService role_PermissionServiceImpl;
	
	@Override
	public List<User> selectByTime(UserVo userVo) {
		
		//设置分页信息，注意：设置后只对下面代码的第一条查询语句有效，属于一次性的，
		//要注意把这条代码放在要分页查询的语句的前面位置
		PageHelper.startPage(userVo.getPageNum(), userVo.getPageSize(), "create_time DESC");

		List<User>list= userMapper.selectByTime(userVo.getCreateTime());
		
		return list;
	}

	/**
	 * 查询用户信息和相关的角色、权限信息
	 */
	@Override
	public User findByUsername(String username) {
		User user= userMapper.selectByUserName(username);
		List<User_Role>URlist=user_RoleServiceImpl.selectByUserId(user.getId());
		List<Role> roles=new ArrayList<Role>();
		for (User_Role user_Role : URlist) {
			Role role=roleServiceImpl.selectByKey(user_Role.getRoleId());
			if(role!=null) {
				//获取角色对应的所有权限
				List<Role_Permission>RPList=role_PermissionServiceImpl.selectByRoleId(role.getId());
				List<Permission>permissions=new ArrayList<Permission>();
				for (Role_Permission rp : RPList) {
					Permission p=permissionServiceImpl.selectByKey(rp.getPermissionId());
					if(p!=null) {
						permissions.add(p);
					}
				}
				role.setPermissions(permissions);
				roles.add(role);
			}
		}
		user.setRoleList(roles);
		return user;
	}
	
	
	
	
}
