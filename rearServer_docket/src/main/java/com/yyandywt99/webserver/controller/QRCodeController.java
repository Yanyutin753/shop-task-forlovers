package com.yyandywt99.webserver.controller;
import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.service.QRCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
public class QRCodeController {
    @Autowired
    private HttpServletRequest httpServletRequest;

    private final QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/generateQRCode")
    public Result generateQRCode(@RequestParam String text) {
        try {
            String baseUrl = httpServletRequest.getRequestURL().toString();
            String requestUri = httpServletRequest.getRequestURI();
            String baseUrlWithoutPath = baseUrl.replace(requestUri, "");
            log.info(baseUrlWithoutPath);
            String filename = UUID.randomUUID().toString() + ".jpg";
            String projectRoot = System.getProperty("user.dir");
            String relativePath = "target/classes/static/image/Qrc/" + filename;
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
            qrCodeService.generateQRCode(text, filePath);
            return Result.success(baseUrlWithoutPath+"/image/Qrc/"+filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("上传失败");
    }
}
