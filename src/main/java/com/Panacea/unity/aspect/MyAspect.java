package com.Panacea.unity.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.Panacea.unity.annotation.MyAnnotation;
import com.Panacea.unity.util.BaseUtil;
import com.Panacea.unity.util.Result;

/**
 * 检测用户是否拥有指定VIP模块的权限
 * @author 夜未
 * @since 2020年7月10日
 */
@Aspect //标注增强处理类（切面类）
@Component //交由Spring容器管理

/**声明组件的顺序，值越小，优先级越高，越先被执行/初始化。
   如果没有该注解，则优先级最低，多个切面的时候可能会用到 */
@Order(1)
public class MyAspect {
	

	//切面环绕通知，用在注解@annotation上，只要有这个注解的方法，都会进入这个切面
	@Around(value = "@annotation(myAnnotation)")//特别注意，这里的myAnnotation必须与下面方法MyAnnotation参数的一样
	public Object checkVipPrivileges( ProceedingJoinPoint p,MyAnnotation myAnnotation) throws Throwable {
		int attribute= myAnnotation.attribute();//可以拿到注解里设置的属性
//		  获取session
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
//                .getRequestAttributes())
//                .getRequest();
//        HttpSession session = request.getSession();
		
		if(attribute==1) {
			System.out.println("有权限");
		}else {
			System.out.println("无权限");
			return BaseUtil.reFruitBean("无权限", Result.PERMISSION_DENIED, null);
		}
		
		//如果没有权限，则return 一个返回的信息，如：
		//return BaseUtil.reFruitBean("无权限", Result.PERMISSION_DENIED, null);
		
        //如果上面逻辑判断有权限，就return 执行 p.proceed()；代码继续执行注解所在的方法
		return  p.proceed();
	}

}
