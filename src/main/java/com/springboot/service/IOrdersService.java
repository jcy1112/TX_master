package com.springboot.service;

import com.springboot.entity.Cart;
import com.springboot.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 文涛
 * @since 2023-03-04
 */
public interface IOrdersService extends IService<Orders> {

    void addOrder(List<Cart> carts,String address);

}
