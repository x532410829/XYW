package com.Panacea.unity.bean.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.Panacea.unity.util.PageInfoUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * user类的辅助查询类,继承自定义的分页工具类，传参时就可以带分页的参数了
 * @author 夜未
 * @since 2020年9月10日
 */
@Data
public class UserVo extends PageInfoUtil implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5895988850069492535L;

	private Long id;
	
	private String userName;
	
	private String passWord;

	//如果只是日期，没有时间，可以不加该注解，如果有时间就要加上，不然无法解析或数据库
	//的时间可能会少8小时
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date createTime;
}
