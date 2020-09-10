package com.Panacea.unity.service;

import java.util.List;

import com.Panacea.unity.bean.User;
import com.Panacea.unity.bean.vo.UserVo;

/**
 * User 的service
 * @author 夜未
 * @since 2020年9月10日
 */
public interface IUserService extends IService<User> {

	/**
	 * 自定义的service方法
	 * 按时间条件查询user列表
	 * @param userVo
	 * @return
	 */
	List<User> selectByTime(UserVo userVo);

}
