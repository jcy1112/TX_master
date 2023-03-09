package com.springboot.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.common.Result;
import com.springboot.entity.Item;
import com.springboot.entity.Orders;
import com.springboot.entity.User;
import com.springboot.service.IItemService;
import com.springboot.service.IOrdersService;
import com.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.springboot.service.ICommentService;
import com.springboot.entity.Comment;

import org.springframework.web.bind.annotation.RestController;

/*
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 文涛
 * @since 2023-03-08
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private ICommentService commentService;

    @Resource
    private IItemService itemService;

    @Resource
    private IOrdersService ordersService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Comment comment) {
        if (comment.getId() == null) { // 新增评论
            comment.setUserId(TokenUtils.getCurrentUser().getId());
            comment.setTime(DateUtil.now());

            if (comment.getPid() != null) {  // 判断如果是回复，进行处理
                Integer pid = comment.getPid();
                Comment pComment = commentService.getById(pid);
                if (pComment.getOriginId() != null) {  // 如果当前回复的父级有祖宗，那么就设置相同的祖宗
                    comment.setOriginId(pComment.getOriginId());
                } else {  // 否则就设置父级为当前回复的祖宗
                    comment.setOriginId(comment.getPid());
                }
            }

        }
        commentService.saveOrUpdate(comment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        commentService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        commentService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(commentService.list());
    }

    @GetMapping("/tree/{goodsId}")
    public Result findTree(@PathVariable Integer goodsId) {
        List<Comment> goodsComments = commentService.findCommentDetail(goodsId);
        return Result.success(goodsComments);

//        List<Comment> goodsComments = commentService.findCommentDetail(articleId);  // 查询所有的评论和回复数据
//        // 查询评论数据（不包括回复）
//        List<Comment> originList = goodsComments.stream().filter(comment -> comment.getOriginId() == null).collect(Collectors.toList());
//
//        // 设置评论数据的子节点，也就是回复内容
//        for (Comment origin : originList) {
//            List<Comment> comments = goodsComments.stream().filter(comment -> origin.getId().equals(comment.getOriginId())).collect(Collectors.toList());  // 表示回复对象集合
//            comments.forEach(comment -> {
//                Optional<Comment> pComment = goodsComments.stream().filter(c1 -> c1.getId().equals(comment.getPid())).findFirst();  // 找到当前评论的父级
//                pComment.ifPresent((v -> {  // 找到父级评论的用户id和用户昵称，并设置给当前的回复对象
//                    comment.setPUserId(v.getUserId());
//                    comment.setPNickname(v.getNickname());
//                }));
//            });
//            origin.setChildren(comments);
//        }
//        return Result.success(originList);

    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(commentService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();

        return Result.success(commentService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

