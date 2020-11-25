package com.Panacea.unity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger的配置类，
 * @author 夜未
 * @since 2020年11月24日
 */
@Configuration //配置类
@EnableSwagger2// 开启Swagger2的自动配置
public class SwaggerConfig{
	
	
	/**
	 * 配置文件中设置是否开启swagger，按需求设置，比如线上环境就设置false不启用
	 */
	@Value("${swagger.show}")
    private boolean swaggerShow;
	
	/**
	 * 配置docket以配置Swagger具体参数：一个docket一个分组
	 * @return
	 */
	@Bean
    public Docket createRestApi(Environment environment) {
		
        return new Docket(DocumentationType.SWAGGER_2)
        		.enable(swaggerShow) //配置是否启用Swagger，如果是false，在浏览器将无法访问
        		.groupName("Swagger测试用例") // 配置分组
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.Panacea.demo"))
              //为有@Api注解的Controller生成API文档
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //为有@ApiOperation注解的方法生成API文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }
  
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger API文档")
                .description("简介：接口文档及调试工具......")
                .contact("小盆友")
                .version("1.0")
//                .description("版本")
                .build();
    }
	
	
    
    //配置多个分组
    @Bean
    public Docket docket1(){
       return new Docket(DocumentationType.SWAGGER_2).groupName("分组1")
    		   .enable(swaggerShow); //配置是否启用Swagger，如果是false，在浏览器将无法访问;
    }
    @Bean
    public Docket docket2(){
       return new Docket(DocumentationType.SWAGGER_2).groupName("分组2")
    		   .enable(swaggerShow); //配置是否启用Swagger，如果是false，在浏览器将无法访问;
    }


}
