package com.javaweb.michaelkai.common.enums;

/**
 * @ClassName MsgActionEnum
 * @Description TODO
 * @Author YuKai Fan
 * @Date 2019/8/22 23:07
 * @Version 1.0
 **/
public enum MsgActionEnum {
    CONNECT(1, "第一次(或重连)初始化连接"),
    CHAT(2, "聊天消息"),
    SIGNED(3, "消息签收"),
    KEEPLIVE(4, "客户端保持心跳");

    public final Integer type;
    public final String content;

    MsgActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }
}
