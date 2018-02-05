package com.zlikun.learning.exchange;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zlikun.learning.Conf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-01-02 19:01
 */
@Slf4j
public class TopicExchangeTest extends Conf {

    @Test
    public void test() throws Exception {

        // 获取一个连接
        Connection connection = factory.newConnection();
        // 创建一个信道
        Channel channel = connection.createChannel();

        // 声明交换器
        AMQP.Exchange.DeclareOk declareOk = channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, "topic", false);
        log.info("exchange : {}", declareOk.toString());

        // 发布消息
        String message = "Hello World!";
        channel.basicPublish(TOPIC_EXCHANGE_NAME, "A.*", null, message.getBytes());
        log.info("[x] Sent '{}'", message);

        channel.close();
        connection.close();

    }

}
