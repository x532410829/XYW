package com.Panacea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 万能药项目------Panacea
 * 目的：将所有用过技术，做成单个零件式的demo，以后可以直接复制在修改部分地方即可使用，帮助快速开发
 * 建立项目尽量做到所有代码都带上详细的注释、功能介绍、引用的包、注意事项等
 * 
 * @author 夜未
 * @since 2020年9月9日
 */
@ServletComponentScan//组件扫描
@SpringBootApplication
@EnableScheduling//定时器的注解
//@ComponentScan(basePackages= {"com.Panacea"})
@MapperScan({"com.Panacea.unity.dao","com.Panacea.*.dao"})//通用mapper扫描
public class PanaceaProjectApplication {
	
	
/**
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
 */
	
	public static void main(String[] args) {
		SpringApplication.run(PanaceaProjectApplication.class, args);
		System.out.println("==================================="
						+  "=============启动完成=============="
						+  "===================================");
	}

}
