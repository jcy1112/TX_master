package com.springboot.service.impl;

import com.springboot.entity.Cart;
import com.springboot.mapper.CartMapper;
import com.springboot.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author 文涛
 * @since 2023-03-07
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

}
