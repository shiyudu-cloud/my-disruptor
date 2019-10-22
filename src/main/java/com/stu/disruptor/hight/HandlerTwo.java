package com.stu.disruptor.hight;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.Random;
import java.util.UUID;

/**
 * @Author: dushiyu
 * @Date: 2019-10-22 15:54
 * @Version 1.0
 */
public class HandlerTwo implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handler  2 : set id");
        trade.setId(UUID.randomUUID().toString());
        Thread.sleep(1000);
    }

}
