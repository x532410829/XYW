package com.Panacea.unity.config;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
/**
 * 通用mapper的配置类
 * @author 夜未
 * @since 2020年9月10日
 * @param <T>
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
	
}