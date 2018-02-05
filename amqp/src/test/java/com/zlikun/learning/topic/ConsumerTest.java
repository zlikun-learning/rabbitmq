package com.zlikun.learning.topic;

import com.rabbitmq.client.*;
import com.zlikun.learning.Conf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-02-05 15:12
 */
@Slf4j
public class ConsumerTest extends Conf {

    @Test
    public void consume() throws IOException, TimeoutException, InterruptedException {

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, "topic");

        String queueName = channel.queueDeclare().getQueue();
        // routingKey
        // * 可以匹配一个单词
        // # 可以匹配零个或多个单词
        channel.queueBind(queueName, TOPIC_EXCHANGE_NAME, "*.update");

        while (true) {
            channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    log.info("exchange = {}, routingKey = {}, deliveryTag = {}, consumerTag = {}",
                            envelope.getExchange(), envelope.getRoutingKey(), envelope.getDeliveryTag(), consumerTag);
                    log.info("consume message : {}", new String(body, "UTF-8"));
                }
            });
            TimeUnit.MILLISECONDS.sleep(500);
        }

//        channel.close();
//        connection.close();

    }

}
