package com.stu.disruptor.withnetty.common;

import com.lmax.disruptor.WorkHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

/**
 * @Author: dushiyu
 * @Date: 2019-11-06 15:31
 * @Version 1.0
 */
@Data
public class TranslatorDataMapper {

    private TranslatorData translatorData;

    private ChannelHandlerContext context;

}
