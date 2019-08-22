package com.javaweb.michaelkai.netty.entity;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserChannelRel
 * @Description 用户id与channel的关联关系处理
 * @Author YuKai Fan
 * @Date 2019/8/22 23:14
 * @Version 1.0
 **/
public class UserChannelRel {
    private static Map<String, Channel> manager = new HashMap<>();

    public static void put(String userId, Channel channel) {
        manager.put(userId, channel);
    }

    public static Channel get(String userId) {
        return manager.get(userId);
    }

    public static void output() {
        for(Map.Entry<String, Channel> entry : manager.entrySet()) {
            System.out.println("UserId: " + entry.getKey() + ", ChannelId: " + entry.getValue().id().asLongText());
        }
    }
}
