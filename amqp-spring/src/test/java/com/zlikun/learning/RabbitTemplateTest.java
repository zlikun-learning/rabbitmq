package com.zlikun.learning;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-02-05 15:36
 */
public class RabbitTemplateTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Before
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        rabbitTemplate = context.getBean(RabbitTemplate.class);
    }

    @Test
    public void test() throws InterruptedException {
        Message message = MessageBuilder.withBody("Hello RabbitMQ !".getBytes())
                .build();
        // rabbitTemplate.convertAndSend("", message);
        rabbitTemplate.send("", message);
    }

    @After
    public void destroy() {

    }

}
