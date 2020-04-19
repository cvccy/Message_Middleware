package com.cvccy.direct_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create by cvccy on 2020/04/19
 */
public class DirectExchangeProduct {

    public static void main(String[] args) throws IOException, TimeoutException {

        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("cvccy.com");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("cvccy");
        connectionFactory.setUsername("cvccy");
        connectionFactory.setPassword("kdfj&*^fhew");

        //2.创建连接
        Connection connection = connectionFactory.newConnection();

        //3.创建channel
        Channel channel = connection.createChannel();

        //4.定义交换机名称
        String exchangeName = "cvccy.directchange";

        //5.定义routing key
        String routingKey = "cvccy.directchange.key1024";

        //6.消息内容

        String messageBody = "hello cvccy";
        channel.basicPublish(exchangeName,routingKey,null,messageBody.getBytes());

        channel.close();
        connection.close();
    }
}
