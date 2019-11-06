package com.stu.disruptor.withnetty.client;

import com.stu.disruptor.withnetty.common.TranslatorData;
import com.stu.disruptor.withnetty.common.disruptor.MessageProducer;
import com.stu.disruptor.withnetty.common.disruptor.RingBufferWorkerPoolFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: dushiyu
 * @Date: 2019-10-24 16:02
 * @Version 1.0
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        try {
//            TranslatorData translatorData =  (TranslatorData) msg;
//
//            System.out.println("接受到的 resp ： "+translatorData.toString());
//        }finally {
//            //一定要注意用完缓存要释放
//            ReferenceCountUtil.release(msg);
//        }

        TranslatorData translatorData =  (TranslatorData) msg;
        String producerId = "code:session:002";
        MessageProducer messageProducer = RingBufferWorkerPoolFactory.getInstance().getMessageProducer(producerId);
        messageProducer.sendData(translatorData,ctx);
    }
}
