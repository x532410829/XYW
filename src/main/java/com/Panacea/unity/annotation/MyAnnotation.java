package com.Panacea.unity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义的注解
 * @author 夜未
 * @since 2020年12月8日
 */
@Target({ElementType.METHOD})//指定为用在方法上的切面
@Retention(RetentionPolicy.RUNTIME)//设置该注解生存期是运行时
public @interface MyAnnotation {

	 String value() default "";//默认
	 int attribute();//自定义注解里可以设置的注解属性，如：@MyAnnotation(attribute=1)
}
