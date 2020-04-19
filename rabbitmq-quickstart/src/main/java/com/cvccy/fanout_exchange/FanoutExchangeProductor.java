package com.cvccy.fanout_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FanoutExchangeProductor {

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
        String exchangeName = "cvccy.fanoutExchange";

        //定义routingKey
        String routingKey = "";

        //消息体内容
        String messageBody = "hello cvccy";

        channel.basicPublish(exchangeName,"123",null,"This is first message".getBytes());
        channel.basicPublish(exchangeName,"456",null,"This is second message".getBytes());
        channel.basicPublish(exchangeName,"789",null,"This is threed message".getBytes());

        channel.close();
        connection.close();



    }

}
