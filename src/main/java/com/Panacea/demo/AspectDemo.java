package com.Panacea.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Panacea.unity.annotation.MyAnnotation;
import com.Panacea.unity.aspect.MyAspect;
import com.Panacea.unity.util.BaseUtil;
import com.Panacea.unity.util.Result;

/**
 * 切面和注解的测试demo
 * @author 夜未
 * @since 2020年12月8日
 */
@RestController
@RequestMapping("aspect")
public class AspectDemo {

	MyAspect myAspect;//自定义的切面，对注解@MyAnnotation生效
	
	/**
	 * 自定义的注解，设置默认属性，MyAspect切面会优先执行
	 * @return
	 */
	@MyAnnotation(attribute=1)
	@RequestMapping("test1")
	@ResponseBody
	public Result test1() {
		System.out.println("执行test");
		return BaseUtil.reFruitBean("成功",Result.SUCCESS, null);
	}
	
	@MyAnnotation(attribute=2)
	@RequestMapping("test2")
	@ResponseBody
	public Result test2() {//调这个接口会提示无权限，不会执行下面的打印语句
		System.out.println("执行test2");
		return BaseUtil.reFruitBean("成功",Result.SUCCESS, null);
	}
	
}
