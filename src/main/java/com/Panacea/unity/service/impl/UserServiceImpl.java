package com.Panacea.unity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Panacea.unity.bean.User;
import com.Panacea.unity.bean.vo.UserVo;
import com.Panacea.unity.dao.UserMapper;
import com.Panacea.unity.service.IUserService;
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

	@Override
	public List<User> selectByTime(UserVo userVo) {
		
		//设置分页信息，注意：设置后只对下面代码的第一条查询语句有效，属于一次性的，
		//要注意把这条代码放在要分页查询的语句的前面位置
		PageHelper.startPage(userVo.getPageNum(), userVo.getPageSize(), "create_time DESC");

		List<User>list= userMapper.selectByTime(userVo.getCreateTime());
		
		return list;
	}
	
	
	
	
}
