package com.zlikun.learning;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 基准测试
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2017-12-25 18:41
 */
@Slf4j
public class BenchmarkTest {

    private final String HOST = "rabbitmq.zlikun.com";
    private final String QUEUE_NAME = "A.TEST";

    private ConnectionFactory factory;

    @Before
    public void init() {
        factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername("root");
        factory.setPassword("123456");
    }

    @Test
    public void test() throws IOException, TimeoutException {

        long time = System.currentTimeMillis();

        for (int i = 0; i < 100_000; i++) {
            send("测试发送消息到RabbitMQ，编号：" + i);
        }

        log.info("程序执行耗时：{} 毫秒!", System.currentTimeMillis() - time);

    }

    private void send(String message) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

        channel.close();
        connection.close();
    }

    @After
    public void destroy() {

    }

}
