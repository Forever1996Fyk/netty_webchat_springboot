package com.javaweb.michaelkai.netty.chatroom.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @ClassName ChatRoomHandler
 * @Description 聊天室handler类
 * @Author YuKai Fan
 * @Date 2019/8/26 20:25
 * @Version 1.0
 **/
public class ChatRoomHandler extends SimpleChannelInboundHandler<String> {
    public static ChannelGroup client = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //String content = msg.text();
        Channel channel = ctx.channel();
        for (Channel ch : client) {
            if (ch != channel) {
                ch.writeAndFlush("用户【" + channel.remoteAddress() + "】说：" + msg + "\n");
            } else {
                ch.writeAndFlush("【我】说：" + msg + "\n");
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        client.add(ctx.channel());
        for (Channel channel : client) {
            if (ctx.channel() != channel) { //通知除了自己以外的其他用户
                client.writeAndFlush("【提示】：用户【" + ctx.channel().remoteAddress() + "】进入聊天室。。。\n");
            }
        }
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        client.remove(ctx.channel());
        for (Channel channel : client) {
            if (ctx.channel() != channel) { //通知除了自己以外的其他用户
                client.writeAndFlush("【提示】：用户【" + ctx.channel().remoteAddress() + "】下线了。。。\n");
            }
        }

    }

    /**
     * @Description 服务端监听到客户端活动
     *
     * @Author YuKai Fan
     * @Date  2019/8/26
     * @Param
     * @return
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("用户【" + ctx.channel().remoteAddress() + "】在线中。。。");
    }

    /**
     * @Description 服务端见到客户端不活动
     *
     * @Author YuKai Fan
     * @Date 2019/8/26
     * @Param
     * @return
     **/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("用户【" + ctx.channel().remoteAddress() + "】离线了。。。");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        client.remove(ctx.channel());
        ctx.channel().close();
    }
}
