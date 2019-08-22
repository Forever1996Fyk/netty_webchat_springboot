package com.javaweb.michaelkai.pojo;

import java.io.Serializable;
import java.util.Date;


/**
 * @author YuKai Fan
 * @create 2019-08-22 09:48:04
 */
public class FreiendsRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String sendUserId;
    private String acceptUserId;
    //用户请求表
    private Date requestDateTime;

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

    public Date getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(Date requestDateTime) {
        this.requestDateTime = requestDateTime;
    }
}
