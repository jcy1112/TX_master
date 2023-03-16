package com.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品实体类
 *
 * @author 文涛
 * @since 2023-03-04
 */
@Data
@ApiModel(value = "Goods对象", description = "")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("名称")
      private String name;

      @ApiModelProperty("价格")
      private BigDecimal price;

      @ApiModelProperty("图片")
      private String img;

      @ApiModelProperty("描述")
      private String descpription;

      @ApiModelProperty("库存")
      private Integer nums;

      @ApiModelProperty("状态")
      private Boolean status;


}
