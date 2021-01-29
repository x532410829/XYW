package com.Panacea.demo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Panacea.unity.config.MinioConfig;
import com.Panacea.unity.util.BaseUtil;
import com.Panacea.unity.util.Result;

import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;

/**
 * MinIO是一个开源的对象存储服务。适合于存储大容量非结构化的数据，例如图片、视频、日志文件、备份
 * 数据和容器/虚拟机镜像等，而一个对象文件可以是任意大小，从几kb到最大5T不等。
 * MinIO是一个非常轻量的服务,可以很简单的和其他应用的结合，类似 NodeJS, Redis 或者 MySQL。
 * 开发文档：http://docs.minio.org.cn/docs/
 * @author 夜未
 * @since 2021年1月11日
 */
@RestController
@RequestMapping("minio")
public class MinIODemo {
	
	
	/**
	 * 首先添加pom文件的依赖，只需要一个配置类和添加配置属性到 application.yml(也可以不加，放到配置类里面)
	 * Minio 配置文件
	 */
	private static volatile MinioConfig minioConfig;

	/**  url分隔符*/
	public static final String URI_DELIMITER = "/";

	
	/**
	 * 测试上传文件，返回访问地址（注意：访问的桶bucket需要设置访问权限，在管理页面设置，如设置全部可以访问：prefix=*  读和写）
	 * @param file 文件
	 * @param type 选择对应的bucket
	 * @return
	 */
	 @RequestMapping("upload")
	 @ResponseBody
	public Result upload(MultipartFile file,int type) {
		
		String path=  putObject(file,type);
		 System.out.println("返回路径path:"+path);
		return BaseUtil.reFruitBean("完成", Result.SUCCESS, path);
	}
	
	
	 /**
	  * 文件复制，可以从一个bucket复制到另一个bucket
	  * @param source 原文件的路径加名字：如：/2021/01/11/1234.jpg 在根目录就只有名字
	  * @param targe  需要复制到的新的 路径和名字
	  * @param type 选择对应的bucket
	  * @return
	  */
	 @RequestMapping("copy")
	 @ResponseBody
	public Result copyFile(String source,String targe,int type) {
		
		 copy(source, targe, type);
		return BaseUtil.reFruitBean("完成", Result.SUCCESS, null);
	}
	
	 /**
	  * 删除文件
	  * @param source 需要删除的文件路径和名称 如： 2021/01/11/1234.jpg
	  * @param type 选择对应的bucket
	  * @return
	  */
	 @RequestMapping("delete")
	 @ResponseBody
	public Result deleteFile(String source,int type) {
		
		 delete(source, type);
		return BaseUtil.reFruitBean("删除完成", Result.SUCCESS, null);
	}
	
	
	
	
	
	
	/**
	 * 上传文件
	 * @param multipartFile
	 * @return
	 */
	public static String putObject (MultipartFile multipartFile,int type) {
		return putObject(new MultipartFile[] {multipartFile},type).get(0);
	}
	
	/**
	 * 上传文件
	 * @param multipartFiles
	 * @return
	 */
	public static List<String> putObject(MultipartFile[]multipartFiles,int type) {
		try {
			
			MinioConfig minioConfig = minioConfig(type);

			MinioClient minioClient = MinioClient.builder().endpoint(minioConfig.getEndpoint())
					.credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
					.build();

			List<String> retVal = new LinkedList<>(); 

			String[] folders = getDateFolder();

			for (MultipartFile multipartFile : multipartFiles) {
				
				// UUID重命名
				String fileName = UUID.randomUUID().toString().replace("-", "") + "." + getSuffix(multipartFile.getOriginalFilename());

				//得到路径： 年/月/日/file
				String finalPath = new StringBuilder(String.join(URI_DELIMITER, folders)) 
									.append(URI_DELIMITER)
									.append(fileName).toString();

				ObjectWriteResponse res= minioClient.putObject(PutObjectArgs.builder()
						.stream(multipartFile.getInputStream(), multipartFile.getSize(),PutObjectArgs.MIN_MULTIPART_SIZE)
						.object(finalPath)
						.contentType(multipartFile.getContentType())
						.bucket(minioConfig.getBucket())
						.build());
				System.out.println("返回值："+res.toString());
				retVal.add(gateway(finalPath,type));
			}
			return retVal;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 复制文件
	 * @param source
	 * @param target
	 */
	public static void copy(String source, String target,int type) {
		
		MinioConfig minioConfig = minioConfig(type);
		
		MinioClient minioClient = MinioClient.builder().endpoint(minioConfig.getEndpoint())
				.credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
				.build();
		try {
			minioClient.copyObject(CopyObjectArgs.builder()
									.bucket(minioConfig.getBucket())
									.object(target)
									.source(CopySource.builder()
											.bucket(minioConfig.getBucket())
											.object(source)
											.build())
									.build());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 删除文件
	 * @param object
	 */
	public static void delete (String object,int type) {
		
		MinioConfig minioConfig = minioConfig(type);
		
		MinioClient minioClient = MinioClient.builder().endpoint(minioConfig.getEndpoint())
				.credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
				.build();
		try {
			minioClient.removeObject(RemoveObjectArgs.builder()
					.bucket(minioConfig.getBucket())
					.object(object)
					.build());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取访问网关
	 * @param path
	 * @return
	 */
	protected static String gateway(String path,int type) {
		String gateway = minioConfig(type).getGateway();
		if (!gateway.endsWith(URI_DELIMITER)) {
			gateway += URI_DELIMITER;
		}
		return gateway + path;
	}
	
	/**
	 * 获取文件后缀
	 * @param fileName
	 * @return
	 */
	protected static String getSuffix(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index != -1) {
			String suffix = fileName.substring(index + 1);
			if (!suffix.isEmpty()) {
				return suffix;
			}
		}
		throw new IllegalArgumentException("非法文件名称："  + fileName);
	}

	/**
	 * 获取年月日[2020, 09, 01]
	 * @return
	 */
	protected static String[] getDateFolder() {
		String[] retVal = new String[3];

		LocalDate localDate = LocalDate.now();
		retVal[0] = localDate.getYear() + "";

		int month = localDate.getMonthValue();
		retVal[1] = month < 10 ? "0" + month : month + "";

		int day = localDate.getDayOfMonth();
		retVal[2] = day < 10 ? "0" + day : day + "";

		return retVal;
	}
	
	/**
	 * 获取minio配置
	 * @return
	 */
	protected static MinioConfig minioConfig(int type) {
		if (minioConfig == null) {
			synchronized (MinIODemo.class) {
				if (minioConfig == null) {
					/**
					 * 从IOC中获取到配置项
					 */
//					minioConfig = SpringHelper.applicationContext.getBean(MinioConfig.class);
					minioConfig=new MinioConfig(type);
				}
			}
		}
		return minioConfig;
	}

    
    
    
    
    
    
    
    
    

}
