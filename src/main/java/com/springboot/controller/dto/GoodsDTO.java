package com.springboot.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用于接收直接购买商品的请求
 * @Author jcy
 * @Date 2023/3/21 16:09
 */


@Data
public class GoodsDTO {

    private Integer id;
    private String name;
    private BigDecimal price;
    private String img;
    private Integer nums;

}
