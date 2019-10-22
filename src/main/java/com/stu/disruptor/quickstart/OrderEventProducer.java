package com.stu.disruptor.quickstart;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @Author: dushiyu
 * @Date: 2019-10-21 13:33
 * @Version 1.0
 */
public class OrderEventProducer {

    private RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(ByteBuffer bb) {
        //1.生产者发送消息的时候，需要从ringBuffer获取到一个可用的序号
        long sequence = ringBuffer.next();
        try {
            //2.根据这个信号找到具体的"orderEvent"元素 是一个属性没有被赋值的"空对象"
            OrderEvent orderEvent = ringBuffer.get(sequence);
            orderEvent.setValue(String.valueOf(bb.getLong(0)));
        }finally {
            //3.提交/发布 对象
            ringBuffer.publish(sequence);
        }
    }
}
