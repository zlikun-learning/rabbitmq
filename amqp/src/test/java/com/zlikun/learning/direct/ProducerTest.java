package com.zlikun.learning.direct;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.zlikun.learning.Conf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 消息生产者
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-02-05 10:38
 */
@Slf4j
public class ProducerTest extends Conf {

    @Test
    public void produce() throws Exception {

        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个信道
        Channel channel = connection.createChannel();

        // 声明交换器，默认：direct 交换器
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME,
                true, false, false, null);
        log.info("QUEUE NAME is {}", declareOk.getQueue());

        // 创建纯文本消息
        // 发布消息
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        log.info("[x] Sent '{}'", message);

        channel.close();
        connection.close();

    }

}
