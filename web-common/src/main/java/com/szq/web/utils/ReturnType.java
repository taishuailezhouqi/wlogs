package com.szq.web.utils;

import java.util.stream.Stream;

/**
 * roc
 */
public enum ReturnType {


    SUCCESS(10000, "成功"),
    SYSTEM_ERROR(10001, "系统错误"),
    FAILURE(10002, "失败"),
    INVALID_PARAMETER(10003, "非法参数"),
    MISS_PARAM(10004, "缺少参数"),
    TOKEN_INVALID(10005, "token失效"),
    MISS_TOKEN(10006, "缺少token"),
    TOKEN_ERROR(10007, "token错误"),
    NO_LOGIN(10008, "用户未登录"),
    LOGIN_FAIL(20000, "登录失败"),
    USERNAME_OR_PWD_ERROR(20001, "用户名or密码错误"),
    USERNAME_OR_PWD_NULL(20002, "用户名or密码为空"),
    REPEATEDREMINDER(10010,"请勿频繁提交"),
    UNAUTHORIZED(20003, "未经授权"),
    FORBIDDEN(20004, "演示账号仅支持查询操作！"),
    USERNAME_NOT_FOUND(20005, "该用户不存在");



    private Integer code;

    private String mssage;

    ReturnType(int code, String msg) {
        this.code = code;
        this.mssage = msg;
    }

    public static ReturnType getByCode(String msg) {
        for (ReturnType returnType : ReturnType.values()) {
            if (msg.equals(returnType.getMsg())) {
                return returnType;
            }
        }
        return null;
    }

    public static boolean checkCode(int targetCode) {
        return Stream.of(ReturnType.values()).anyMatch(e -> e.getCode() == targetCode);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return mssage;
    }

    public void setMsg(String msg) {
        this.mssage = msg;
    }


}
