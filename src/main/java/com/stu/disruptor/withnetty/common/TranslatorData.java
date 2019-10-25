package com.stu.disruptor.withnetty.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: dushiyu
 * @Date: 2019-10-24 14:01
 * @Version 1.0
 */
@Data
public class TranslatorData implements Serializable {

    private String id;
    private String name;
    private String message;
}
