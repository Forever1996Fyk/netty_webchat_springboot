package com.javaweb.michaelkai.netty.entity;

import java.io.Serializable;

/**
 * @ClassName DataContent
 * @Description TODO
 * @Author YuKai Fan
 * @Date 2019/8/22 23:00
 * @Version 1.0
 **/
public class DataContent implements Serializable {

    private static final long serialVersionUID = 6446891441246311400L;

    //动作类型
    private Integer action;
    //聊天消息实体
    private ChatMsg chatMsg;
    //扩展字段
    private String extand;

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public ChatMsg getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(ChatMsg chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getExtand() {
        return extand;
    }

    public void setExtand(String extand) {
        this.extand = extand;
    }
}
