package com.cvccy.quickstart;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by cvccy on 2020/4/18.
 */
public class RabbitmqConsumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //创建连接工厂
        ConnectionFactory connectionFactory  = new ConnectionFactory();
        connectionFactory.setHost("cvccy.com");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("cvccy");
        connectionFactory.setUsername("cvccy");
        connectionFactory.setPassword("kdfj&*^fhew");

        //创建连接
        Connection connection = connectionFactory.newConnection();

        //创建一个channel
        Channel channel = connection.createChannel();

        //声明队列
        String queueName = "cvccy-queue-01";

        channel.queueDeclare(queueName,true,false,true,null);

        //创建消费者

        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //consumerTag: 内部生成的消费标签  properties: 消息属性  body: 消息内容
                System.out.println("-----------consume message----------");
                System.out.println("consumerTag: " + consumerTag);
                //envelope包含属性：deliveryTag(标签), redeliver, exchange, routingKey
                //redeliver是一个标记，如果设为true，表示消息之前可能已经投递过了，现在是重新投递消息到监听队列的消费者
                System.out.println("envelope: " + envelope);
                System.out.println("properties: " + properties);
                System.out.println("body: " + new String(body));
            }
        });
    }
}
