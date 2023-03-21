package com.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.common.CodeEnum;
import com.springboot.common.Result;
import com.springboot.controller.dto.GoodsDTO;
import com.springboot.entity.Cart;
import com.springboot.entity.User;
import com.springboot.service.UserService;
import com.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.springboot.service.OrdersService;
import com.springboot.entity.Orders;

import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制类
 *
 *
 * @author 文涛
 * @since 2023-03-04
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Resource
    private UserService userService;

    @Resource
    private OrdersService ordersService;

    //private final String now = DateUtil.now();

    /**
     * 新增或者更新
     * @param orders
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Orders orders) {
        if (orders.getId() == null) {
            orders.setUserid(Integer.valueOf(TokenUtils.getCurrentUser().getUsername()));
        }
        boolean value = ordersService.saveOrUpdate(orders);
        if (!value){
            return Result.error(CodeEnum.CODE_402.getCode(), "保存失败");
        }
        return Result.success("保存成功");
    }

    /**
     * 下单
     * @param carts
     * @return
     */
    @PostMapping("/addOrder")
    public Result addOrder(@RequestBody List<Cart> carts) {
        Integer userId = TokenUtils.getCurrentUser().getId();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        List<User> userlist = userService.list(queryWrapper);
        for (User users : userlist){
            String address = users.getAddress();
            ordersService.addOrder(carts,address);
        }
        return Result.success();
    }


    /**
     * 商品详情页直接购买
     * @param goodsDTO
     * @return
     */
    @PostMapping("/buy")
    public Result buy(@RequestBody GoodsDTO goodsDTO) {
        Integer userId = TokenUtils.getCurrentUser().getId();
        User user = userService.getById(userId);
        String address = user.getAddress();
        Orders orders = ordersService.buy(goodsDTO, address);

        return Result.success(orders);
    }


    /**
     * 修改订单状态
     * @param orders 订单信息
     * @return
     */
    @PostMapping("/status")
    public Result updateOrder(@RequestBody Orders orders) {
        Boolean value = ordersService.updateOrder(orders);
        if (!value){
            return Result.error(CodeEnum.CODE_402.getCode(), "修改失败");
        }
        return Result.success("修改成功");
    }

    /**
     * 前端订单展示
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page/PersonOrders")
    public Result findPagePersonOrders(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam(defaultValue = "") Integer status) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        Integer userid = TokenUtils.getCurrentUser().getId();
        queryWrapper.eq("userid", userid);
        return Result.success("查询成功", ordersService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean value = ordersService.removeById(id);
        if(!value){
            return Result.error(CodeEnum.CODE_402.getCode(), "删除失败");
        }
        return Result.success("删除成功");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        boolean value = ordersService.removeByIds(ids);
        if(!value){
            return Result.error(CodeEnum.CODE_402.getCode(), "删除失败");
        }
        return Result.success("删除成功");
    }

    /**
     * 查新全部
     * @return
     */
    @GetMapping
    public Result findAll() {
        return Result.success("查询成功",ordersService.list());
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success("查询成功",ordersService.getById(id));
    }

    /**
     *    分页查询，条件查询  查询所有订单
     *
     * @param orderNo  订单编号
     * @param pageNum  页码
     * @param pageSize 一页显示数目
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String orderNo,
                           @RequestParam(defaultValue = "") Integer status,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        if (!"".equals(orderNo)) {
            queryWrapper.like("orderno", orderNo);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        return Result.success("查询成功", ordersService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
}


