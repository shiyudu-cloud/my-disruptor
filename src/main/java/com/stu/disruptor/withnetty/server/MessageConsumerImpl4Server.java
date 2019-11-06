package com.stu.disruptor.withnetty.server;

import com.stu.disruptor.withnetty.common.TranslatorData;
import com.stu.disruptor.withnetty.common.TranslatorDataMapper;
import com.stu.disruptor.withnetty.common.disruptor.MessageConsumer;

/**
 * @Author: dushiyu
 * @Date: 2019-11-06 16:17
 * @Version 1.0
 */
public class MessageConsumerImpl4Server extends MessageConsumer {


    public MessageConsumerImpl4Server(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataMapper event) throws Exception {
        TranslatorData translatorData = event.getTranslatorData();
        System.out.println(translatorData.toString());
        event.getContext().writeAndFlush(translatorData);
    }
}
