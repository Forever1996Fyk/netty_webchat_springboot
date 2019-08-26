package com.javaweb.michaelkai.netty.chatroom.client;

/**
 * @ClassName MyThread
 * @Description TODO
 * @Author YuKai Fan
 * @Date 2019/8/26 21:33
 * @Version 1.0
 **/
public class MyThread extends Thread {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            ChatRoomClient chatRoomClient = new ChatRoomClient();
            chatRoomClient.start();
        }
    }
}
