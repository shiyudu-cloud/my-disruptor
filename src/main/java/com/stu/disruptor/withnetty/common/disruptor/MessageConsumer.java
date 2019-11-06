package com.stu.disruptor.withnetty.common.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.stu.disruptor.withnetty.common.TranslatorDataMapper;
import lombok.Data;

/**
 * @Author: dushiyu
 * @Date: 2019-11-06 15:23
 * @Version 1.0
 */
@Data
public abstract class MessageConsumer implements WorkHandler<TranslatorDataMapper > {

    private String consumerId;


    public  MessageConsumer (String consumerId){
        this.consumerId =consumerId;
    }
}
