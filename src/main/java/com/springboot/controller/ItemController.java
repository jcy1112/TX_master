package com.springboot.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.springboot.common.AuthAccess;
import com.springboot.common.CodeEnum;
import com.springboot.common.Result;
import com.springboot.entity.Orders;
import com.springboot.entity.User;
import com.springboot.service.OrdersService;
import com.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.springboot.service.ItemService;
import com.springboot.entity.Item;

import org.springframework.web.bind.annotation.RestController;

/**
 *  订单明细控制类
 *
 * @author 文涛
 * @since 2023-03-04
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Resource
    private UserService userService;

    @Resource
    private ItemService itemService;

    @Resource
    private OrdersService ordersService;

    /**
     * 评论
     * @param goodsId 商品id
     * @return
     */
    @AuthAccess
    @GetMapping("/comment/{goodsId}")
    public Result comment(@PathVariable Integer goodsId) {
        ArrayList<Object> comments = new ArrayList<>();
        List<Item> itemList = itemService.list(new QueryWrapper<Item>().eq("goods_id", goodsId));
        for (Item orderItem : itemList) {
            Integer orderId = orderItem.getOrderId();
            Orders orders = ordersService.getById(orderId);
            Integer userid = orders.getUserid();
            User user = userService.getById(userid);
            if (StrUtil.isNotBlank(orderItem.getComment())) {
                comments.add(Dict.create().set("avatar", user.getAvatarUrl()).set("user", user.getNickname()).set("comment", orderItem.getComment()));
            }
        }
        return Result.success(comments);
    }

    /**
     * 通过订单id查找
     * @param orderId 订单id
     * @return
     */
    @GetMapping("/{orderId}")
    public Result findItemByOrderId(@PathVariable Integer orderId) {
        QueryWrapper<Item> queryWrapper = new QueryWrapper<Item>();
        queryWrapper.eq("order_id",orderId);
        return Result.success("查询成功",itemService.list(queryWrapper));
    }

    /**
     * 新增或者更新
     * @param item
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Item item) {
        boolean value = itemService.saveOrUpdate(item);
        if (!value){
            return Result.error(CodeEnum.CODE_402.getCode(), "保存失败");
        }
        return Result.success("保存成功");
    }

}

