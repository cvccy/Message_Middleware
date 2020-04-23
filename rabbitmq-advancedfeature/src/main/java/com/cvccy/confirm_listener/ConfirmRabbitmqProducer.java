package com.cvccy.confirm_listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class ConfirmRabbitmqProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("cvccy.com");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("cvccy");
        connectionFactory.setUsername("cvccy");
        connectionFactory.setPassword("********");
        connectionFactory.setConnectionTimeout(100000);


        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        //设置消息投递模式(确认模式)
        channel.confirmSelect();

        String exchangeName = "cvccy.confirm.topicexchange";
        String routingKey = "cvccy.confirm.key";

        Map<String,Object> cvccyInfo = new HashMap<>();
        cvccyInfo.put("company","cvccy");
        cvccyInfo.put("location","baijing");

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .correlationId(UUID.randomUUID().toString())
                .timestamp(new Date())
                .headers(cvccyInfo)
                .build();

        String msgContext = "你好 cvccy....";
        /**
         * 消息确认监听
         */
        channel.addConfirmListener(new CvccyConfirmListener());

        channel.basicPublish(exchangeName,routingKey,basicProperties,msgContext.getBytes());


        /**
         * 注意:在这里千万不能调用channel.close不然 消费就不能接受确认了
         */




    }
}
