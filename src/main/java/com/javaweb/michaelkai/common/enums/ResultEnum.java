package com.javaweb.michaelkai.common.enums;

/**
 * @program: project_parent
 * @description: 返回结果集枚举
 * @author: YuKai Fan
 * @create: 2019-05-07 11:07
 **/
public enum ResultEnum {
    /**
     * 通用状态
     */
    SUCCESS(200, "成功"),
    ERROR(400, "错误"),

    APPROVED(200, "通过"),
    NOT_APPROVED(200, "不通过"),

    /**
     * 工作流问题
     */
    MODEL_DATA_ISNULL(500, "模型数据为空，请先设计流程并成功保存"),
    MODEL_DATA_ERROR(500, "数据模型不符要求，请至少设计一条主线流程"),

    START_FAIL(500, "启动失败"),
    TASK_COMPLETE_FAIL(500, "任务完成失败"),

    DEPLOYMENT_ID_ISNULL(500, "流程部署id为空, 请部署流程"),

    ACT_SYNCHRONIZE_DATA_FAIL(500, "同步数据失败"),
    ACT_PROCESS_DEPLOY_DEL_FAIL(500, "流程定义删除失败"),

    ACT_PROCESS__RETURN_SUBMIT_ERROR(500, "流程重新提交错误"),
    ACT_PROCESS_RETURN_SUBMIT_TYPE_ERROR(500, "流程重新提交类型错误"),
    ACT_PROCESS_RETURN_SUBMIT_ID_ERROR(500, "流程重新提交id错误"),
    ACT_PROCESS_RETURN_SUBMIT_TASK_ID_ERROR(500, "流程重新提交任务id错误"),

    ACT_PROCESS_IMAGE_ERROR(500, "获取流程图错误"),

    /**
     * 账户问题
     */
    USER_EXIST(401, "该用户名已经存在"),
    USER_PWD_NULL(402, "密码不能为空"),
    USER_INEQUALITY(403, "两次密码不一致"),
    USER_OLD_PWD_ERROR(404, "原来密码不正确"),
    USER_NAME_PWD_NULL(405, "用户名和密码不能为空"),
    USER_CAPTCHA_ERROR(406, "验证码错误"),
    USER_PHONE_NOT_EXIST(407, "手机号不存在"),

    ACCOUNT_PWD_ERROR(408, "用户名或密码错误"),
    ACCOUNT_LOCKED(409, "账号已被锁定"),

    /**
     * 角色问题
     */
    ROLE_EXIST(401, "该角色标识已经存在，不允许重复！"),

    /**
     * 部门问题
     */
    DEPT_EXIST_USER(401, "部门存在用户，无法删除"),

    /**
     * 字典问题
     */
    DICT_EXIST(401, "该字典标识已经存在，不允许重复！"),

    /**
     * 非法操作
     */
    STATUS_ERROR(401, "非法操作：状态有误"),

    /**
     * 权限问题
     */
    NO_PERMISSIONS(401, "权限不足！"),
    NO_ADMIN_AUTH(500, "不允许操作超级管理员"),
    NO_ADMIN_STATUS(501, "不能修改超级管理员状态"),
    NO_ADMINROLE_AUTH(500, "不允许操作管理员角色"),
    NO_ADMINROLE_STATUS(501, "不能修改管理员角色状态"),

    /**
     * 文件上传问题
     */
    UPLOAD_FAIL(500, "上传失败"),
    NO_FILE_NULL(500, "文件不能为空"),
    NO_FILE_TYPE(500, "不支持该文件类型");

    private Integer value;

    private String message;

    ResultEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}