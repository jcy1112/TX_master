package com.springboot.service.impl;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.controller.enums.OrderStatusEnum;
import com.springboot.entity.*;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.OrdersMapper;
import com.springboot.service.ICartService;
import com.springboot.service.IGoodsService;
import com.springboot.service.IItemService;
import com.springboot.service.IOrdersService;
import com.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


/*
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2023-03-04
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

    @Autowired
    private IItemService ItemService;

    @Autowired
    private ICartService cartService;

    @Autowired
    IGoodsService goodsService;


    @Transactional
    @Override
    public void addOrder(List<Cart> carts,String address) {

        BigDecimal total = new BigDecimal(0);
        Orders orders = new Orders();
        orders.setOrderno(IdUtil.fastSimpleUUID());
        orders.setUserid(TokenUtils.getCurrentUser().getId());
        orders.setStatus(OrderStatusEnum.NEED_SEND.getCode());
        orders.setAddress(address);

        for (Cart cart : carts) {
            total = total.add(cart.getPrice().multiply(BigDecimal.valueOf(cart.getNum())));
        }
        orders.setTotal(total);  // 计算总价格
        save(orders); // 保存订单 获取订单id

        for (Cart cart : carts) {
            // 下单成功，扣库存
            Goods goods = goodsService.getById(cart.getGoodsId());
            if (goods.getNums() < cart.getNum()) {
                throw new ServiceException("-1", "库存不足");
            }
            goods.setNums(goods.getNums() - cart.getNum());
            goodsService.updateById(goods);
            Item orderItem = new Item();
            orderItem.setOrderId(orders.getId());
            orderItem.setGoodsName(cart.getGoodName());
            orderItem.setPrice(cart.getPrice());
            orderItem.setImg(cart.getImg());
            orderItem.setNum(cart.getNum());
            orderItem.setGoodsId(cart.getGoodsId());
            ItemService.save(orderItem);
            // 下单成功后删除购物车商品
            cartService.removeById(cart.getId());
        }
    }
}
