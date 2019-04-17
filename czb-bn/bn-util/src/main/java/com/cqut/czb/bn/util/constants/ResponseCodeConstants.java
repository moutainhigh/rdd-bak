package com.cqut.czb.bn.util.constants;

/**
 * 状态不要定义过多，避免前台的多种判断
 * */
public class ResponseCodeConstants {

    /**
     * 请求成功code
     * */
    public static int SUCCESS = 200;

    /**
     * 请求失败
     * */
    public static int FAILURE = 500;

    /**
     * 权限验证失败
     * */
    public static int INVALID_TOKEN = 1001;

    /**
     * 账号不存在
     * */
    public static int LOGIN_USER_NOT_FOUND = 1002;

    /**
     * 密码错误
     * */
    public static int LOGIN_INCORRECT_PASSWORD = 1003;

    /**
     * 账号过期
     * */
    public static int LOGIN_USER_EXPIRED = 1004;

    /**
     * 账号被冻结
     * */
    public static int LOGIN_USER_LOCKED = 1005;

    /**
     * 账号失效
     * */
    public static int LOGIN_USER_DISABLED = 1006;

    /**
     * 数据验证出错
     * */
    public static int BIND_ERROR = 1007;

}
