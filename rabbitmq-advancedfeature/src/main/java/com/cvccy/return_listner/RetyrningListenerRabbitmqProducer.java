package com.cvccy.return_listner;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class RetyrningListenerRabbitmqProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("cvccy.com");
        connectionFactory.setVirtualHost("cvccy");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("cvccy");
        connectionFactory.setPassword("kdfj&*^fhew");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //准备发送消息
        String exchangeName = "cvccy.retrun.direct";
        String okRoutingKey = "cvccy.retrun.key.ok";
        String errorRoutingKey = "cvccy.retrun.key.error";

        /**
         * 设置监听不可达消息
         */
        channel.addReturnListener(new ReturningListener());


        //设置消息属性
        Map<String,Object> cvccyInfo = new HashMap<>();
        cvccyInfo.put("company","hanfeng");
        cvccyInfo.put("location","长沙");

        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .correlationId(UUID.randomUUID().toString())
                .timestamp(new Date())
                .headers(cvccyInfo)
                .build();

        String msgContext = "你好 cvccy...."+System.currentTimeMillis();

        /**
         * 发送消息
         * mandatory:该属性设置为false,那么不可达消息就会被mq broker给删除掉
         *          :true,那么mq会调用我们得retrunListener 来告诉我们业务系统 说该消息
         *          不能成功发送.
         */
        channel.basicPublish(exchangeName,okRoutingKey,true,basicProperties,msgContext.getBytes());


        String errorMsg1 = "你好 cvccy mandotory为false...."+System.currentTimeMillis();

        //错误发送   mandotory为false
        channel.basicPublish(exchangeName,errorRoutingKey,false,basicProperties,errorMsg1.getBytes());

        String errorMsg2 = "你好 cvccy mandotory为true...."+System.currentTimeMillis();

        //错误发送 mandotory 为true
        channel.basicPublish(exchangeName,errorRoutingKey,true,basicProperties,errorMsg2.getBytes());

    }
}
