package com.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.common.CodeEnum;
import com.springboot.common.Result;
import com.springboot.entity.Goods;
import com.springboot.entity.User;
import com.springboot.mapper.CartMapper;
import com.springboot.service.GoodsService;
import com.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.springboot.service.CartService;
import com.springboot.entity.Cart;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *  购物车控制类
 *
 * @author 文涛
 * @since 2023-03-07
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @Resource
    private CartMapper cartMapper;

    @Resource
    private GoodsService goodsService;

    /**
     * 购物车展示
     * @return
     */
    @GetMapping("/personCart")
    public Result personCart() {
        BigDecimal total = new BigDecimal(0);
        //获取用户id
        Integer userid = TokenUtils.getCurrentUser().getId();
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        List<Cart> cartList = cartService.list(queryWrapper);
        for (Cart cart : cartList) {
            Goods goods = goodsService.getById(cart.getGoodsId());
            cart.setGoodName(goods.getName());
            cart.setImg(goods.getImg());
            cart.setPrice(goods.getPrice());
            total = total.add(goods.getPrice().multiply(BigDecimal.valueOf(cart.getNum())));
        }
        Dict dict = Dict.create().set("list", cartList).set("total", total);
        return Result.success(dict);
    }

    /**
     * 计算下单价格
     * @param carts
     * @return
     */
    @PostMapping("/cal")
    public Result cal(@RequestBody List<Cart> carts) {
        BigDecimal total = new BigDecimal(0);
        for (Cart cart : carts) {
            Goods goods = goodsService.getById(cart.getGoodsId());
            total = total.add(goods.getPrice().multiply(BigDecimal.valueOf(cart.getNum())));
        }
        return Result.success(total);
    }

    /**
     * 新增或者更新
     * @param cart
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Cart cart) {
        if (cart.getId() == null) {
            cart.setUserid(Integer.valueOf(TokenUtils.getCurrentUser().getId()));
        }
        boolean value = cartService.saveOrUpdate(cart);
        if (!value){
            return Result.error(CodeEnum.CODE_402.getCode(), "保存失败");
        }
        return Result.success("保存成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean value = cartService.removeById(id);
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
        boolean value = cartService.removeByIds(ids);
        if(!value){
            return Result.error(CodeEnum.CODE_402.getCode(), "删除失败");
        }
        return Result.success("删除成功");
    }

    /**
     * 查找全部
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Cart> carlist = cartService.list();
        for (Cart cart : carlist) {
            Goods goods = goodsService.getById(cart.getGoodsId());
            cart.setGoodName(goods.getName());
            cart.setImg(goods.getImg());
            cart.setPrice(goods.getPrice());
        }
        return Result.success("查询成功",carlist);
    }

    /**
     * 通过id查找
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success("查询成功",cartService.getById(id));
    }

    /**
     * 分页查询，条件查询
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        User currentUser = TokenUtils.getCurrentUser();
        Integer userId = currentUser.getId();
        return Result.success("查询成功",cartMapper.page(new Page<>(pageNum,pageSize),userId,name));
    }

    /**
     * 导出接口
     * @param response
     * @throws Exception
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Cart> list = cartService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("Cart信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     *
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Cart> list = reader.readAll(Cart.class);
        boolean value = cartService.saveBatch(list);
        if(!value){
            return Result.error(CodeEnum.CODE_402.getCode(), "导入失败");
        }
        return Result.success("导入成功");
    }
}

