package com.yinxt.surveyscale.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 * http://localhost:8082/survey/swagger-ui.html#/
 *
 * @author yinxt
 * @version 1.0
 * @date 2020/5/5 15:52
 */
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.yinxt.surveyscale.controller"})
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean swaggerEnable;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .enable(swaggerEnable);
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("《量表小助手接口文档》")//标题
                .description("description:项目摘要")//描述
                .license("license").licenseUrl("http://www.baidu.com")
                .termsOfServiceUrl("http://www.baidu.com")//（不可见）条款地址，公司内部使用的话不需要配
                .contact(new Contact("yinxt", "http://www.baidu.com", "943121438@qq.com"))//作者信息
                .version("1.0.0")//版本号
                .build();
    }

}
