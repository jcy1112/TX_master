package com.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

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
@ApiModel(value = "Lun对象", description = "")
public class Lun implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("图片")
    @Alias("图片")
    private String img;

    @ApiModelProperty("商品链接")
    @Alias("商品链接")
    private String link;

    @ApiModelProperty("商品id")
    @Alias("商品id")
    private Integer goodsId;

    @ApiModelProperty("商品名称")
    @Alias("商品名称")
    private String goodsName;


}
