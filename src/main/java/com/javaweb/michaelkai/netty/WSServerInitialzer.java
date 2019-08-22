package com.javaweb.michaelkai.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @ClassName WSServerInitialzer
 * @Description TODO
 * @Author YuKai Fan
 * @Date 2019/8/21 22:04
 * @Version 1.0
 **/
public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        //websocket基于http协议，所以要有http相应的编解码器
        pipeline.addLast(new HttpServerCodec());
        //对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        //对httpMessage进行聚合，集合成FullHttpRequest或FullHttpResponse
        //几乎在netty中的编程，都会用到此handler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        // =======================以上是对http支持的handler=======================

        /**
         * websocket服务器处理的协议，用于指定给客户端连接的路由：/ws
         * 本handler会帮你处理一些繁重的复杂的事
         * 会帮你处理握手动作：handshaking(close, ping, pong) ping + pong = 心跳
         * 对于websocket来讲，都是以frames进行传输的，不同的数据类型对应frames也不同
         *
         **/
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //自定义handler
        pipeline.addLast(new ChatHandler());
    }
}
