package com.Panacea.demo;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Panacea.unity.bean.User;
import com.Panacea.unity.bean.vo.UserVo;
import com.Panacea.unity.config.SwaggerConfig;
import com.Panacea.unity.util.BaseUtil;
import com.Panacea.unity.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * API文档工具Swagger
 * @author 夜未
 * @since 2020年10月14日
 */
@Api(tags="模块控制器的名字",description="controller控制器的介绍..")
@RestController
@RequestMapping("swagger")
public class SwaggerDemo {
	
	//依赖导包，查看pom文件springfox-swagger2和springfox-swagger-ui
	
	//配置swagger的基本设置
	SwaggerConfig conf;
	
	//为实体类或返回的信息类设置swagger识别的注解
	
	
	//开始使用,访问http://localhost:8088/JOJO/swagger-ui.html 就可以看到ui界面了
	
	
	//设置请求路径和请求方式，可以设置多个请求方式
	@RequestMapping(value = "/hi", method = {RequestMethod.GET,RequestMethod.POST})
	@ApiOperation(value = "接口名称,方法的用途、作用",notes="方法的备注说明")
	@ApiImplicitParams(//参数列表，多个参数时就有多个@ApiImplicitParam
		{@ApiImplicitParam(name="name", value="参数的说明、解释，name要和参数名字一样",required=true)}//required=true 表示必填
			)
	public String swagger(String name) {
		return "hi,"+name;
	}
	
	
	/**
	 * 参数为实体类的时候，swagger显示的是实体类的所有字段
	 */
	@RequestMapping(value = "/user", method = {RequestMethod.POST})
	@ApiOperation(value = "接口名称,方法的用途、作用",notes="方法的备注说明")
	@ResponseBody
	public Result swagger( @RequestBody UserVo user) {
		
		return BaseUtil.reFruitBean("返回的信息", Result.SUCCESS, user);
	}

}
