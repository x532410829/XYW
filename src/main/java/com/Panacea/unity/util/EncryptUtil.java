package com.Panacea.unity.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
/**
 * 加密工具
 * @author 夜未
 * @since 2020年9月24日
 */
public class EncryptUtil {
	
	//algorithmName 加密类型[MD5、Md2、Sha1、Sha256等]
	public static  String Type_MD5="MD5";
	
	/**
	 * MD5加密
	 * @param salt 盐
	 * @param pswd 密码
	 * @return
	 */
	public static String encrypt(String salt, String pswd) {
		String newPassword = new SimpleHash(Type_MD5, pswd, ByteSource.Util.bytes(salt),2).toHex();
		return newPassword;
	}

}
