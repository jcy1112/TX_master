package com.springboot.service.impl;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.springboot.common.OrderStatusEnum;
import com.springboot.entity.*;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.OrdersMapper;
import com.springboot.service.CartService;
import com.springboot.service.GoodsService;
import com.springboot.service.ItemService;
import com.springboot.service.OrdersService;
import com.springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


/**
 * 服务实现类
 *
 * @author
 * @since 2023-03-04
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Resource
    private ItemService itemService;

    @Resource
    private CartService cartService;

    @Resource
    GoodsService goodsService;

    /**
     * 下订单
     * @param carts   购物车勾选信息
     * @param address 地址
     */
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
            boolean result = itemService.save(orderItem);
            if (result){
                // 下单成功后删除购物车商品
                cartService.removeById(cart.getId());
            }
        }
    }


    /**
     * 修改订单状态
     *
     * @param orders
     */
    @Override
    public Boolean updateOrder(Orders orders) {
        if (Objects.equals(orders.getStatus(), OrderStatusEnum.CANCEL.getCode())) {
            // 取消订单，加库存
            Integer ordersId = orders.getId();
            QueryWrapper<Item> order_id = new QueryWrapper<Item>().eq("order_id", ordersId);
            List<Item> orderItems = itemService.list(order_id);
            for (Item orderItem : orderItems) {
                Goods goods = goodsService.getById(orderItem.getGoodsId());
                goods.setNums(goods.getNums() + orderItem.getNum());
                goodsService.updateById(goods);
            }
        }
        return (updateById(orders));
    }
}
