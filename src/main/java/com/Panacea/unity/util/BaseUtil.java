package com.Panacea.unity.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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
	
	
	/**
	 * 获取唯一的UUID码
	 * 
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String num = uuid.toString();
		num = num.replace("-", "");// 替换掉中间的横杠
		return num;
	}
	
	/**
	 * 获取指定长度的字符数字串
	 * @param length
	 * @return
	 */
	public static String getCharAndNumr(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 字符串
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 取得大写字母还是小写字母
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
	
	/**
	 * 判断值是否为空，为空返回true
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().equals("") || str.equals(null) || str.trim().equals("null");
	}
	
	/**
	 * 根据日期和传入的信息生成订单号
	 * @param hotelkey 如：XYZ
	 * @return
	 */
	public static String setOrderNumber(String hotelkey){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String ordernumber = hotelkey+sdf.format(date)+Long.toString(date.getTime()).substring(9);
		return ordernumber;
	}
	
}
