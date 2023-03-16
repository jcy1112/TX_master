package com.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.common.CodeEnum;
import com.springboot.controller.dto.UserDTO;
import com.springboot.controller.dto.UserPasswordDTO;
import com.springboot.entity.User;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.UserMapper;
import com.springboot.service.UserService;
import com.springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 服务实现类
 *
 * @author
 * @since 2023-03-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Log LOG = Log.get();

    @Resource
    private UserMapper userMapper;

    /**
     * 修改密码
     *
     * @param userPasswordDTO 用户密码信息
     */
    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        int update = userMapper.updatePassword(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(CodeEnum.CODE_600.getCode(), "密码错误");
        }
    }

    /**
     * 登录
     *
     * @param userDTO 用户信息
     * @return
     */
    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            //设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            //密码置空，防止将密码返回前端
            userDTO.setPassword("");
            return userDTO;
        } else {
            throw new ServiceException(CodeEnum.CODE_600.getCode(), "用户名或密码错误");
        }
    }

    /**
     * 注册
     *
     * @param userDTO 用户信息
     * @return
     */
    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            save(one);  // 把 copy完之后的用户对象存储到数据库
        } else {
            throw new ServiceException(CodeEnum.CODE_600.getCode(), "用户已存在");
        }
        return one;
    }

    /**
     * 查询用户信息的方法，给login register 使用
     *
     * @param userDTO 用户信息
     * @return
     */
    private User getUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(CodeEnum.CODE_500.getCode(), "系统错误");
        }
        return one;
    }

}
