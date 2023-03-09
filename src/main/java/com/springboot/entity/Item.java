package com.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/*
 * <p>
 *
 * </p>
 *
 * @author 文涛
 * @since 2023-03-04
 */
@Getter
@Setter
@TableName("order_item")
@ApiModel(value = "Item对象", description = "")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("订单id")
    @Alias("订单id")
    private Integer orderId;

    @ApiModelProperty("商品id")
    @Alias("商品id")
    private Integer goodsId;

    @ApiModelProperty("商品数量")
    @Alias("商品数量")
    private Integer num;

    @ApiModelProperty("商品单价")
    @Alias("商品单价")
    private BigDecimal price;

    @ApiModelProperty("商品图片")
    @Alias("商品图片")
    private String img;
    private String goodsName;
    private String comment;


}
