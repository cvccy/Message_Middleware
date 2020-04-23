package com.cvccy.consumer_limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QosRabbitmqProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("cvccy.com");
        connectionFactory.setVirtualHost("cvccy");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("cvccy");
        connectionFactory.setPassword("kdfj&*^fhew");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //定义交换机的名称
        String exchangeName = "cvccy.Qos.direct";

        String routingKey = "cvccy.Qos.key";

        String msgBody = "你好hanfeng";

        for(int i=0;i<100;i++) {
            channel.basicPublish(exchangeName,routingKey,null,(msgBody+i).getBytes());
        }



    }
}
