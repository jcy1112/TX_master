package com.springboot.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.common.Result;
import com.springboot.entity.Orders;
import com.springboot.entity.User;
import com.springboot.service.IOrdersService;
import com.springboot.service.IUserService;
import com.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.springboot.service.IItemService;
import com.springboot.entity.Item;

import org.springframework.web.bind.annotation.RestController;

/*
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 文涛
 * @since 2023-03-04
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Resource
    private IUserService userService;

    @Resource
    private IItemService itemService;

    @Resource
    private IOrdersService ordersService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Item item) {
//        if (orders.getId() == null) {
////            orders.setCreateTime(DateUtil.now());
////            TokenUtils.getCurrentUser().getUsername();
//            orders.setUserid(Integer.valueOf(TokenUtils.getCurrentUser().getUsername()));
//        }
        itemService.saveOrUpdate(item);
        return Result.success();
    }
    @GetMapping("/comment/{goodsId}")
    public Result comment(@PathVariable Integer goodsId) {
        ArrayList<Object> comments = new ArrayList<>();
        List<Item> list = itemService.list(new QueryWrapper<Item>().eq("goods_id", goodsId));
        for (Item orderItem : list) {
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

    @GetMapping("/{orderId}")
    public Result findItemByOrderId(@PathVariable Integer orderId) {
        QueryWrapper<Item> queryWrapper = new QueryWrapper<Item>();
        queryWrapper.eq("order_id",orderId);
        return Result.success(itemService.list(queryWrapper));
    }

}

