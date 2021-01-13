package com.Panacea.unity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

import lombok.Data;
import net.bytebuddy.implementation.bytecode.Throw;

/**
 * Minio 配置文件,设置访问地址，账号密码，存储桶名称
 * @author 夜未
 * @since 2021年1月11日
 */
@Data
public class MinioConfig {
	
	//下面静态变量配置需要用到的属性
	
	/** minio服务的访问路径和端口*/
	public static final String root_endpoint= "http://127.0.0.1:8009";
	//账号密码（可以改成密钥，安全性比较高，修改在minio的配置文件config.json里面修改）
	public static final String root_accessKey= "minioadmin";
	public static final String root_secretKey= "minioadmin";
	
	//每个储存仓库bucket都设置一个自己对应的变量
	
	/** 图片bucket 的名称*/
	public static final String pic_bucket= "picture";
	/**访问时需要用到的路径*/
	public static final String pic_gateway= root_endpoint+"/"+pic_bucket;

	
	
	/** 视频bucket 的名称*/
	public static final String video_bucket= "video";
	/**访问时需要用到的路径*/
	public static final String video_gateway= root_endpoint+"/"+video_bucket;
	
	
	
	//为了方便使用，这里创建一个封装用的属性，然后创建按类型或自定义属性进行封装的构造方法
	private String endpoint;
	private String accessKey;
	private String secretKey;
	private String bucket;
	private String gateway;
	
	//构造客户端配置的类型
	/**图片的仓库*/
	public static final int type_pic=1;
	/**视频的仓库*/
	public static final int type_video=2;
	
	public MinioConfig(String endpoint, String accessKey, String secretKey, String bucket, String gateway) {
		super();
		this.endpoint = endpoint;
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.bucket = bucket;
		this.gateway = gateway;
	}
	
	public MinioConfig(int type) {
		super();
		switch (type) {
		
		case type_pic://图片仓库
			this.endpoint = root_endpoint;
			this.accessKey = root_accessKey;
			this.secretKey = root_secretKey;
			this.bucket = pic_bucket;
			this.gateway = pic_gateway;
			break;
		case type_video://视频仓库
			this.endpoint = root_endpoint;
			this.accessKey = root_accessKey;
			this.secretKey = root_secretKey;
			this.bucket = video_bucket;
			this.gateway = video_gateway;
			break;
		default:
			throw new RuntimeException("参数错误");
		}

	}
	

}
