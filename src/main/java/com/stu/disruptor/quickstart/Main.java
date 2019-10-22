package com.stu.disruptor.quickstart;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: dushiyu
 * @Date: 2019-10-21 12:53
 * @Version 1.0
 */
public class Main {

    public static void main(String[] args) {
        OrderEventFactory orderEventFactory = new OrderEventFactory();
        int rangBufferSize = 1024 * 1024;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        /**
         * 参数说明
         * 1.eventFactory ： 消息工厂对象
         * 2.rangBufferSize：容器的长度
         * 3.executor ： 线程池（建议使用自定义线程池）并且使用这个 RejectedExecutionHandler
         * 4.ProducerType ：单生产者 还是 多生产者
         * 5.WaitStrategy ： 等待策略
         */
        //1.初始化 Disruptor
        Disruptor<OrderEvent> disruptor = new Disruptor<>(orderEventFactory,
                rangBufferSize,
                executor,
                ProducerType.SINGLE,
                new BlockingWaitStrategy());
        //2.添加消费者的监听（Disruptor 与消费者的关联关系）
        OrderEventHandler orderEventHandler = new OrderEventHandler();
        disruptor.handleEventsWith(orderEventHandler);
        //3.直接启动Disruptor
        disruptor.start();
        //4.获取实际存储对象的容器
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        OrderEventProducer orderEventProducer = new OrderEventProducer(ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);

        for (int i = 0; i < 100; i++) {
            bb.putLong(0, i);
            orderEventProducer.sendData(bb);
        }
        disruptor.shutdown();
        executor.shutdown();
    }
}
