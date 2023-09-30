package com.yyandywt99.webserver.controller;

import com.yyandywt99.webserver.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author Yangyang
 * @create 2023-09-20 15:48
 */
@Slf4j
@RestController
public class uploadController {


    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传：{}", file);

        String originalFilename = file.getOriginalFilename();

        int index = originalFilename.lastIndexOf(".");

        String filename = UUID.randomUUID().toString() + originalFilename.substring(index);

        // 使用相对路径保存文件
        String relativePath = "target/classes/static/image/" + filename;

        // 获取项目根目录的绝对路径
        String projectRoot = System.getProperty("user.dir");

        // 构建文件保存的完整路径
        String filePath = projectRoot + File.separator + relativePath;

        // 使用相对路径保存文件
        file.transferTo(new File(filePath));

        return Result.success("http://localhost:8081/image/" + filename);
    }

}

