package com.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 购物车实体类
 *
 *
 * @author 文涛
 * @since 2023-03-07
 */
@Data
@ApiModel(value = "Cart对象", description = "")
public class Cart  {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("商品id")
    @Alias("商品id")
    private Integer goodsId;

    @ApiModelProperty("商品数量")
    @Alias("商品数量")
    private Integer num;

    @ApiModelProperty("用户id")
    @Alias("用户id")
    private Integer userid;

    @TableField(exist = false)
    private String goodName;
    @TableField(exist = false)
    private String img;
    @TableField(exist = false)
    private BigDecimal price;

    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String nickname;


}
