package com.zlikun.learning;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-01-02 18:45
 */
@Slf4j
public class DirectExchangeTest {

    private final String HOST = "rabbitmq.zlikun.com";
    private final String QUEUE_NAME = "A.TEST";

    private ConnectionFactory factory;

    @Before
    public void init() {
        factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername("root");
        factory.setPassword("123456");
        factory.setVirtualHost("/");

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

        // 获取一个连接
        Connection connection = factory.newConnection();
        // 创建一个信道
        Channel channel = connection.createChannel();

        // 声明交换器，默认：direct 交换器
        AMQP.Exchange.DeclareOk declareOk = channel.exchangeDeclare("direct-exchange", "direct", false);
        log.info("exchange : {}", declareOk.toString());

        // 创建纯文本消息
        // 发布消息
        String message = "Hello World!";
        channel.basicPublish("direct-exchange", QUEUE_NAME, null, message.getBytes());
        log.info("[x] Sent '{}'", message);

        channel.close();
        connection.close();

    }

}
