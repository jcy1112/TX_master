package com.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.common.CodeEnum;
import com.springboot.common.Result;
import com.springboot.entity.Goods;
import com.springboot.service.GoodsService;
import com.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.springboot.service.CollectionService;
import com.springboot.entity.Collection;

import org.springframework.web.bind.annotation.RestController;

/*
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 文涛
 * @since 2023-03-21
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Resource
    private CollectionService collectionService;

    @Resource
    private GoodsService goodsService;

    // 新增或者更新
    @PostMapping("/{goodsId}")
    public Result save(@PathVariable Integer goodsId) {
        Goods goods = goodsService.getById(goodsId);
        Integer userId = Integer.valueOf(TokenUtils.getCurrentUser().getId());

        Collection collection = new Collection();
        collection.setUserId(userId);
        collection.setGoodsId(String.valueOf(goodsId));
        collection.setGoodsName(goods.getName());
        collection.setImg(goods.getImg());
        collection.setPrice(goods.getPrice());
        collection.setStatus(goods.getStatus());

        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("goods_name", collection.getGoodsName());
        Collection one = collectionService.getOne(queryWrapper);
        if (one == null) {
            boolean save = collectionService.save(collection);
            if (save) {
                return Result.success("收藏成功");
            } else {
                return Result.error(CodeEnum.CODE_402.getCode(), "收藏失败,请稍后再试");
            }
        } else {
            return Result.error(CodeEnum.CODE_600.getCode(), "商品已收藏");
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        collectionService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        collectionService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String goodsName,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        Integer userId = Integer.valueOf(TokenUtils.getCurrentUser().getId());
        if (!"".equals(goodsName)){
            queryWrapper.eq("user_id", userId).eq("goods_name",goodsName);
        }else {
            queryWrapper.eq("user_id", userId);
        }
        return Result.success(collectionService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

