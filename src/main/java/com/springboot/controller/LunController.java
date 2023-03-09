package com.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.common.Result;
import com.springboot.config.interceptor.AuthAccess;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.springboot.service.ILunService;
import com.springboot.entity.Lun;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/*
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 文涛
 * @since 2023-03-07
 */
@RestController
@RequestMapping("/lun")
public class LunController {

    @Resource
    private ILunService lunService;

    private final String now = DateUtil.now();

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Lun lun) {
        /*if (lun.getId() == null) {
            //lun.setTime(DateUtil.now());
            //lun.setUser(TokenUtils.getCurrentUser().getUsername());
        }*/
        lunService.saveOrUpdate(lun);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        lunService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        lunService.removeByIds(ids);
        return Result.success();
    }

    @AuthAccess
    @GetMapping
    public Result findAll() {
        return Result.success(lunService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(lunService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Lun> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
//        User currentUser = TokenUtils.getCurrentUser();
//        if (currentUser.getRole().equals("ROLE_USER")) {
//            queryWrapper.eq("user", currentUser.getUsername());
//        }
        return Result.success(lunService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

