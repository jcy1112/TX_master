package com.springboot.controller.dto;

import lombok.Data;

/**
 * 接收前端登录请求的参数类
 * @Author jcy
 * @Date 2023/3/3 15:04
 */
@Data
public class UserDTO {

    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;

}
