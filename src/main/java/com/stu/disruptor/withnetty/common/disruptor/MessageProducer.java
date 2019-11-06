package com.stu.disruptor.withnetty.common.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.stu.disruptor.withnetty.common.TranslatorData;
import com.stu.disruptor.withnetty.common.TranslatorDataMapper;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

/**
 * @Author: dushiyu
 * @Date: 2019-11-06 15:23
 * @Version 1.0
 */
@Data
public class MessageProducer {

    private String producerId;

    private RingBuffer<TranslatorDataMapper> ringBuffer;

    public MessageProducer(RingBuffer<TranslatorDataMapper> ringBuffer,String producerId) {
        this.producerId = producerId;
        this.ringBuffer = ringBuffer;
    }

    public void sendData(TranslatorData translatorData, ChannelHandlerContext context){
        long next = ringBuffer.next();
        TranslatorDataMapper translatorDataMapper = ringBuffer.get(next);
        translatorDataMapper.setTranslatorData(translatorData);
        translatorDataMapper.setContext(context);
        ringBuffer.publish(next);
    }
}
