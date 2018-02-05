package com.zlikun.learning;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * http://www.rabbitmq.com/tutorials/tutorial-one-java.html
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2017-12-25 18:24
 */
@Slf4j
public class AmqpTest extends Conf {

    @Test
    public void test() throws Exception {

        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个频道
        Channel channel = connection.createChannel();

        // 声明交换器，默认：direct 交换器
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME,
                false, false, false, null);
        log.info("QUEUE NAME is {}", declareOk.getQueue());

        // 创建纯文本消息
        // 发布消息
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        log.info("[x] Sent '{}'", message);

        channel.close();
        connection.close();

    }

}
