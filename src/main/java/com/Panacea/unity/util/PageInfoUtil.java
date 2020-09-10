package com.Panacea.unity.util;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class PageInfoUtil  {

	private int pageSize;    //每页显示条数
	private int pageNum;	 //当前页数
	private int pageTotal;		//总条数
	@DateTimeFormat(pattern="yyyy-MM-dd HH-mm-ss")
	private Date startTime;  //开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH-mm-ss")
	private Date endTime;    //结束时间
	private String orderBy;
	
	
}
