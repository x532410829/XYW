package com.Panacea.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.Panacea.PanaceaProjectApplication;

/**
 * 定时任务
 * @author 夜未
 * @since 2020年9月11日
 */
@Component
public class SchedulerTaskDemo {

	
	 /**
	  * 启动类添加注解 @EnableScheduling 就可以了，不需要额外其他配置
	  */
	 PanaceaProjectApplication panaceaProjectApplication;
	
	
	//@Scheduled 参数可以接受两种定时的设置，一种是我们常用的 cron="*/6 * * * * ?"
	//cron表达式可以直接找工具生成，自己写容易弄错
	//一种是 fixedRate = 6000，两种都表示每隔六秒打印一下内容。 
	 
//	 @Scheduled(fixedRate = 6000) ：上一次开始执行时间点之后6秒再执行
//	 @Scheduled(fixedDelay = 6000) ：上一次执行完毕时间点之后6秒再执行
//	 @Scheduled(initialDelay=1000, fixedRate=6000) ：第一次延迟1秒后执行，之后按 fixedRate 的
//	  规则每6秒执行一次
	 
	
//		private int count=0;
//	    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//
//	    @Scheduled(cron="*/6 * * * * ?")
//	    private void process(){
//	        System.out.println("定时器运行："+(count++));
//	    }
//	    
//	    @Scheduled(fixedRate = 6000)
//	    public void reportCurrentTime() {
//	        System.out.println("现在时间：" + dateFormat.format(new Date()));
//	    }
	
	
	
}
