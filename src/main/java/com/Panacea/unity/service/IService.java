package com.Panacea.unity.service;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * 通用service接口类，包含基本的通用mapper操作，和BaseService一起使用，BaseService实现了这里的方法
 * @author 夜未
 * @since 2020年9月10日
 * @param <T>
 */
@Service
public interface IService<T> {

	List<T> selectAll();

	/**
	 * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
	 * @param key
	 * @return
	 */
	T selectByKey(Object key);

	/**
	 * 保存一个实体，null的属性也会保存，不会使用数据库默认值
	 * @param entity
	 * @return
	 */
	int save(T entity);

	/**
	 * 根据主键字段进行删除，方法参数必须包含完整的主键属性
	 * @param key
	 * @return
	 */
	int delete(Object key);
	
	/**
	 * 根据实体属性作为条件进行删除，查询条件使用等号
	 * @param key
	 * @return
	 */
	int deleteByObject(T key);

	int batchDelete(List<String> list, String property, Class<T> clazz);

	/**
	 * 根据主键更新实体全部字段，null值会被更新
	 * @param entity
	 * @return
	 */
	int updateAll(T entity);

	/**
	 * 根据主键更新属性不为null的值
	 * @param entity
	 * @return
	 */
	int updateNotNull(T entity);

	List<T> selectByExample(Object example);
	
	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号
	 * @param entity
	 * @return
	 */
	List<T> select(T entity);

	/**
	 * 保存一个实体，null的属性不会保存，会使用数据库默认值
	 * @param entity
	 * @return
	 */
	int saveSelective(T entity);
}
