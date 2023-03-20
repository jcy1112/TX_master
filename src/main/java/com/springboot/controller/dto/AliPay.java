package com.springboot.controller.dto;

import lombok.Data;

/**
 * @Author jcy
 * @Date 2023/3/19 20:21
 */

@Data
public class AliPay {

    private String traceNo;
    private String totalAmount;
    private String subject;
    private String alipayTraceNo;

    /**
     * 账号：owbbbb2911@sandbox.com
     * 密码：111111
     */

}