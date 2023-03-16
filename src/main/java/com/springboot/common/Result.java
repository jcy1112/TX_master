package com.springboot.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 结果集，用于前后端传输统一
 * 传输结构 { code , msg , data }
 *
 * @Author jcy
 * @Date 2023/3/2 18:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result(CodeEnum.CODE_200.getCode(), CodeEnum.CODE_200.getMessage(), null);
    }

    public static Result success(Object data) {
        return new Result(CodeEnum.CODE_200.getCode(), CodeEnum.CODE_200.getMessage(), data);
    }

    public static Result success(String msg) {
        return new Result(CodeEnum.CODE_200.getCode(), msg, null);
    }

    public static Result success(String msg,Object data) {
        return new Result(CodeEnum.CODE_200.getCode(), msg, data);
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result error() {
        return new Result(CodeEnum.CODE_500.getCode(), CodeEnum.CODE_500.getMessage(), null);
    }

}
