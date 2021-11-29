package com.hz.system.message;

import com.rabbitmq.client.Channel;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @explain
 * @Classname ReceiveMessage
 * @Date 2021/11/26 16:49
 * @Created by hanzhao
 */
@Component
public class ReceiveMessage {

    @Autowired
    private MqttClient mqttClient;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "test_queue", durable = "true"),
            exchange = @Exchange(name = "test_exchange", type = "fanout"),
            key = ""))
    @RabbitHandler
    private void receive1(Message message, Channel channel) {
        //消费者操作
        try {
            receiveMessage(new String(message.getBody()));
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 正常消费队列，第二个参数的意思是小于该ack的消息是否等待全部完成后再一次性签收
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);// 将消息重新放回队列
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveMessage(String message) {
        // 1.接收AMQP消息
        Thread t = Thread.currentThread();
        String name = t.getName();
        System.out.println("name=" + name);
        System.out.println("-------------------接收消息-------------------");
        System.out.println(message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 2.发送mqtt 消息
        String mqttTopic = "test";
        MqttMessage msg = new MqttMessage();
        String msgStr = message;
        msg.setPayload(msgStr.getBytes());//设置消息内容
        msg.setQos(0);//设置消息发送质量，可为0,1,2.
        msg.setRetained(false);//服务器是否保存最后一条消息，若保存，client再次上线时，将再次受到上次发送的最后一条消息。
        try {
            mqttClient.publish(mqttTopic, msg);//设置消息的topic，并发送
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
