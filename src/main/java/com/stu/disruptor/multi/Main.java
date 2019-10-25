package com.stu.disruptor.multi;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * @Author: dushiyu
 * @Date: 2019-10-23 14:03
 * @Version 1.0
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        //1.创建多消费者
        RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.MULTI,
                () -> new Order(),
                1024* 1024,
                new YieldingWaitStrategy());
        //2.创建屏障 RingBuffer sequenceBarrier
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        //3.创建多消费者
        Consumer[] consumers = new Consumer[10];
        for (int i = 0; i < 10; i++) {
            consumers[i] = new Consumer("Consumer {"+i+"}");
        }
        //4.创建多消费者消费 workPool

        WorkerPool<Order> workerPool = new WorkerPool(ringBuffer,
                sequenceBarrier,
                new MyWorkPoolExceptionHandler(),
                consumers);

        //5.设置多个消费者的sequence信号 设置消费进度，并设置到ringBuffer中
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        //6.启动workPool
        workerPool.start(Executors.newFixedThreadPool(10));

        //7.

        CountDownLatch countDownLatch  = new CountDownLatch(1);

        for (int i = 0; i < 100; i++) {
            Producer producer = new Producer(ringBuffer);

            new Thread(() -> {

                try {
                    countDownLatch.await();
                }catch (Exception e){

                }
                for (int j = 0; j <100 ; j++) {
                    producer.onData();
                }
            }).start();
        }
        Thread.sleep(2000);

        System.out.println("----------------线程创建完毕 开始生产数据----------------");

        countDownLatch.countDown();

        Thread.sleep(10000);

        System.out.println("第三个消费者处理总数 :"+consumers[2].getCount());
    }

    static class MyWorkPoolExceptionHandler implements ExceptionHandler{

        @Override
        public void handleEventException(Throwable ex, long sequence, Object event) {

        }

        @Override
        public void handleOnStartException(Throwable ex) {

        }

        @Override
        public void handleOnShutdownException(Throwable ex) {

        }
    }
}
