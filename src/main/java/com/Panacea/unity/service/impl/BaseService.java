package com.Panacea.unity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.Panacea.unity.service.IService;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
/**
 * 通用mapper的基础service方法类，自己的service继承他就好了
 * @author 夜未
 * @since 2020年9月10日
 * @param <T>
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public abstract class BaseService<T> implements IService<T> {

	@Autowired
	protected Mapper<T> mapper;

	public Mapper<T> getMapper() {
		return mapper;
	}

	@Override
	public List<T> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public T selectByKey(Object key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int save(T entity) {
		return mapper.insert(entity);
	}
	/**
	 * 保存一个实体，null的属性不会保存，会使用数据库默认值
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int saveSelective(T entity) {
		return mapper.insertSelective(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int delete(Object key) {
		return mapper.deleteByPrimaryKey(key);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int deleteByObject(T key) {
		return mapper.delete(key);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int batchDelete(List<String> list, String property, Class<T> clazz) {
		Example example = new Example(clazz);
		example.createCriteria().andIn(property, list);
		return this.mapper.deleteByExample(example);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int updateAll(T entity) {
		return mapper.updateByPrimaryKey(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int updateNotNull(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<T> selectByExample(Object example) {
		return mapper.selectByExample(example);
	}
	
	@Override
	public List<T> select(T entity) {
		return mapper.select(entity);
	}
	
}
