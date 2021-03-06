package com.cvccy.topic_exchange;

import com.cvccy.util.MyConsumer;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Create by cvccy on 2020/04/19
 */
public class TopicExchangeConsumer {
    public static void main(String[] args) throws IOException,InterruptedException, TimeoutException {


        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("cvccy.com");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("cvccy");
        connectionFactory.setUsername("cvccy");
        connectionFactory.setPassword("********");

        //创建连接
        Connection connection = connectionFactory.newConnection();

        //创建channel
        Channel channel = connection.createChannel();

        //声明交换机
        String exchangeName = "cvccy.topicExchange";
        String exchangeType = "topic";
        channel.exchangeDeclare(exchangeName, exchangeType, true, true, null);

        //声明队列
        String queueName = "cvccy.topic.queue";
        channel.queueDeclare(queueName, true, false, false, null);

        //声明绑定关系
        String bingdingStr = "cvccy.*";
        channel.queueBind(queueName, exchangeName, bingdingStr);

        //开始消费
        /**
         * 开始消费
         */
        channel.basicConsume(queueName,true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("-----------consume message----------");
                System.out.println("body: " + new String(body));
            }
        });
    }
}
