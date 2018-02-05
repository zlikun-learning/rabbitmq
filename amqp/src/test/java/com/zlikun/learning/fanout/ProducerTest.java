package com.zlikun.learning.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zlikun.learning.Conf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-02-05 13:45
 */
@Slf4j
public class ProducerTest extends Conf {

    @Test
    public void produce() throws IOException, TimeoutException, InterruptedException {

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, "fanout");

        // 向绑定的队列发送消息(如果是后绑定的，无法收到消息)
        channel.basicPublish(FANOUT_EXCHANGE_NAME,
                "", null, "The topic message .".getBytes());

        channel.close();
        connection.close();
    }

}
