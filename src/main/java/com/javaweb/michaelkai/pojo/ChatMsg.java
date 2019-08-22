package com.javaweb.michaelkai.pojo;

import java.io.Serializable;


/**
 * @author YuKai Fan
 * @create 2019-08-22 09:48:04
 */
public class ChatMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String sendUserId;
    private String acceptUserId;
    private String msg;
    private Integer signFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getAcceptUserId() {
        return acceptUserId;
    }

    public void setAcceptUserId(String acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getSignFlag() {
        return signFlag;
    }

    public void setSignFlag(Integer signFlag) {
        this.signFlag = signFlag;
    }
}
