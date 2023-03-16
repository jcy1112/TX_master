package com.springboot.service;

import com.springboot.controller.dto.UserDTO;
import com.springboot.controller.dto.UserPasswordDTO;
import com.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *  服务类
 *
 * @author 文涛
 * @since 2023-03-04
 */
public interface UserService extends IService<User> {

    /**
     * 登录
     * @param userDTO
     * @return
     */
    UserDTO login(UserDTO userDTO);

    /**
     * 注册
     * @param userDTO
     * @return
     */
    User register(UserDTO userDTO);

    /**
     * 修改密码
     * @param userPasswordDTO
     */
    void updatePassword(UserPasswordDTO userPasswordDTO);
}
