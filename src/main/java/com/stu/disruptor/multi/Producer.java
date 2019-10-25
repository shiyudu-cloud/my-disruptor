package com.stu.disruptor.multi;

import com.lmax.disruptor.RingBuffer;

import java.util.UUID;

/**
 * @Author: dushiyu
 * @Date: 2019-10-23 14:55
 * @Version 1.0
 */
public class Producer {

    private RingBuffer<Order> ringBuffer;

    public Producer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData() {
        long next = ringBuffer.next();
        Order order = ringBuffer.get(next);
        order.setId(UUID.randomUUID().toString());
        ringBuffer.publish(next);
    }
}
