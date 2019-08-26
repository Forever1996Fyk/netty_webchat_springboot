package com.javaweb.michaelkai.netty.chatroom.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName ChatRoomServer
 * @Description 创建单例的聊天室服务
 * @Author YuKai Fan
 * @Date 2019/8/26 20:15
 * @Version 1.0
 **/
public class ChatRoomServer {

    private static class SingletionServer {
        static final ChatRoomServer instance = new ChatRoomServer();
    }

    public static ChatRoomServer getInstance() {
        return SingletionServer.instance;
    }

    private EventLoopGroup mainGroup;//主线程池,nio监听事件
    private EventLoopGroup subGroup;//从线程池,nio工作线程池
    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;

    public ChatRoomServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChatRoomServerInitialzer());
    }

    public void start() {
        this.channelFuture = serverBootstrap.bind(8090);
        System.out.println("netty chatRoom server 启动完毕...");
    }

    public void shutdown() {
        this.mainGroup.shutdownGracefully();
        this.subGroup.shutdownGracefully();
    }

}
