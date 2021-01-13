package com.Panacea.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.assertj.core.internal.InputStreams;
import org.bouncycastle.jcajce.provider.asymmetric.ec.GMSignatureSpi.sha256WithSM2;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import com.Panacea.unity.util.MyPutRet;
import com.Panacea.unity.util.QiNiuYunUtil;

/**
 * 七牛云demo；由于七牛云的数据都有缓存，所以覆盖上传上去的可能还是看到覆盖前的东西，可以去刷新目录查看
 * 或者自定义好域名后设置不缓存或者短时间缓存
 * @author 夜未
 * @since 2020年9月16日
 */
@SuppressWarnings("all")
public class QiNiuYunDemo {
	
	/**
	 * 上传工具，所有配置都在里面了
	 */
	
	private  QiNiuYunUtil uploadUtil;
	
	/**
	 * 上传本地的文件，名字系统会覆盖
	 */
	@Test
	public void upload() {
		try {
			String url="文件路径";
			MyPutRet myPutRet =QiNiuYunUtil.upload(url, "4英杰.jpg", 1, false);
			System.out.println(myPutRet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取文件字节流再上传
	 */
	@Test
	public void uploadByte() {
		try {
			String url="文件路径";
			File file=new File(url);
			FileInputStream fi=new FileInputStream(file);
			byte[]buffer=new byte[(int)file.length()];
			int offset=0;
			int numRead=0;
			while(offset<buffer.length&&(numRead=fi.read(buffer,offset,buffer.length-offset))>=0){
			offset+=numRead;
			}
			//确保所有数据均被读取
			if(offset!=buffer.length){
			throw new IOException("没有读取完文件："+file.getName());
			}
			fi.close();
			
			MyPutRet myPutRet =QiNiuYunUtil.upload(buffer, "4212.jpg", 0, false);
			System.out.println(myPutRet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 前端上传图片或文件使用MultipartFile来接收
	 * @param files
	 * @throws IOException 
	 */
	 public void webUpload( MultipartFile[] files) throws IOException {
		 for (MultipartFile multipartFile : files) {
			 File.createTempFile("临时文件名字","文件后缀");
		}
	 }
	
	/**
	 * 直接覆盖上传，好像和上面的区别不大（暂时没发现有什么问题）
	 */
	@Test
	public void overUpload() {
		try {
			String url="文件路径";
			MyPutRet myPutRet =QiNiuYunUtil.upload(url, "4英杰.jpg", 1, true);
			System.out.println(myPutRet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除文件，删除后可能还有缓存，一段时间内依然可以访问，但是文件是已经删除了的
	 */
	@Test
	public void delete() {
		String url="文件路径";
		int code =QiNiuYunUtil.delete(1,"4英杰.jpg");
		System.out.println(code);
	}
	
	
	
	

}
