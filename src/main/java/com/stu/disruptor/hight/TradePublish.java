package com.stu.disruptor.hight;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: dushiyu
 * @Date: 2019-10-22 15:40
 * @Version 1.0
 */
public class TradePublish implements Runnable{

    public CountDownLatch countDownLatch;

    public Disruptor<Trade> disruptor;

    public TradePublish(CountDownLatch countDownLatch, Disruptor<Trade> disruptor){
        this.countDownLatch = countDownLatch;
        this.disruptor = disruptor;
    }


    @Override
    public void run() {
        TradeEventTranslator tradeEventTranslator = new TradeEventTranslator();

//        for (int i = 0; i < 10; i++) {
            disruptor.publishEvent(tradeEventTranslator);
//        }
        countDownLatch.countDown();
    }
}

class TradeEventTranslator implements EventTranslator<Trade>{

    @Override
    public void translateTo(Trade trade, long l) {
        trade.setPrice(new Random().nextDouble()*9999);
    }
}
