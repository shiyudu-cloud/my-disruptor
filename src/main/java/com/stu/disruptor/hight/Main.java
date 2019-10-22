package com.stu.disruptor.hight;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: dushiyu
 * @Date: 2019-10-22 14:55
 * @Version 1.0
 */
public class Main {


    public static void main(String[] args) throws InterruptedException {
        ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(4);

        Disruptor<Trade> disruptor = new Disruptor(() -> new Trade(), 1024 * 1024, r -> {
            Thread thread = new Thread(r);
            return thread;
        }, ProducerType.SINGLE,new BusySpinWaitStrategy());

        
        //串型操作
        /**disruptor.handleEventsWith(new HandlerOne())
                .handleEventsWith(new HandlerTwo())
                .handleEventsWith(new HandlerThree());
         */

        //并行操作 的两种方式
        disruptor.handleEventsWith(new HandlerOne(),new HandlerTwo(),new HandlerThree());
//        disruptor.handleEventsWith(new HandlerTwo());
//        disruptor.handleEventsWith(new HandlerThree());



        //启动disruptor
        RingBuffer<Trade> ringBuffer = disruptor.start();

        CountDownLatch countDownLatch = new CountDownLatch(1);

        EXECUTOR_SERVICE.submit(new TradePublish(countDownLatch, disruptor));

        countDownLatch.await();

        disruptor.shutdown();

        EXECUTOR_SERVICE.shutdown();
    }


}
