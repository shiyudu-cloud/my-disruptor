package com.stu.disruptor.withnetty.common.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import com.stu.disruptor.multi.Order;
import com.stu.disruptor.withnetty.common.TranslatorData;
import com.stu.disruptor.withnetty.common.TranslatorDataMapper;
import org.springframework.util.ObjectUtils;
import sun.plugin2.message.Message;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

/**
 * @Author: dushiyu
 * @Date: 2019-11-06 15:20
 * @Version 1.0
 */
public class RingBufferWorkerPoolFactory {

    private static final RingBufferWorkerPoolFactory INSTANCE = new RingBufferWorkerPoolFactory();

    private RingBufferWorkerPoolFactory(){}

    public static RingBufferWorkerPoolFactory getInstance(){
        return INSTANCE;
    }

    private static Map<String,MessageProducer> producers = new ConcurrentHashMap<>();
    private static Map<String,MessageConsumer> consumers = new ConcurrentHashMap<>();

    public static RingBuffer<TranslatorDataMapper> ringBuffer ;

    private SequenceBarrier sequenceBarrier;

    private WorkerPool<TranslatorDataMapper> workerPool;

    public void initAndStart(ProducerType type, int bufferSize, WaitStrategy waitStrategy, MessageConsumer[] messageConsumers){
        ringBuffer = RingBuffer.create(type,
                () -> new TranslatorDataMapper(),
                bufferSize,
                waitStrategy);
        sequenceBarrier = ringBuffer.newBarrier();
        workerPool = new WorkerPool<TranslatorDataMapper>(ringBuffer,
                sequenceBarrier,
                new MyWorkPoolExceptionHandler(),
                messageConsumers
                );

        //将消费者放到池中
        for (MessageConsumer messageConsumer : messageConsumers) {
            consumers.put(messageConsumer.getConsumerId(),messageConsumer);
        }

        ringBuffer.addGatingSequences(this.workerPool.getWorkerSequences());

        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() /2 ));
    }

    public MessageProducer getMessageProducer(String producerId){
        MessageProducer messageProducer = producers.get(producerId);
        if(ObjectUtils.isEmpty(messageProducer)){
            messageProducer = new MessageProducer(ringBuffer, UUID.randomUUID().toString());
            producers.put(producerId,messageProducer);
        }
        return messageProducer;
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
