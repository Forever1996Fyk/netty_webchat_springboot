package com.javaweb.michaelkai.pojo;

import java.io.Serializable;

/**
 * @author YuKai Fan
 * @create 2019-08-22 09:48:04
 */
public class MyFriends implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String myUserId;
    //我的朋友表
    private String myFriendUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }

    public String getMyFriendUserId() {
        return myFriendUserId;
    }

    public void setMyFriendUserId(String myFriendUserId) {
        this.myFriendUserId = myFriendUserId;
    }
}
