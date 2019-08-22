package com.javaweb.michaelkai.netty.entity;

import java.io.Serializable;


/**
 * @author YuKai Fan
 * @create 2019-08-22 09:48:04
 */
public class ChatMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    //聊天内容
    private String msg;
    //发送者的用户id
    private String senderId;
    //接收者的用户Id
    private String rerceiverId;
    //用于消息的签收
    private String msgId;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRerceiverId() {
        return rerceiverId;
    }

    public void setRerceiverId(String rerceiverId) {
        this.rerceiverId = rerceiverId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
