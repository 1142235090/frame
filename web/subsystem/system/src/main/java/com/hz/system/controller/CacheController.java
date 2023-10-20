package com.hz.system.controller;

import com.hz.system.annotation.RedisControllerCache;
import com.hz.system.service.SysSystemService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * @explain
 * @Classname test
 * @Date 2021/9/10 14:40
 * @Created by hanzhao
 */
@RestController
@RefreshScope
@RequestMapping("cache")
public class CacheController {

    @Autowired
    private SysSystemService sysSystemService;

    @SneakyThrows
    @RequestMapping(value = "/images", method = RequestMethod.GET)
    @RedisControllerCache
    public byte[] testCache(String fileName) {
        File file = new File("C:\\Users\\18833\\Pictures\\Saved Pictures\\" + fileName);
        return  Base64.getDecoder().decode(convertImageToBase64(file));
    }

    public static String convertImageToBase64(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fileInputStream.read(bytes);
        fileInputStream.close();
        return Base64.getEncoder().encodeToString(bytes);
    }
}
