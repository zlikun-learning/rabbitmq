package com.zlikun.learning.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zlikun.learning.Conf;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-02-05 14:56
 */
public class ProducerTest extends Conf {

    @Test
    public void test() throws IOException, TimeoutException {

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, "direct");

        // 多重绑定，使用routingKey来路由，只能绑定到同个一队列？
        channel.queueBind(QUEUE_NAME, DIRECT_EXCHANGE_NAME, "info");
        channel.queueBind(QUEUE_NAME, DIRECT_EXCHANGE_NAME, "warn");
        channel.queueBind(QUEUE_NAME, DIRECT_EXCHANGE_NAME, "error");

        // 发送多条消息，将根据routingKey自动路由
        channel.basicPublish(DIRECT_EXCHANGE_NAME, "info", null, "info message".getBytes());
        channel.basicPublish(DIRECT_EXCHANGE_NAME, "warn", null, "warn message".getBytes());
        channel.basicPublish(DIRECT_EXCHANGE_NAME, "error", null, "error message".getBytes());

        channel.close();
        connection.close();
    }

}
