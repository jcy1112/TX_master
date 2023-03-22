package com.springboot.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 文涛
 * @since 2023-03-21
 */
@Getter
@Setter
@ApiModel(value = "Collection对象", description = "")
public class Collection {

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("商品id")
    private String goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("图片")
    private String img;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("状态")
    private Boolean status;

}
