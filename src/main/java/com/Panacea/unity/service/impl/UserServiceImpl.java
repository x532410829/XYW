package com.Panacea.unity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.IIOException;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.Panacea.unity.bean.JpaUser;
import com.Panacea.unity.bean.Permission;
import com.Panacea.unity.bean.Role;
import com.Panacea.unity.bean.Role_Permission;
import com.Panacea.unity.bean.User;
import com.Panacea.unity.bean.User_Role;
import com.Panacea.unity.bean.vo.UserVo;
import com.Panacea.unity.dao.UserMapper;
import com.Panacea.unity.dao.UserRepository;
import com.Panacea.unity.service.IPermissionService;
import com.Panacea.unity.service.IRoleService;
import com.Panacea.unity.service.IRole_PermissionService;
import com.Panacea.unity.service.IUserService;
import com.Panacea.unity.service.IUser_RoleService;
import com.Panacea.unity.util.EncryptUtil;
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
	
	@Autowired
	private UserRepository userRepository;
	

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
	
	
	/**
	 * 修改用户信息
	 */
	@Override
	public void updateUser(User user) {
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public void addUser(User user) {
		user.setCreateTime(new Date());
		user.setState((byte) 0);
		String salt=new SecureRandomNumberGenerator().nextBytes().toHex();
		user.setSalt(salt);
		user.setPassWord(EncryptUtil.encrypt(salt, user.getPassWord()));
		//插入User数据
		userMapper.insert(user);
	}
	
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

	@Override
	public boolean checkUserName(String userName) {
		User user= userMapper.selectByUserName(userName);
		if(user!=null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 事务回滚测试1，模拟发生异常后，所有写入数据库的数据都会回滚
	 */
	@Transactional
	@Override
	public void transactionalTest1(User record) {
		//创建一条数据
		record.setCreateTime(new Date());
		record.setState((byte) 0);
		String salt=new SecureRandomNumberGenerator().nextBytes().toHex();
		record.setSalt(salt);
		record.setPassWord(EncryptUtil.encrypt(salt, record.getPassWord()));
		//插入User数据
		userMapper.insert(record);
		
		//插入JpaUser数据
		JpaUser jUser=new JpaUser();
		jUser.setAge(111);
		jUser.setUserName(record.getUserName());
		jUser.setPassword(record.getPassWord());
		userRepository.save(jUser);
		//模拟这里发生异常
		if(record.getId()!=null) {
		throw new RuntimeException("模拟异常");
	 }
	}

	
	/**
	 * 模拟发生异常，但是被try catch捕获了的情况，需要主动进行回滚
	 */
	@Transactional
	@Override
	public void transactionalTest2(User record) {
		try {

		//创建一条数据
		record.setCreateTime(new Date());
		record.setState((byte) 0);
		String salt=new SecureRandomNumberGenerator().nextBytes().toHex();
		record.setSalt(salt);
		record.setPassWord(EncryptUtil.encrypt(salt, record.getPassWord()));
		//插入User数据
		userMapper.insert(record);

		//模拟发生异常
		if(record.getId()!=null) {
			throw new RuntimeException("模拟异常");
		 }
		} catch (Exception e) {
			e.printStackTrace();
			//捕获异常不做任何处理的话，事务不会回滚，数据正常写入；
			//如果加上下面这句代码的话，就可以主动触发回滚操作
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			//也可以在这自己手动抛一个RuntimeException出去，也可以触发事务回滚
			throw new RuntimeException("catch里面主动抛一个异常，触发事务回滚操作");
		}
	}

	
	
	/**
	 * 测试同一个service没加@Transactional的方法里有多个数据库操作时，发生异常也会全部回滚
	 */
	@Transactional
	@Override
	public void transactionalTest3(User user) {
		
		//多个操作一起
		testAddAanDel(user);
		//模拟发生异常
		if(user.getId()!=null) {
			throw new RuntimeException("模拟异常");
		 }
	}
	/**
	 * 测试方法，多个数据库操作在同一个方法里面
	 * @param user
	 */
	public void testAddAanDel(User user) {
		//添加用户
		addUser(user);
		//修改用户信息
		User user2=new User();
		user2.setId(13L);
		user2.setUserName("小朋友");
		userMapper.updateByPrimaryKeySelective(user2);
		//添加JPA用户信息
		JpaUser jUser=new JpaUser();
		jUser.setAge(111);
		jUser.setUserName(user.getUserName());
		jUser.setPassword(user.getPassWord());
		userRepository.save(jUser);
	}

	/**
	 * 调用另一个带@Transactional的方法，事务回滚，默认事务会传播
	 */
	@Transactional
	@Override
	public void transactionalTest4(User user) {
		
		//多个操作一起
		testAddAanDel2(user);
		//模拟发生异常
		if(user.getId()!=null) {
			throw new RuntimeException("模拟异常");
		 }
	}
	
	@Transactional
	public void testAddAanDel2(User user) {
		testAddAanDel(user);
	}

	
	
	@Transactional(propagation=Propagation.REQUIRES_NEW,//没有事务就创建事务
			readOnly=false,//是否只读
			isolation=Isolation.READ_COMMITTED,//事务隔离级别：读提交
			timeout=5)//超时时间，单位秒
	@Override
	public void transactionalTest5(User record) {
		
	}
	
	
	
}
