package com.cvccy.topic_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicExchangeProductor {

    public static void main(String[] args) throws IOException, TimeoutException {


        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("cvccy.com");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("cvccy");
        connectionFactory.setUsername("cvccy");
        connectionFactory.setPassword("kdfj&*^fhew");

        //创建连接
        Connection connection = connectionFactory.newConnection();

        //创建channel
        Channel channel = connection.createChannel();


        //定义交换机名称
        String exchangeName = "cvccy.topicExchange";

        //定义routingKey
        String routingKey01 = "cvccy.key01";
        String routingKey02 = "cvccy.key02";
        String routingKey03 = "cvccy.key03";

        //消息体内容
        String messageBody = "hello cvccy";

        channel.basicPublish(exchangeName,routingKey01,null,"This is first message".getBytes());
        channel.basicPublish(exchangeName,routingKey02,null,"This is second message".getBytes());
        channel.basicPublish(exchangeName,routingKey03,null,"This is threed message".getBytes());

        channel.close();
        connection.close();



    }

}
