package com.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 轮播图实体类
 *
 *
 * @author 文涛
 * @since 2023-03-07
 */
@Data
@ApiModel(value = "Lun对象", description = "")
public class Lun  {

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
