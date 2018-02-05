package com.zlikun.learning.direct;

import com.rabbitmq.client.*;
import com.zlikun.learning.Conf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-02-05 10:41
 */
@Slf4j
public class ConsumerTest extends Conf {

    /**
     * 独立获取一条消息
     * @throws IOException
     * @throws TimeoutException
     */
    @Test
    public void get() throws IOException, TimeoutException {
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME,
                true, false, false, null);
        log.info("QUEUE NAME is {}", declareOk.getQueue());

        GetResponse response = channel.basicGet(QUEUE_NAME, true);
        log.info("get message : {}", new String (response.getBody()));

        connection.close();
    }

    /**
     * 持续消费消息
     * @throws IOException
     * @throws TimeoutException
     */
    @Test
    public void consume() throws IOException, TimeoutException {

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.basicQos(1);    // 预取1条消息

        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME,
                true, false, false, null);
        log.info("QUEUE NAME is {}", declareOk.getQueue());

        // 订阅队列消息，可以设置为自动应答
        String consumerTag = channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 处理消息
                log.info("[{}] consumer message : {}", consumerTag, new String(body));
                // 手动应答(当 autoAck = false 时使用)
                // channel.basicAck(envelope.getDeliveryTag(), false);
            }

            @Override
            public void handleConsumeOk(String consumerTag) {
                super.handleConsumeOk(consumerTag);
            }
        });
        log.info("consumerTag is {}", consumerTag);

        connection.close();

    }

}
