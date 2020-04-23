package com.cvccy.return_listner;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class ReturningConsumer extends DefaultConsumer {

    private Channel channel;

    public ReturningConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException
    {

        System.out.println("body:"+new String(body));

        /**
         * multiple:false标识不批量签收
         */
        //channel.basicAck(envelope.getDeliveryTag(),false);
    }

}
