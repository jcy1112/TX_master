package com.springboot.mapper;

import com.springboot.controller.dto.UserPasswordDTO;
import com.springboot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 *  Mapper 接口
 *
 * @author 文涛
 * @since 2023-03-04
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 修改密码
     * @param userPasswordDTO
     * @return
     */
    @Update("update sys_user set password = #{newPassword} where username = #{username} and password = #{password}")
    int updatePassword(UserPasswordDTO userPasswordDTO);

}
