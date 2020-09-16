package com.Panacea.unity.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;

/**
 * 七牛云Demo，这里为全部配置了，修改一下自己的密钥和空间名称、域名就可以了
 * 导包 qiniu-java-sdk
 * @author 夜未
 * @since 2020年9月16日
 */
public class QiNiuYunUtil {
	
	
	//用户的密钥，改成自己的，登录七牛官网在用户个人中心查看得到
	private static String accessKey = "你的accessKey";
	private static String secretKey = "你的secretKey";
	//认证
	private static Auth auth = Auth.create(accessKey, secretKey);

	
	//储存空间名字，改成自己的，就是在七牛创建的空间名称,有多少个都可以全部放在这里
	private final static String bucket0 = "你空间的名称1";//
	private final static String bucket1 = "你空间的名称2";//
	
	

	//外链CDN域名,就是空间绑定的域名，需要自己去绑定域名，默认给的域名只有30天有效
	private final static String domainOfBucket0 = "你空间的名称1对应的域名";
	private final static String domainOfBucket1 = "你空间的名称2对应的域名";

	
	

	/**
	 * 这里的配置注意Region.huanan()指的是创建空间时所选择的归属地区，与之对应即可
	 */
	private static Configuration cfg = new Configuration(Region.huanan());
	//...其他参数参考类注释

	//	UploadManager uploadManager = new UploadManager(cfg);

	/**
	 * 获取上传凭证token
	 * @return
	 */
	private  static  String getToken(String bucket) {
		return auth.uploadToken(bucket);	
	}

	/**获取上传凭证token
	 * 覆盖上传的时候用这个token 
	 * @param bucket 
	 * @param key 要覆盖的文件
	 * @return
	 */
	private  static  String getOverUploadToken(String bucket,String key) {
		return auth.uploadToken(bucket,key);	
	}
	
	/**
	 * 本地上传文件
	 * @param filePath 文件路径
	 * @param fileName 文件的名字，默认不指定key的情况下null，以文件内容的hash值作为文件名；覆盖上传时
	 * 					的文件名称即为要覆盖的文件名称
	 * @param type 空间的标识，就是需要上传到哪个空间，各类型的资源上传到对应的空间
	 * @param isOverwrite 是否覆盖上传
	 * @throws IOException
	 */
	public static MyPutRet upload(String filePath,String fileName,int type,boolean isOverwrite) throws IOException {
        try {
        	String bucket="";
        	String domain="";
        	switch (type) {
			case 0:
				bucket=bucket0;
				domain=domainOfBucket0;
				break;
			case 1:
				bucket=bucket1;
				domain=domainOfBucket1;
				break;
			default:
				break;
			}
        	//设置断点续传文件进度保存目录
        	String localTempDir = Paths.get(System.getenv("java.io.tmpdir"), bucket).toString();
            FileRecorder fileRecorder = new FileRecorder(localTempDir);
            UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
        	String token=""; 
            if(isOverwrite) {//覆盖上传
            	token=getOverUploadToken(bucket, fileName);
            }else {//普通上传
            	token=getToken(bucket);
            }
        	Response response = uploadManager.put(filePath, fileName, token);
            //解析上传成功的结果
        	MyPutRet myPutRet=response.jsonToObject(MyPutRet.class);
        	myPutRet.setCode(response.statusCode);
        	myPutRet.setMsg(response.error);
        	myPutRet.setUrl(domain+File.separator+fileName);//返回文件url路径
            return myPutRet;
        } catch (QiniuException e) {
            Response r = e.response;
//            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            return null;
        }
    }
    
    /**
     * 文件byte[]数据，上传文件
     * @param fileByte 文件的数据
     * @param fileName 文件的名字，默认不指定key的情况下null，以文件内容的hash值作为文件名
     * @param type 空间的标识，就是需要上传到哪个空间，各类型的资源上传到对应的空间
     * @param isOverwrite 是否覆盖上传
     * @throws IOException
     */
    public static MyPutRet upload(byte[] fileByte,String fileName,int type,boolean isOverwrite) throws IOException {
        try {
        	String bucket="";
        	String domain="";
        	switch (type) {
        	case 0:
				bucket=bucket0;
				domain=domainOfBucket0;
				break;
			case 1:
				bucket=bucket1;
				domain=domainOfBucket1;
				break;
			default:
				break;
			}
        	//设置断点续传文件进度保存目录
        	String localTempDir = Paths.get(System.getenv("java.io.tmpdir"), bucket).toString();
            FileRecorder fileRecorder = new FileRecorder(localTempDir);
            UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
        	String token=""; 
            if(isOverwrite) {//覆盖上传
            	token=getOverUploadToken(bucket, fileName);
            }else {//普通上传
            	token=getToken(bucket);
            }
        	Response response = uploadManager.put(fileByte, fileName, token);
            //解析上传成功的结果
        	MyPutRet myPutRet=response.jsonToObject(MyPutRet.class);
        	myPutRet.setCode(response.statusCode);
        	myPutRet.setMsg(response.error);
        	myPutRet.setUrl(domain+File.separator+fileName);//返回文件url路径
        	return myPutRet;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 删除文件
     * @param bucketNum 标识对应的空间名称
     * @param fileName 要删除的文件名称
     * @return 200为成功，其他错误代码去官网查看
     */
    @SuppressWarnings("unused")
    public static int delete(int bucketNum,String fileName) {
    	BucketManager bucketManager = new BucketManager(auth, cfg);
    	try {
    		String bucket="";
    		switch (bucketNum) {
			case 0 : 
				bucket=bucket0;
				break;
			case 1 : 
				bucket=bucket1;
				break;
			default:
				return 300;
			}
		Response response=bucketManager.delete(bucket, fileName);
    	   return 200;
    	} catch (QiniuException ex) {
    		return ex.code();
    	    //如果遇到异常，说明删除失败
    	}
    }
	
	
	
	
	

}
