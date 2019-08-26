package com.javaweb.michaelkai.netty.chatroom.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName ChatRoomClientHandler
 * @Description TODO
 * @Author YuKai Fan
 * @Date 2019/8/26 21:10
 * @Version 1.0
 **/
public class ChatRoomClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println(msg);
    }
}
