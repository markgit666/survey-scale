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
import springfox.documentation.service.Tag;
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
                .tags(new Tag("doctor:", "医生相关API"), getTags())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .enable(swaggerEnable);
    }

    private Tag[] getTags() {
        Tag[] tags = {
                new Tag("examination:", "答题相关API"),
                new Tag("excel:", "excel导出相关API"),
                new Tag("file:", "文件上传下载相关API"),
                new Tag("patient:", "被试者相关API"),
                new Tag("report:", "报告表相关API"),
                new Tag("eligible:", "实验条件相关API"),
                new Tag("scale:", "量表相关API")
        };
        return tags;
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("《量表小助手接口文档》")//标题
                .description("description:帮助医生给被试者做经颅磁刺激后的调查问卷小助手")//描述
                .license("license").licenseUrl("http://www.baidu.com")
                .termsOfServiceUrl("http://www.baidu.com")//（不可见）条款地址，公司内部使用的话不需要配
                .contact(new Contact("殷笑天", "http://www.baidu.com", "943121438@qq.com"))//作者信息
                .version("1.0.0")//版本号
                .build();
    }

}
