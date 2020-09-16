package com.Panacea.unity.util;

import lombok.Data;
/**
 * 七牛云上传后参数返回的接收工具类
 * @author 夜未
 * @since 2020年9月16日
 */
@Data
public class MyPutRet {
	public String key;//上传后的文件名称
    public String hash;//hash吗
    public String bucket;
    public long fsize;
    public int code;
    public String msg;
    public String url;//上传后的访问地址
//    public int width;
//    public int height;
    

}
