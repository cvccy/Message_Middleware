package com.cvccy;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create 2020/04/22
 */
public class ConfirmRabbitmqConsumer {

    public static void main(String[] args) throws TimeoutException, IOException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("cvccy.com");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("cvccy");
        connectionFactory.setUsername("cvccy");
        connectionFactory.setPassword("kdfj&*^fhew");
        connectionFactory.setConnectionTimeout(100000);

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //声明交换机
        String exchangeName = "cvccy.confirm.topicexchange";
        String exchangeType = "topic";
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);

        //声明队列
        String queueName = "cvccy.confirm.queue";
        channel.queueDeclare(queueName,true,false,false,null);

        //交换机绑定队列
        String routingKey = "cvccy.confirm.#";
        channel.queueBind(queueName,exchangeName,routingKey);

        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException{
                System.out.println("-----------consume message----------");
                System.out.println("body: " + new String(body));
            }
        });





    }





}
