package com.stu.disruptor.hight;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @Author: dushiyu
 * @Date: 2019-10-22 15:54
 * @Version 1.0
 */
public class HandlerOne implements EventHandler<Trade> , WorkHandler<Trade> {

    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        this.onEvent(trade);
    }

    @Override
    public void onEvent(Trade trade) throws Exception {
        System.out.println("handler 1 : set name");
        trade.setName("H1");
        Thread.sleep(1000);
    }
}
