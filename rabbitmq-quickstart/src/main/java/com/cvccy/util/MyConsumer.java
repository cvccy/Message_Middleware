package com.cvccy.util;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class MyConsumer extends DefaultConsumer {
    public MyConsumer(Channel channel) {
        super(channel);
    }


    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        //consumerTag: 内部生成的消费标签  properties: 消息属性  body: 消息内容
        System.out.println("-----------consume message----------");
        //System.out.println("consumerTag: " + consumerTag+"|||envelope: " + envelope+"||properties: " + properties);
        //envelope包含属性：deliveryTag(标签), redeliver, exchange, routingKey
        //redeliver是一个标记，如果设为true，表示消息之前可能已经投递过了，现在是重新投递消息到监听队列的消费者
        System.out.println("body: " + new String(body));
    }
}
