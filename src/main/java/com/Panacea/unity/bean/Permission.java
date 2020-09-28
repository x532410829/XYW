package com.Panacea.unity.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 权限表
 * @author 夜未
 * @since 2020年9月17日
 */
@Table(name="t_permission")
@Data
public class Permission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5349859058941981762L;
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;//主键.
    private String name;//名称.
    
    @Column(columnDefinition="enum('menu','button')")
    private String resourceType;//资源类型，[menu|button]
    
    private String url;//资源路径.
    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private Integer parentId; //父编号
    private String parentIds; //父编号列表
    private Boolean available = Boolean.FALSE;
    private List<Role> roles;
	
}
