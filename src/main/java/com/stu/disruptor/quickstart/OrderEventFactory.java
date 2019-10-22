package com.stu.disruptor.quickstart;

import com.lmax.disruptor.EventFactory;

/**
 * @Author: dushiyu
 * @Date: 2019-10-21 12:37
 * @Version 1.0
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {
    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();
    }
}
