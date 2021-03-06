package com.javaweb.michaelkai.pojo;

import java.io.Serializable;


/**
 * @author YuKai Fan
 * @create 2019-08-22 09:48:04
 */
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String userName;
    private String password;
    private String faceImage;
    private String faceImageBig;
    private String nickName;
    private String qrcode;
    //用户表
    private String cid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public String getFaceImageBig() {
        return faceImageBig;
    }

    public void setFaceImageBig(String faceImageBig) {
        this.faceImageBig = faceImageBig;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
