package com.stu.disruptor.multi;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: dushiyu
 * @Date: 2019-10-22 14:56
 * @Version 1.0
 */
@Data
public class Order {

    private String id;

    private String name;

    private Double price;
}
