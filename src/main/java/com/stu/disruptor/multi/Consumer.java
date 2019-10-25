package com.stu.disruptor.multi;

import com.lmax.disruptor.WorkHandler;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: dushiyu
 * @Date: 2019-10-23 14:10
 * @Version 1.0
 */
public class Consumer implements WorkHandler<Order> {

    private String commandId;

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public Consumer(String commandId) {
        this.commandId = commandId;
    }


    @Override
    public void onEvent(Order event) throws Exception {
        Thread.sleep(1+new Random().nextInt(5));
        System.out.println("当前消费者："+this.commandId+", 消费信息ID："+event.getId());
        atomicInteger.incrementAndGet();
    }

    public int getCount(){
        return atomicInteger.get();
    }
}
