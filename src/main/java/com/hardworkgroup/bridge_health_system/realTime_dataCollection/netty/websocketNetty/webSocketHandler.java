package com.hardworkgroup.bridge_health_system.realTime_dataCollection.netty.websocketNetty;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class webSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    // 指定类初始化日志对象
    private static final Logger log = LoggerFactory.getLogger(WebSocketNettyServer.class);

    /*
    * 当web客户端连接后， 触发方法
    * */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端连接: "+ctx.channel().id().asLongText());
        // 添加到channelGroup 通道组
        NettyConfig.getChannelGroup().add(ctx.channel());
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("服务器收到消息：{}",msg.text());

        // 获取用户ID,关联channel
        JSONObject webSocketData = JSONObject.parseObject(msg.text());
        String userID = (String) webSocketData.get("userID");
        NettyConfig.getUserChannelMap().put(userID,ctx.channel());

        // 将用户ID作为自定义属性加入到channel中，方便随时channel中获取用户ID
        AttributeKey<String> key = AttributeKey.valueOf("userId");
        ctx.channel().attr(key).setIfAbsent(userID);

        // 回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器连接成功！"));
    }

    /**
     * 客户端与服务端 断连时 执行
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端断开连接: "+ctx.channel().id().asLongText());
        // 删除通道
        NettyConfig.getChannelGroup().remove(ctx.channel());
        // 删除用户与channel的对应关系
        removeUserId(ctx);
    }

    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 打印异常
        cause.printStackTrace();
        // 删除通道
        NettyConfig.getChannelGroup().remove(ctx.channel());
        // 删除用户与channel的对应关系
        removeUserId(ctx);
        // 关闭通道
        ctx.close();
    }

    /**
     * 删除用户与channel的对应关系
     */
    private void removeUserId(ChannelHandlerContext ctx){
        // 获得channel对应的userID
        AttributeKey<String> key = AttributeKey.valueOf("userId");
        String userID = ctx.channel().attr(key).get();
        // 删除用户与channel的对应关系
        NettyConfig.getUserChannelMap().remove(userID);
    }

}
