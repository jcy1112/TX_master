package com.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/*
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 文涛
 * @since 2023-03-07
 */
public interface CartMapper extends BaseMapper<Cart> {

    Page<Cart> page(Page<Object> objectPage, Integer userId, String name);

}
