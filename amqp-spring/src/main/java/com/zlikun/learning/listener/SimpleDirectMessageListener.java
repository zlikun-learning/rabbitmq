package com.zlikun.learning.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 消息监听器
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-02-05 16:05
 */
@Slf4j
@Component
public class SimpleDirectMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        log.info("receive message : {}", new String(message.getBody()));
    }

}
