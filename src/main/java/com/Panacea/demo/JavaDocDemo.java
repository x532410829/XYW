package com.Panacea.demo;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Panacea.unity.bean.User;
import com.Panacea.unity.util.Result;

/**
 * 使用JavaDoc 生成Restful web Api文档
 * @author 小盆友
 * @since 2021年3月4日
 */
@RestController
@RequestMapping("javadoc")
public class JavaDocDemo {
	
	
	/**
	 * 无需导包，但是要依赖nodeJS,使用前确保使用npm 安装了javadoc,安装命令：npm install apidoc -g
	 * 在所有接口上加上API文档的注解，自动生成时会识别的，API注解看下面案例怎么写的；
	 * 
	 * 在项目根路径上增加一个配置文件 apidoc.json ,在里面配置API文档的一些信息
	 * 使用cmd命令切换到项目的根目录下，使用命令生成文档: apidoc -i src/ -o doc
	 * 生成成功后会在根目录下生成doc目录，游览器打开里面的index.html就可以访问API文档了
	 */
	
	
	
	/**
	 * @apiGroup 用户管理模块组
	 * @api {post} /javadoc/addUser 用户注册
	 * @apiSampleRequest /javadoc/addUser
	 * @apiDescription 接口描述:用户账号密码注册
	 * @apiParam {String} userName 用户名称 参数
	 * @apiParam {String} nickName 用户昵称
	 * @apiParam {String} passWord 用户密码
	 * @apiSuccess {Object}  data 请求成功返回的数据
	 * @apiSuccess {int(10)} errorType   返回数据结果码
	 * @apiSuccess {String} errorMessage  返回数据信息
	 * 
	 * 返回数据的示例
	 * @apiSuccessExample Success-Response:  
	 *  HTTP/1.1 200 OK
	 * {
	 * errorType:10,
	 * errorMessage:'注册成功',
	 * data:{}
	 *  }
	 */
	@RequestMapping("addUser")
	@ResponseBody
	public Result addUser(@RequestBody User user) {
		return null;
	}
	
	
	/**
	 * 账号密码登录
	 * @apiGroup 用户管理模块组
	 * @api {post} /javadoc/login 账号密码登录
	 * @apiSampleRequest /javadoc/login
	 * @apiDescription 接口描述:用户账号密码登录
	 * @apiParam {String} userName 用户名称
	 * @apiParam {String} passWord 用户密码
	 * @apiSuccess {Object}  data 请求成功返回的数据
	 * @apiSuccess {int(10)} errorType   返回数据结果码
	 * @apiSuccess {String} errorMessage  返回数据信息
	 * 
	 * 返回数据的示例
	 * @apiSuccessExample Success-Response:  
	 *  HTTP/1.1 200 OK
	 * {
	 * errorType:10,
	 * errorMessage:'登录成功',
	 * data:{}
	 *  }
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Result login(@RequestBody User user) {
		return null;
	}

}
