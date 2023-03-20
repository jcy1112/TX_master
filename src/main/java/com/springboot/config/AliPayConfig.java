package com.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @Author jcy
 * @Date 2023/3/19 20:13
 */
@Data
@Component
@ConfigurationProperties(prefix = "alipay")  //读取配置文件中的配置
public class AliPayConfig {
    private String appId;  //appid
    private String appPrivateKey;  //应用私钥
    private String alipayPublicKey;  //支付宝公钥
    private String notifyUrl;

}