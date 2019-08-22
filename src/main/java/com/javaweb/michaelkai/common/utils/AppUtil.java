package com.javaweb.michaelkai.common.utils;

/**
 * @program: project_base
 * @description: 通用工具
 * @author: YuKai Fan
 * @create: 2019-05-31 13:35
 **/
public class AppUtil {

    /**
     * 获取随机全局唯一id
     * @return
     */
    public static String randomId() {
        IdWorker idWorker = new IdWorker(1,1);
        return String.valueOf(idWorker.nextId());
    }
}