package com.springboot.controller.dto;

import lombok.Data;

/**
 * @Author jcy
 * @Date 2023/3/19 20:21
 */

@Data
public class AliPay {

    private String traceNo;   //订单号
    private String totalAmount;   //总价格
    private String subject;   //订单名
    private String alipayTraceNo;   //付款编号

    /**
     * 账号：owbbbb2911@sandbox.com
     * 密码：111111
     */

}