package com.cvccy.return_listner;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;

public class ReturningListener implements ReturnListener {

    @Override
    public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException  {
        System.out.println("replyCode:"+replyCode);
        System.out.println("replyText:"+replyText);
        System.out.println("exchange:"+exchange);
        System.out.println("routingKey:"+routingKey);
        System.out.println("properties:"+properties);
        System.out.println("msg body:"+new String(body));
    }
}
