package com.zlikun.learning.fanout;

import com.rabbitmq.client.*;
import com.zlikun.learning.Conf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-02-05 14:09
 */
@Slf4j
public class ConsumerTest extends Conf {

    @Test
    public void consume() throws IOException, TimeoutException, InterruptedException {

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, "fanout");

        String queueName = channel.queueDeclare().getQueue();
        log.info("queue name is {}", queueName);

        // 绑定队列名与交换机，无需指定路由键，Fanout模式下，将无条件向广播给所有绑定的队列
        channel.queueBind(queueName, FANOUT_EXCHANGE_NAME, "");

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
