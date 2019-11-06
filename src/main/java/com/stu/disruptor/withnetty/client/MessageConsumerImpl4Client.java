package com.stu.disruptor.withnetty.client;

import com.stu.disruptor.withnetty.common.TranslatorData;
import com.stu.disruptor.withnetty.common.TranslatorDataMapper;
import com.stu.disruptor.withnetty.common.disruptor.MessageConsumer;
import io.netty.util.ReferenceCountUtil;

/**
 * @Author: dushiyu
 * @Date: 2019-11-06 16:17
 * @Version 1.0
 */
public class MessageConsumerImpl4Client extends MessageConsumer {


    public MessageConsumerImpl4Client(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataMapper event) {
        TranslatorData translatorData = event.getTranslatorData();
        try {
            System.out.println(translatorData.toString());
        }finally {
            ReferenceCountUtil.release(translatorData);
        }

    }
}
