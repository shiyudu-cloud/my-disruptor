package com.stu.disruptor.withnetty.server;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import com.stu.disruptor.withnetty.common.codec.MarshallingCodeCFactory;
import com.stu.disruptor.withnetty.common.disruptor.MessageConsumer;
import com.stu.disruptor.withnetty.common.disruptor.RingBufferWorkerPoolFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author: dushiyu
 * @Date: 2019-10-24 14:04
 * @Version 1.0
 */
public class NettyServer {

    public NettyServer() {
        //1.创建两个工作线程组 一个用于网络连接接受 另一个实际处理业务线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //表示缓存区自适应
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    //缓冲区池话操作
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    //添加natty 日志
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //回调方法，异步调用 不建议写业务代码
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            socketChannel.pipeline().addLast(new ServerHandler());
                        }
                    });

            //绑定端口 同步等待请求连接

            ChannelFuture channelFuture = serverBootstrap.bind(8765).sync();
            System.out.println(" server start ");
//            channelFuture.channel().close();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        finally {
//            //优雅停机
//            bossGroup.shutdownGracefully();
//            workGroup.shutdownGracefully();
//
//            System.out.println(" server shutdown ");
//        }
    }

    public static void main(String[] args) {
        MessageConsumer[] consumers = new MessageConsumer[4];
        for (int i = 0; i < consumers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Server("code:serverId:"+i);
            consumers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,1024*1024,new BlockingWaitStrategy(),consumers);
        new NettyServer();
    }

}
