package org.json0368.tliassystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.json0368.tliassystem.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {
     @PostMapping("/upload")
    public Result upload(String name, Integer age, MultipartFile file) throws IOException {
        log.info("接收参数：姓名 = {} ，年龄 = {}；上传文件{}", name, age, file.getOriginalFilename());
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = UUID.randomUUID().toString() + "." + extension;
        file.transferTo(new File("E:\\code\\Project\\course\\JavaWeb\\upload\\" + newFileName));
        return Result.success();
    }
}
