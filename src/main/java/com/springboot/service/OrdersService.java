package com.springboot.service;

import com.springboot.controller.dto.GoodsDTO;
import com.springboot.entity.Cart;
import com.springboot.entity.Goods;
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
public interface OrdersService extends IService<Orders> {

    /**
     * 下订单
     * @param carts
     * @param address
     */
    void addOrder(List<Cart> carts,String address);


    /**
     * 商品详情页直接购买
     * @param goodsDTO
     * @param address
     * @return
     */
    Orders buy(GoodsDTO goodsDTO , String address);


    /**
     * 修改订单状态
     * @param orders
     */
    Boolean updateOrder(Orders orders);

}
