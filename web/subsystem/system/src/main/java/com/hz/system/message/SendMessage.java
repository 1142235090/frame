package com.hz.system.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @explain
 * @Classname SendMessage
 * @Date 2021/11/26 16:49
 * @Created by hanzhao
 */
@Component
public class SendMessage implements CommandLineRunner {

    private int index = 0;

    private RabbitTemplate rabbitTemplate;

    public SendMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws InterruptedException {
        //测试使用代码
        Boolean where = false;
        while (where) {
            System.out.println("Sending message...");
            try {
                rabbitTemplate.convertAndSend("test_exchange", "", "发送消息" + index);
            } catch (Exception e) {
                e.printStackTrace();
            }
            index++;
            Thread.sleep(1000);
        }
    }
}
