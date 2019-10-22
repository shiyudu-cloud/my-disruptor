package com.stu.disruptor.hight;

import com.lmax.disruptor.EventHandler;

import java.util.UUID;

/**
 * @Author: dushiyu
 * @Date: 2019-10-22 15:54
 * @Version 1.0
 */
public class HandlerThree implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handler  3 : NAME :"+
                trade.getName()+
                ", ID:  "+
                trade.getId()+
                ", INSTANCE : "+
                trade.toString());
        Thread.sleep(1000);
    }

}
