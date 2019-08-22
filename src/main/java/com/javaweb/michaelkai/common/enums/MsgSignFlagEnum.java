package com.javaweb.michaelkai.common.enums;

/**
 * @ClassName MsgSignFlagEnum
 * @Description TODO
 * @Author YuKai Fan
 * @Date 2019/8/22 23:26
 * @Version 1.0
 **/
public enum MsgSignFlagEnum {
    unsign(0, "未签收"),
    signed(1, "已签收");

    public final Integer type;
    public final String content;

    MsgSignFlagEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }
}
