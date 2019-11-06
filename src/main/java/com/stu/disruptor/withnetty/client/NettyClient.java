package com.stu.disruptor.withnetty.client;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import com.stu.disruptor.withnetty.common.TranslatorData;
import com.stu.disruptor.withnetty.common.codec.MarshallingCodeCFactory;
import com.stu.disruptor.withnetty.common.disruptor.MessageConsumer;
import com.stu.disruptor.withnetty.common.disruptor.RingBufferWorkerPoolFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author: dushiyu
 * @Date: 2019-10-24 15:52
 * @Version 1.0
 */
public class NettyClient {

    public static final String host = "127.0.0.1";

    public static final Integer port = 8765;
    //拓展完成的 clannel池 concurrenthashmap
    private Channel channel;

    public NettyClient(){
        this.connect(host,port);
    }

    public void connect(String host,Integer port){
        //创建一个工作线程组 用作实际处理

        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {


        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                //表示缓存区自适应
                .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                //缓冲区池话操作
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                //添加natty 日志
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });

            ChannelFuture connect = bootstrap.connect(host, port).sync();

            this.channel = connect.channel();

//            connect.channel().closeFuture().sync();
        }catch (Exception e){

        }
//        finally {
//            workGroup.shutdownGracefully();
//        }

    }

    public  void sendDate(){
        for (int i = 0; i < 10; i++) {

            TranslatorData translatorData = new TranslatorData();
            translatorData.setId("ID" + i);
            translatorData.setName("name + "+i);
            translatorData.setMessage("message + "+i);
            this.channel.writeAndFlush(translatorData);
        }
    }

    public static void main(String[] args) {
        MessageConsumer[] consumers = new MessageConsumer[4];
        for (int i = 0; i < consumers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Client("code:clientId:"+i);
            consumers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,1024*1024,new BlockingWaitStrategy(),consumers);
        NettyClient nettyClient = new NettyClient();
        nettyClient.sendDate();
    }
}
