package com.javaweb.michaelkai.netty.chatroom.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ClassName ChatRoomClient
 * @Description TODO
 * @Author YuKai Fan
 * @Date 2019/8/26 21:02
 * @Version 1.0
 **/
public class ChatRoomClient extends Thread {

    public void run() {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatRoomClientInitializer());

            Channel channel = bootstrap.connect("127.0.0.1", 8090).sync().channel();
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                channel.writeAndFlush(input.readLine() + "\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

}
