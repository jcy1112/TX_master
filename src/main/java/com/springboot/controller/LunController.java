package com.springboot.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.common.CodeEnum;
import com.springboot.common.Result;
import com.springboot.common.AuthAccess;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.springboot.service.LunService;
import com.springboot.entity.Lun;

import org.springframework.web.bind.annotation.RestController;

/**
 * 轮播图控制类
 *
 * @author 文涛
 * @since 2023-03-07
 */
@RestController
@RequestMapping("/lun")
public class LunController {

    @Resource
    private LunService lunService;

    private final String now = DateUtil.now();

    /**
     * 新增或者更新
     * @param lun 轮播图对象
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Lun lun) {
        boolean value = lunService.saveOrUpdate(lun);
        if (!value){
            return Result.error(CodeEnum.CODE_402.getCode(), "保存失败");
        }
        return Result.success("保存成功");
    }

    /**
     * 按id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean value = lunService.removeById(id);
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
        boolean value = lunService.removeByIds(ids);
        if(!value){
            return Result.error(CodeEnum.CODE_402.getCode(), "删除失败");
        }
        return Result.success("删除成功");
    }

    /**
     * 查询全部
     * @return
     */
    @AuthAccess
    @GetMapping
    public Result findAll() {
        return Result.success("查询成功",lunService.list());
    }

    /**
     * 按id查找
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success("查询成功",lunService.getById(id));
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
        QueryWrapper<Lun> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success("查询成功",lunService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

