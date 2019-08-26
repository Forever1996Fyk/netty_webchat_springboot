package com.javaweb.michaelkai.netty.chatroom.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @ClassName ChatRoomClientInitializer
 * @Description TODO
 * @Author YuKai Fan
 * @Date 2019/8/26 21:08
 * @Version 1.0
 **/
public class ChatRoomClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //当有客户端连接服务器时，netty会调用这个初始化器的 initChannel方法
        System.out.println("客戶端開始初始化。。。。");

        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new ChatRoomClientHandler());

    }
}
