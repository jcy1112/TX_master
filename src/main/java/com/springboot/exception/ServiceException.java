package com.springboot.exception;

import lombok.Getter;

/**
 * 自定义异常
 * @Author jcy
 * @Date 2023/3/3 16:08
 */
@Getter
public class ServiceException extends RuntimeException {
    private String code;

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }

}
