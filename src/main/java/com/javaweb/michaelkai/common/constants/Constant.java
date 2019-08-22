package com.javaweb.michaelkai.common.constants;

/**
 * @program: project_base
 * @description: 常量
 * @author: YuKai Fan
 * @create: 2019-05-28 16:14
 **/
public class Constant {
    //默认密码
    public static final String DEFAULT_PWD = "111111";

    //通用时间格式
    public static final String DATE_FORMAT_COMMON = "yyyy-MM-dd HH:mm:ss";

    //创建，更新日期格式
    public static final String DATE_FORMAT_CREATE_UPDATE = "yyyy-MM-dd HH:mm:ss";

    //文件上传目录日期格式
    public static final String DATE_FORMAT_FILE_INDEX = "yyyyMMdd";

    //通知通告发布时间格式
    public static final String DATE_FORMAT_NOTICE_PUBLIC = "yyyy-MM-dd HH:mm:ss";

    //通知通告redis缓存离线信息
    public static final String REDIS_NOTICE_OFFLINE = "REDIS_NOTICE_OFFLINE:";

    //token过期时间
    public static final String JWT_TOKEN = "JWT_TOKEN:";

    //token黑名单(但是黑名单中的token无法认证)
    public static final String JWT_TOKEN_BLACKLIST = "JWT_TOKEN_BLACKLIST:";
}