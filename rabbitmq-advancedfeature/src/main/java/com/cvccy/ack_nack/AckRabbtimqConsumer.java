package com.cvccy.ack_nack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AckRabbtimqConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("cvccy.com");
        connectionFactory.setVirtualHost("cvccy");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("cvccy");
        connectionFactory.setPassword("kdfj&*^fhew");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "cvccy.ack.direct";
        String exchangeType = "direct";
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);

        String queueName = "cvccy.ack.queue";
        channel.queueDeclare(queueName,true,false,false,null);

        String routingKey = "cvccy.ack.key";
        channel.queueBind(queueName,exchangeName,routingKey);

        channel.basicConsume(queueName,false,new AckConsumer(channel));
    }

}
