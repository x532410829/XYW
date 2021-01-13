package com.Panacea;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**没有这个的话，tomcat就无法启动项目，只能用自带的tomcat启动
 * 使用外部tomcat启动就需要这个(注意yml配置文件可能不生效，端口为tomcat端口，项目路径为项目名)
 * 要修改端口和访问路劲就修改tomcat的配置，server文件夹里面的server.xml
 * @author 夜未
 * @since 2020年12月22日
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PanaceaProjectApplication.class);
	}

}
