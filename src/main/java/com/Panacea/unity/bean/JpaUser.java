package com.Panacea.unity.bean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 测试JPA的实体类，JPA只需要创建好数据库，表会自动生成，生成表的策略可以配置
 * @author 夜未
 * @since 2020年12月11日
 */
@Data
@Entity
@Table(name = "jpa_user")//表名称，会自动生成这个名字的表
public class JpaUser {

	
	
	
	/**	设置主键生成策略，不设置的话，数据库会自动生成一个hibernate_sequence表来记录主键
	 	JPA提供的四种标准用法为TABLE，SEQUENCE，IDENTITY，AUTO。 
		1，TABLE：使用一个特定的数据库表格来保存主键。  
		2，SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。  
		3，IDENTITY：主键由数据库自动生成（主要是自动增长型）  
		4，AUTO：主键由程序控制。  
	 */
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	
	/**
	 * nullable=false是这个字段在保存时必需有值，不能还是null值就调用save去保存入库;
	   unique=true是指这个字段的值在这张表里不能重复，所有记录值都要唯一，就像主键那样;
	   字段这里是userName命名的话，到数据库里会自动生成字段为user_name
	   如果字段设置了不重复，插入重复信息的话会抛异常
	 */
    @Column(nullable = false, unique = true)
    private String userName;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private int age;
    
    
}
