package com.springboot.service;

import com.springboot.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/*
 * <p>
 *  服务类
 * </p>
 *
 * @author 文涛
 * @since 2023-03-08
 */
public interface ICommentService extends IService<Comment> {
    List<Comment> findCommentDetail(Integer goodsId);
}
