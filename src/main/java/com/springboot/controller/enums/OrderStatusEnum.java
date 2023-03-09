package com.springboot.controller.enums;

public enum OrderStatusEnum {
    NEED_SEND(1, "待发货"),
    NEED_RECEIVE(2, "待收货"),
    NEED_COMMENT(3, "待评价"),
    DONE(4, "已完成");
    private int code;
    private String label;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    OrderStatusEnum(int code, String label) {
        this.code = code;
        this.label = label;
    }
}
