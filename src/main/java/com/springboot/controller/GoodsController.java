package com.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.common.CodeEnum;
import com.springboot.common.Result;
import com.springboot.common.AuthAccess;
import com.springboot.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.springboot.service.GoodsService;
import com.springboot.entity.Goods;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商品控制类
 *
 * @author 文涛
 * @since 2023-03-04
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    /**
     * 前台商品列表，
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     * @AuthAccess 自定义注解 放行权限
     */
    @AuthAccess
    @GetMapping("/front")
    public Result frontAll(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {

        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(goodsService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
     * 前台按照id查询商品
     *
     * @param id
     * @return
     */
    @AuthAccess
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success("查询成功", goodsService.getById(id));
    }

    /**
     * 新增或者更新
     *
     * @param goods
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Goods goods) {
        boolean value = goodsService.saveOrUpdate(goods);
        if (!value) {
            return Result.error(CodeEnum.CODE_402.getCode(), "保存失败");
        }
        return Result.success("保存成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean value = goodsService.removeById(id);
        if (!value) {
            return Result.error(CodeEnum.CODE_402.getCode(), "删除失败");
        }
        return Result.success("删除成功");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        boolean value = goodsService.removeByIds(ids);
        if (!value) {
            return Result.error(CodeEnum.CODE_402.getCode(), "删除失败");
        }
        return Result.success("删除成功");
    }

    /**
     * 按照状态查找全部
     *
     * @param status 商品状态
     * @return
     */
    @GetMapping
    public Result findAll(@RequestParam(required = false) Boolean status) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(status != null, "status", status);
        return Result.success("查询成功", goodsService.list(queryWrapper));
    }

    /**
     * 分页查询，条件查询
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success("查询成功", goodsService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
     * 分页查询，条件查询
     *查询已上架商品
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page/status")
    public Result findstatusPage(@RequestParam(defaultValue = "") String name,
                                 @RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam Boolean status) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status);
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success("查询成功", goodsService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


    /**
     * 导出接口
     *
     * @param response
     * @throws Exception
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Goods> list = goodsService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("Goods信息表", "UTF-8");
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
     * @return
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Goods> list = reader.readAll(Goods.class);
        return Result.success("导入成功");
    }

}

