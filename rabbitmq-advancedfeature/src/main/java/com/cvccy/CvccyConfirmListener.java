package com.cvccy;

import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;

public class CvccyConfirmListener implements ConfirmListener {

    /**
     *
     * @param deliveryTag 唯一消息Id
     * @param multiple:是否批量
     * @throws IOException
     */
    @Override
    public void handleAck(long deliveryTag, boolean multiple) throws IOException {

        System.out.println("当前时间:"+System.currentTimeMillis()+"CvccyConfirmListener handleAck:"+deliveryTag);
    }

    /**
     * 处理异常
     * @param deliveryTag
     * @param multiple
     * @throws IOException
     */
    @Override
    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
        System.out.println("CvccyConfirmListener handleNack:"+deliveryTag);

    }
}
