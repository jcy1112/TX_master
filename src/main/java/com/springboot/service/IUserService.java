package com.springboot.service;

import com.springboot.controller.dto.UserDTO;
import com.springboot.controller.dto.UserPasswordDTO;
import com.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/*
 * <p>
 *  服务类
 * </p>
 *
 * @author 文涛
 * @since 2023-03-04
 */
public interface IUserService extends IService<User> {
    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);
}
