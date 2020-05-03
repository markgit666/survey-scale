package com.yinxt.surveyscale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Configurable
@ServletComponentScan
public class SurveyScaleApplicationTests {

    @Test
    public void contextLoads() {
    }

    public static void main(String[] args) {
        SpringApplication.run(SurveyScaleApplicationTests.class, args);
    }


}
