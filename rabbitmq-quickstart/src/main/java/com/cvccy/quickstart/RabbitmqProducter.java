package com.cvccy.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by cvccy on 2020/4/18.
 */
public class RabbitmqProducter {

    public static void main(String[] args) throws IOException, TimeoutException {
        //1:创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        //2设置连接工厂的属性
        connectionFactory.setHost("cvccy.com");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("cvccy");
        connectionFactory.setUsername("cvccy");
        connectionFactory.setPassword("********");

        //3:通过连接工厂创建连接对象
        Connection connection = connectionFactory.newConnection();

        //4:通过连接创建channel
        Channel channel = connection.createChannel();

        //5:通过channel发送消息
        for(int i=0;i<5;i++) {
            String message = "hello cvccy--"+i;
            /**
             * 消息会发送的exchange上，
             * 但是在这里没有指定交换机?那消息发送到哪里了？
             * The default exchange is implicitly bound to every queue, with a routing key equal to the queue name.
             * It is not possible to explicitly bind to, or unbind from the default exchange. It also cannot be deleted.
             * 说明:假如消息发送的时候没有指定具体的交换机的话，那么就会发送到rabbimtq指定默认的交换机上，
             * 那么该交换机就会去根据routing_key查找对应的queueName 然后发送的该队列上.
             *
             */
            channel.basicPublish("","cvccy-queue-01",null,message.getBytes());
        }

        //6:关闭连接
        channel.close();
        connection.close();
    }
}
