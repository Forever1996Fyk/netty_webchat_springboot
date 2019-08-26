package com.javaweb.michaelkai.netty.chatroom.server;

import io.netty.channel.*;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName ChatRoomServerInitialzer
 * @Description 聊天服务初始化器，初始化handler类
 * @Author YuKai Fan
 * @Date 2019/8/26 20:22
 * @Version 1.0
 **/
public class ChatRoomServerInitialzer extends ChannelInitializer<Channel> {
    private ConcurrentMap<ChannelId, Channel> channelMap = new ConcurrentHashMap<>();
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        /*//保存该channel到map中
        channelMap.putIfAbsent(channel.id(), channel);
        channel.closeFuture().addListener((ChannelFutureListener) channelFuture -> {
            System.out.println("channel close future: " + channelFuture);
           //关闭后，从map中移除
           channelMap.remove(channelFuture.channel().id());
        });
        //websocket基于http协议，所以要有http相应的编解码器
        pipeline.addLast(new HttpServerCodec());
        //对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        //对httpMessage进行聚合，集合成FullHttpRequest或FullHttpResponse
        //几乎在netty中的编程，都会用到此handler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));*/

        //pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast("framer",new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder",new StringDecoder());
        pipeline.addLast("encoder",new StringEncoder());
        pipeline.addLast(new ChatRoomHandler());
    }
}
