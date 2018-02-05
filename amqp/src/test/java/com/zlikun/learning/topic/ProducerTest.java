package com.zlikun.learning.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zlikun.learning.Conf;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-02-05 14:47
 */
public class ProducerTest extends Conf {

    @Test
    public void produce() throws IOException, TimeoutException {

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, "topic");

        channel.basicPublish(TOPIC_EXCHANGE_NAME, "user.update", null, "The topic message !".getBytes());

        channel.close();
        connection.close();
    }

}
