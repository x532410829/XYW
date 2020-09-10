package com.Panacea.unity.util;

/**
 * 工具类
 * @author 夜未
 * @since 2020年9月10日
 */
public class BaseUtil {
	/**
	 * 返回提示结果
	 * 
	 * @param errorMessage:错误提示
	 * @param errorType：错误类型
	 * @param data:有无返回的实体类
	 * @return
	 */
	public static Result reFruitBean(String errorMessage, int errorType, Object data) {
		Result result = new Result();
		result.setErrorMessage(errorMessage);
		result.setErrorType(errorType);
		if (data != null) {
			result.setData(data);
		}
		return result;
	}
}
