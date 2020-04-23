package com.cvccy.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class DlxRabbitmqConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("cvccy.com");
        connectionFactory.setVirtualHost("cvccy");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("cvccy");
        connectionFactory.setPassword("kdfj&*^fhew");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //声明正常的队列
        String nomalExchangeName = "cvccy.nomaldlx.exchange";
        String exchangeType = "topic";
        String nomalqueueName = "cvccy.nomaldex.queue";
        String routingKey = "cvccy.dlx.#";

        //申明死信队列
        String dlxExhcangeName = "cvccy.dlx.exchange";
        String dlxQueueName = "cvccy.dlx.queue";

        channel.exchangeDeclare(nomalExchangeName,exchangeType,true,false,null);


        Map<String,Object> queueArgs = new HashMap<>();
        //正常队列上绑定死信队列
        queueArgs.put("x-dead-letter-exchange",dlxExhcangeName);
        queueArgs.put("x-max-length",4);
        channel.queueDeclare(nomalqueueName,true,false,false,queueArgs);
        channel.queueBind(nomalqueueName,nomalExchangeName,routingKey);



        //声明死信队列
        channel.exchangeDeclare(dlxExhcangeName,exchangeType,true,false,null);
        channel.queueDeclare(dlxQueueName,true,false,false,null);
        channel.queueBind(dlxQueueName,dlxExhcangeName,"#");

        channel.basicConsume(nomalqueueName,false,new DlxConsumer(channel));
    }
}
