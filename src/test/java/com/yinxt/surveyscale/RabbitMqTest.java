package com.yinxt.surveyscale;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.charset.StandardCharsets;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-05-10 23:49
 */
@Slf4j
@SpringBootTest(classes = {SurveyScaleApplicationTests.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitMqTest {

    @Test
    public void testRabbitMq() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            String mqName = "yinxtMq";
            channel.queueDeclare(mqName, false, false, false, null);
            String message = "我的第一个rabbitMq消息";
            channel.basicPublish("", mqName, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e) {
            log.error("异常");
        }
    }

}
