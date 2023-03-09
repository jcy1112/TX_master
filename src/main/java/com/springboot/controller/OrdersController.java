package com.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.common.Result;
import com.springboot.entity.Cart;
import com.springboot.entity.User;
import com.springboot.service.IUserService;
import com.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.springboot.service.IOrdersService;
import com.springboot.entity.Orders;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/*
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 文涛
 * @since 2023-03-04
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Resource
    private IUserService userService;

    @Resource
    private IOrdersService ordersService;

//    @Resource
//    IOrderItemService orderItemService;

    private final String now = DateUtil.now();

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Orders orders) {
        if (orders.getId() == null) {
//            orders.setCreateTime(DateUtil.now());
//            TokenUtils.getCurrentUser().getUsername();
            orders.setUserid(Integer.valueOf(TokenUtils.getCurrentUser().getUsername()));
        }
        ordersService.saveOrUpdate(orders);
        return Result.success();
    }

    @PostMapping("/addOrder")
    public Result addOrder(@RequestBody List<Cart> carts) {
        Integer userId = TokenUtils.getCurrentUser().getId();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        List<User> list = userService.list(queryWrapper);
        for (User users : list){
            String address = users.getAddress();
            ordersService.addOrder(carts,address);
        }
        return Result.success();
    }

    @PostMapping("/status")
    public Result updateOrder(@RequestBody Orders orders) {
        ordersService.saveOrUpdate(orders);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        ordersService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        ordersService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(ordersService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(ordersService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(ordersService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    @GetMapping("/page/PersonOrders")
    public Result findPagePersonOrders(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        Integer userid = TokenUtils.getCurrentUser().getId();
        queryWrapper.eq("userid", userid);
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(ordersService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

}

