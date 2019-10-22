package com.stu.disruptor.hight;

import com.lmax.disruptor.EventHandler;

/**
 * @Author: dushiyu
 * @Date: 2019-10-22 15:54
 * @Version 1.0
 */
public class HandlerFour implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handler 1 : set price");
        trade.setPrice(17.0);
        Thread.sleep(1000);
    }

}
