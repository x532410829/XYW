package com.Panacea.demo;

import javax.annotation.PostConstruct;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * 3种方法设置项目启动完成之后或启动时自动执行指定代码或任务
 * @author 夜未
 * @since 2020年12月8日
 */

//------------------第一种使用：ApplicationRunner--------------------

@Component//需要加上注解@Component，交由spring管理
public class StartTackDemo implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//这边执行启动完成后需要执行的操作
		System.out.println("Spring容器启动完毕,可以在这执行一些异步服务等");
	}
}

//-------------------第二种使用：SmartLifecycle------------------------------------
@Component//需要加上注解@Component，交由spring管理
 class StartTackDemo2 implements SmartLifecycle{

	@Override
	public void start() {
		// 在该方法中启动任务或者其他异步服务
		System.out.println("启动服务。。。");
	}

	@Override
	public void stop() {
		//生命周期结束时调用的方法。只有当 boolean isRunning() 方法返回true 该方法才会被执行，
		//该方法是属于Lifecyle接口的，被SmartLifeCycle作为了一个钩子
		System.out.println("服务***结束了");
	}

	@Override
	public boolean isRunning() {
		//作为信号来判定 start 还是stop
		return false;
	}
}

//-----------------第三种使用：@PostConstruct-------------------------------

/**
 * Java中该注解的说明：@PostConstruct该注解被用来修饰一个非静态的void（）方法。被@PostConstruct修饰
 * 的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行，
 * init（）方法之前执行。
 * 通常我们会是在Spring框架中使用到@PostConstruct注解 该注解的方法在整个Bean初始化中的执行顺序：
 * Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
 */
@Component//需要加上注解@Component，交由spring管理
class StartTackDemo3 {
	
	@PostConstruct
	public void initService() {
		System.out.println("启动服务....");
	}
}








