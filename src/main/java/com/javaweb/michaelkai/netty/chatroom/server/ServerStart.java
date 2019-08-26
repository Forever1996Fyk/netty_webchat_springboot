package com.javaweb.michaelkai.netty.chatroom.server;

/**
 * @ClassName ServerStart
 * @Description TODO
 * @Author YuKai Fan
 * @Date 2019/8/26 21:11
 * @Version 1.0
 **/
public class ServerStart {
    public static void main(String[] args) {
        ChatRoomServer.getInstance().start();
    }
}
