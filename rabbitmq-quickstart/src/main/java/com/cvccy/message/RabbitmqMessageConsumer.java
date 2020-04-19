package com.cvccy.message;

import com.cvccy.util.MyConsumer;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqMessageConsumer {
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

        String exchangeName = "cvccy.directchange";
        String exchangeType = "direct";
        String queueName = "cvccy.directqueue";
        String routingKey = "cvccy.directchange.key";
        /**
         * 声明一个交换机
         * exchange:交换机的名称
         * type:交换机的类型 常见的有direct,fanout,topic等
         * durable:设置是否持久化。durable设置为true时表示持久化，反之非持久化.持久化可以将交换器存入磁盘，在服务器重启的时候不会丢失相关信息
         * autodelete:设置是否自动删除。autoDelete设置为true时，则表示自动删除。自动删除的前提是至少有一个队列或者交换器与这个交换器绑定，之后，所有与这个交换器绑定的队列或者交换器都与此解绑。
         * 不能错误的理解—当与此交换器连接的客户端都断开连接时，RabbitMq会自动删除本交换器
         * arguments:其它一些结构化的参数，比如：alternate-exchange
         */
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);

        /**
         * 声明一个队列
         */
        channel.queueDeclare(queueName,true,false,false,null);

        channel.queueBind(queueName,exchangeName,routingKey);

        channel.basicConsume(queueName,true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("-----------consume message----------");

                System.out.println("body: " + new String(body));
            }
        });

    }

}
