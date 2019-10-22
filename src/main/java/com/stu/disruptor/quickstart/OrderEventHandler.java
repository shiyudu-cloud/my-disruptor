package com.stu.disruptor.quickstart;


import com.lmax.disruptor.EventHandler;

/**
 * @Author: dushiyu
 * @Date: 2019-10-21 12:50
 * @Version 1.0
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        System.err.println("消费者 "+orderEvent.getValue());
    }
}
