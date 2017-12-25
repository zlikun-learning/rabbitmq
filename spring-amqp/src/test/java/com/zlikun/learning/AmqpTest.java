package com.zlikun.learning;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * http://www.rabbitmq.com/tutorials/tutorial-one-java.html
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2017-12-25 18:24
 */
public class AmqpTest {

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
    public void test() throws Exception {

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();

    }

}
