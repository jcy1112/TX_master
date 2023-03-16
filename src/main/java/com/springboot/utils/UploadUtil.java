package com.springboot.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * oss文件上传
 */
public class UploadUtil {
    //阿里域名
    public static final String ALI_DOMAIN = "https://wt-mall.oss-cn-chengdu.aliyuncs.com/";

    public static String uploadImage(MultipartFile file) {
        //生成文件名
        String originalFilename = file.getOriginalFilename(); //原来的图片名
        String ext = "." + FilenameUtils.getExtension(originalFilename);//获取文件名后缀.png
        String uuid = UUID.randomUUID().toString().replace("-", "");//随机获取一串字符串
        String fileName = uuid + ext;//随机生成的字符串加后缀就是新文件名
        //地域节点
        String endpoint = "http://oss-cn-chengdu.aliyuncs.com";
        String accessKeyId = "LTAI5tE1hJNk24PC8xFxRhXN";
        String accessKeySecret = "kGJ1faCyblbWNRuDNVTwdlyqLA0eae";
        //OSS客户端对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.putObject(
                    "wt-mall",   //仓库名
                    fileName,      //文件名
                    file.getInputStream()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        ossClient.shutdown();
        return ALI_DOMAIN + fileName;
    }
}
