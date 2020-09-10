package com.Panacea.unity.util;

import java.io.Serializable;

/**
 * 统一的返回前端数据的封装类
 * @author 夜未
 * @since 2020年9月10日
 */
public class Result implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5520427738956224794L;
	
	/**  成功 **/
	public static final int SUCCESS = 10;
	
	/**  请求地址错误 **/
	public static final int NOT_PARAMETER = 20;
	
	/**  没有权限 **/
	public static final int PERMISSION_DENIED = 30;
	
	/** 传递的参数有误**/
	public static final int PARAMETER_ERROR = 40;
	
	/** 触发了内部条件约束 **/
	public static final int CONDITIONAL_CONSTRAINT = 45;
	
	/** 数据已存在数据库中 **/
	public static final int DATA_EXIST = 46;
	
	/** 数据为空 **/
	public static final int DATA_NULL = 47;
	
	/** 上传文件大小超过上限 **/
	public static final int UPLOADFILE_EXCEEDE_ERROR = 50;
	
	/** 上传文件类型不正确 **/
	public static final int UPLOADFILE_TYPE_ERROR = 51;
	
	/** 登陆超时 **/
	public static final int LOGIN_TIMEOUT = 60;
	
	/**认证失败 **/
	public static final int AUTHENTICATION_FAILED = 70;
	
	/** 未知错误 **/
	public static final int UNKOWN_ERROR = 80;
	

	private int errorType;
	private String errorMessage;
	private Object data;

	public int getErrorType() {
		return errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result [errorType=" + errorType + ", errorMessage=" + errorMessage + ", data=" + data + "]";
	}
	
}
