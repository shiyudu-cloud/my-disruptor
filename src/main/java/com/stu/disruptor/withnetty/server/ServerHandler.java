package com.stu.disruptor.withnetty.server;

import com.stu.disruptor.withnetty.common.TranslatorData;
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

        System.out.println("Server : "+ request.toString());

        TranslatorData translatorData = new TranslatorData();
        translatorData.setId("resq : "+ request.getId());
        translatorData.setName("resq :" + request.getName());
        translatorData.setMessage("resq :" + request.getMessage());

        //返回对象
        ctx.writeAndFlush(translatorData);
    }
}
