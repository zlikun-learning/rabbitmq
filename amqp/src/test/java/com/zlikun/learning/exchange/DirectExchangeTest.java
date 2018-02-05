package com.zlikun.learning.exchange;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zlikun.learning.Conf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-01-02 18:45
 */
@Slf4j
public class DirectExchangeTest extends Conf {

    @Test
    public void test() throws Exception {

        // 获取一个连接
        Connection connection = factory.newConnection();
        // 创建一个信道
        Channel channel = connection.createChannel();

        // 声明交换器，默认：direct 交换器
        AMQP.Exchange.DeclareOk declareOk = channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, "direct", false);
        log.info("exchange : {}", declareOk.toString());

        // 创建纯文本消息
        // 发布消息
        String message = "Hello World!";
        channel.basicPublish(DIRECT_EXCHANGE_NAME, QUEUE_NAME, null, message.getBytes());
        log.info("[x] Sent '{}'", message);

        channel.close();
        connection.close();

    }

}
