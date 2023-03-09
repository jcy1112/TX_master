package com.springboot.service.impl;

import com.springboot.entity.Comment;
import com.springboot.mapper.CommentMapper;
import com.springboot.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 文涛
 * @since 2023-03-08
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findCommentDetail(Integer goodsId) {
        return commentMapper.findCommentDetail(goodsId);
    }
}
