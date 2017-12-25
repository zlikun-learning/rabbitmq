package com.zlikun.learning;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * http://www.rabbitmq.com/tutorials/tutorial-one-java.html
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2017-12-25 18:24
 */
@Slf4j
public class AmqpTest {

    private final String HOST = "rabbitmq.zlikun.com";
    private final String QUEUE_NAME = "A.TEST";

    private ConnectionFactory factory;

    @Before
    public void init() {
        factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername("root");
        factory.setPassword("123456");

        // 设置自动重连
        factory.setAutomaticRecoveryEnabled(true);
        // 设置每10秒，重试一次
        factory.setNetworkRecoveryInterval(10_000);
        // 设置重新声明交换器，队列等信息
        factory.setTopologyRecoveryEnabled(true);

        // 连接超时时间，单位：毫秒，默认：60秒
        factory.setConnectionTimeout(15_000);
        // 通道RPC调用超时时间，单位：毫秒，默认：10分钟
        factory.setChannelRpcTimeout(120_000);

    }

    @Test
    public void test() throws Exception {

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        log.info("QUEUE NAME is {}", declareOk.getQueue());

        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        log.info("[x] Sent '{}'", message);

        channel.close();
        connection.close();

    }

}
