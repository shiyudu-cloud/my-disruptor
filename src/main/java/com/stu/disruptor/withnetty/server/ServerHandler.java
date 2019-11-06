package com.stu.disruptor.withnetty.server;

import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import com.stu.disruptor.withnetty.common.TranslatorData;
import com.stu.disruptor.withnetty.common.disruptor.MessageProducer;
import com.stu.disruptor.withnetty.common.disruptor.RingBufferWorkerPoolFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: dushiyu
 * @Date: 2019-10-24 15:37
 * @Version 1.0
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TranslatorData request = (TranslatorData) msg;
//
//        System.out.println("Server : "+ request.toString());
//
//        TranslatorData translatorData = new TranslatorData();
//        translatorData.setId("resq : "+ request.getId());
//        translatorData.setName("resq :" + request.getName());
//        translatorData.setMessage("resq :" + request.getMessage());
//
//        //返回对象
//        ctx.writeAndFlush(translatorData);
//        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,1024*1024,new YieldingWaitStrategy(),);
        //自己的服务应该有自己的ID生成规则
        String producerId = "sessionId:001";
        MessageProducer messageProducer = RingBufferWorkerPoolFactory.getInstance().getMessageProducer(producerId);
        messageProducer.sendData(request,ctx);
    }
}
