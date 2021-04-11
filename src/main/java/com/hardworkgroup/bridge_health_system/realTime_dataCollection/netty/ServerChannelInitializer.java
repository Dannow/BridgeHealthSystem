package com.hardworkgroup.bridge_health_system.realTime_dataCollection.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/*
* 初始化编码器
* */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //得到管道
        ChannelPipeline pipeline = ch.pipeline();
        // 向pipeline添加字符串的解码器
        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
        // 向pipeline添加字符串的编码器
        pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        // 向pipeline添加自定义的NettyServerHandler处理器
        pipeline.addLast(new NettyServerHandler());
    }
}
