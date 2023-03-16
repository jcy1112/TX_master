package com.springboot.common;

/**
 * 状态码
 * @Author jcy
 * @Date 2023/3/13 13:13
 */


public enum CodeEnum {

    CODE_200("200","成功"), //成功
    CODE_401("401","权限不足"),  // 权限不足
    CODE_402("402","数据库错误"),  // 权限不足
    CODE_400("400","参数错误"),  // 参数错误
    CODE_500("500","系统错误"), // 系统错误
    CODE_600("600","其他业务异常"); // 其他业务异常

    //错误码
    public String code;
    //提示信息
    public  String message;

    //构造函数
    CodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
