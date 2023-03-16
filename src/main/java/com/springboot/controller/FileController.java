package com.springboot.controller;

import com.springboot.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件上传相关的接口
 *
 * @Author jcy
 * @Date 2023/3/4 14:36
 */
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 文件上传接口
     *
     * @param file
     * @return
     */
    @PostMapping("/ossupload")
    public String ossUpload(@RequestParam MultipartFile file) {
        return UploadUtil.uploadImage(file);
    }


}
