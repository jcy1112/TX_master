package com.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

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
 * @since 2023-03-07
 */
@Getter
@Setter
@ApiModel(value = "Cart对象", description = "")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

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
