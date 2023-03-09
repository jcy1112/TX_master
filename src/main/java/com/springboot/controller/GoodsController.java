package com.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.common.Constants;
import com.springboot.common.Result;
import com.springboot.config.interceptor.AuthAccess;
import com.springboot.entity.Files;
import com.springboot.mapper.FileMapper;
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

import com.springboot.service.IGoodsService;
import com.springboot.entity.Goods;

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
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private IGoodsService goodsService;

    @Resource
    private GoodsMapper goodsMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final String now = DateUtil.now();

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Goods goods) {
        if (goods.getId() == null) {
            //goods.setTime(DateUtil.now());
            //goods.setUser(TokenUtils.getCurrentUser().getUsername());
        }
        goodsService.saveOrUpdate(goods);
        // 清空缓存
//        flushRedis(Constants.FILES_KEY);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        goodsService.removeById(id);
        // 清空缓存
//        flushRedis(Constants.FILES_KEY);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        goodsService.removeByIds(ids);
        // 清空缓存
//        flushRedis(Constants.FILES_KEY);
        return Result.success();
    }

    @GetMapping
    public Result findAll(@RequestParam(required = false) Boolean status) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(status != null, "status", status);
        return Result.success(goodsService.list(queryWrapper));
    }

    @AuthAccess
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(goodsService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(goodsService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /*
     * 导出接口
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

    /*
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Goods> list = reader.readAll(Goods.class);
//        goodsService.saveBatch(list);
//        // 清空缓存
//        flushRedis(Constants.FILES_KEY);
        return Result.success();
    }


    @AuthAccess
    @GetMapping("/front")
    //    @Cacheable(value = "files" ,key = "'frontAll'")
    public Result frontAll(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {

        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(goodsService.page(new Page<>(pageNum, pageSize), queryWrapper));

//        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
//        if (!"".equals(name)) {
//            queryWrapper.like("name", name);
//            return Result.success(goodsService.page(new Page<>(pageNum, pageSize), queryWrapper));
//        }else {
//            String jsonStr = stringRedisTemplate.opsForValue().get(Constants.FILES_KEY);
//            if (StrUtil.isBlank(jsonStr)){//  取出来的json是空的
//                Page<Goods> pagegoods = goodsService.page(new Page<>(pageNum, pageSize));     //  从数据库取出数据
//                stringRedisTemplate.opsForValue().set(Constants.FILES_KEY, JSONUtil.toJsonStr(pagegoods));
//                return Result.success(pagegoods);
//            }else {
//                // 减轻数据库的压力
//                // 如果有, 从redis缓存中获取数据
//                Page<Goods> pagegoods = JSONUtil.toBean(jsonStr, new TypeReference<Page<Goods>>() {}, true);
//                if (!(pagegoods.getCurrent() ==pageNum)){     //如果当前页不等于redis存储的页码，则从数据库取出
//                    Page<Goods> pagegoods1 = goodsService.page(new Page<>(pageNum, pageSize));     // 从数据库取出数据
//                    stringRedisTemplate.opsForValue().set(Constants.FILES_KEY, JSONUtil.toJsonStr(pagegoods1));
//                    return Result.success(pagegoods1);
//                }
//                return Result.success(pagegoods);
//            }
//        }
    }

    // 删除缓存
    private void flushRedis(String key) {
        stringRedisTemplate.delete(key);
    }


}

