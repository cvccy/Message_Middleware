package com.cvccy.message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class RabbitmqMessageProducter {

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

        Map<String,Object> headsMap = new HashMap<>();
        headsMap.put("company","cvccy.com");
        headsMap.put("name","cvccy");

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)//2标识持久化消息  1标识 服务重启后 消息不会被持久化
                .expiration("10000")//消息过期 10s
                .contentEncoding("utf-8")
                .correlationId(UUID.randomUUID().toString())
                .headers(headsMap)
                .build();

        //5:通过channel发送消息
        for(int i=0;i<5;i++) {
            String message = "hello--"+i;
            channel.basicPublish("cvccy.directchange","cvccy.directchange.key",basicProperties,message.getBytes());
        }

        //6:关闭连接
        channel.close();
        connection.close();

    }
}
