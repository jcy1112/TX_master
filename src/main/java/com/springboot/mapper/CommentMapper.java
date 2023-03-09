package com.springboot.mapper;

import com.springboot.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 文涛
 * @since 2023-03-08
 */
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select c.*,u.nickname,u.avatar_url from t_comment c left join sys_user u on c.user_id = u.id where c.goods_id = #{goodsId}")
    List<Comment> findCommentDetail(@Param("goodsId") Integer goodsId);
}
