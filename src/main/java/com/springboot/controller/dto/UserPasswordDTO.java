package com.springboot.controller.dto;

import lombok.Data;

/**
 * 接收前端修改密码请求的参数类
 * @Author jcy
 * @Date 2023/3/7 12:52
 */
@Data
public class UserPasswordDTO {

    private String username;
    private String password;
    private String newPassword;
}
