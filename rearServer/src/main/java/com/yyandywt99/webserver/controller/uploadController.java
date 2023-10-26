package com.yyandywt99.webserver.controller;

import com.yyandywt99.webserver.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author Yangyang
 * @create 2023-09-20 15:48
 */
@Slf4j
@RestController
public class uploadController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        String baseUrl = httpServletRequest.getRequestURL().toString();
        String requestUri = httpServletRequest.getRequestURI();
        String baseUrlWithoutPath = baseUrl.replace(requestUri, "");
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
        Path path = Paths.get(filePath);
            if (!Files.exists(path) || !Files.isDirectory(path)) {
                try {
                    Files.createDirectories(path);
                    System.out.println("目录已创建: " + path.toString());
                } catch (IOException e) {
                    System.err.println("无法创建目录: " + path.toString());
                    e.printStackTrace();
                }
            } else {
                System.out.println("目录已存在: " + path.toString());
            }
        // 使用相对路径保存文件
        file.transferTo(new File(filePath));
        return Result.success(baseUrlWithoutPath+"/image/" + filename);
    }
}

