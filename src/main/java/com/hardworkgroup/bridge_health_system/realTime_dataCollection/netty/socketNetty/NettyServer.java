package com.hardworkgroup.bridge_health_system.realTime_dataCollection.netty.socketNetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/*
* Netty服务器端配置
* */
@Slf4j
public class NettyServer implements Runnable{

    public void run(){
        // 创建一个主线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 创建一个工作线程组
        EventLoopGroup workGroup = new NioEventLoopGroup(200);

        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 链式编程
            bootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    // 设置bossGroup处理连接的数组长度
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //childHandler相当于给workGroup添加自定义的Handler
                    .childHandler(new ServerChannelInitializer())
                    // 如果设置了keeplive为true，当对方没有发送任何数据过来，超过2小时，那么我们这边会发送一个ack探测包发到对方，探测双方的TCP/IP连接是否有效(对方可能断点，断网)。
                    // 如果不设置，那么客户端宕机时，服务器永远也不知道客户端宕机了，仍然保存这个失效的连接。
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            // 绑定端口,开始接收进来的连接
            ChannelFuture channelFuture = bootstrap.bind(8087).sync();

//            log.info("服务器ip为："+socketAddress.getHostName());
//            log.info("服务器端口号为："+socketAddress.getPort());

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e){
            // 打印错误
            e.printStackTrace();

        }finally {
            // 关闭主线程组
            bossGroup.shutdownGracefully();
            // 关闭工作线程组
            workGroup.shutdownGracefully();
        }
    }
}
