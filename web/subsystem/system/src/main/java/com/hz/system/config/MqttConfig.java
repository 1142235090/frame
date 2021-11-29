package com.hz.system.config;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname MqttBaseConfig
 * @Date 2021/6/29 16:39
 * @Created by hanzhao
 */
@Configuration
@RefreshScope
public class MqttConfig {

    @Value("${mqtt.host}")
    private String mqttHost;

    @Value("${mqtt.username}")
    private String mqttUserName;

    @Value("${mqtt.clientid}")
    private String clientId;

    @Value("${mqtt.pwd}")
    private String mqttPwd;

    @Value("${mqtt.completionTimeout}")
    private Integer completionTimeout;

    /**
     * 创建队列
     * @return
     */
    @Bean
    public MqttClient mqttClient() throws MqttException {
        //1.初始化mqtt参数
        MqttConnectOptions mOptions = new MqttConnectOptions();
        mOptions.setAutomaticReconnect(true);//断开后，是否自动连接
        mOptions.setCleanSession(false);//是否清空客户端的连接记录。若为true，则断开后，broker将自动清除该客户端连接信息
        mOptions.setConnectionTimeout(completionTimeout);//设置超时时间，单位为秒
        mOptions.setUserName(mqttUserName);//设置用户名。跟Client ID不同。用户名可以看做权限等级
        mOptions.setPassword(mqttPwd.toCharArray());//设置登录密码
        mOptions.setKeepAliveInterval(60);//心跳时间，单位为秒。即多长时间确认一次Client端是否在线
        mOptions.setMaxInflight(10);//允许同时发送几条消息（未收到broker确认信息）
        //2.创建mqtt客户端
        MqttClient client = null;
        try {
            client = new MqttClient("tcp://"+mqttHost, clientId,null);
            client.connect(mOptions);//连接broker
            client.setCallback(mqttCallback);//设置回调
        } catch (MqttException e) {
            e.printStackTrace();
        }
        // 订阅主题
        client.subscribe("test");
        return client;
    }

    /**
     * 接收mqtt消息
     */
    static MqttCallback mqttCallback = new MqttCallback() {
        @Override
        public void connectionLost(Throwable cause) {
            System.out.println("MQTT Lost");
        }
        @Override
        public void messageArrived(String topic, MqttMessage message){
            System.out.println("收到消息！");
            System.out.println(new String(message.getPayload()));
        }
        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            System.out.println("MQTT delivery Complete ");
        }
    };
}
