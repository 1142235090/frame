package com.hz.system.controller;

import com.hz.api.entity.SysSystem;
import com.hz.api.entity.TestEntity;
import com.hz.system.annotation.RedisControllerCache;
import com.hz.system.service.SysSystemService;
import com.hz.system.service.TestService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping( method = RequestMethod.GET)
    public TestEntity getSystem() {
        return testService.get(0);
    }

    @RequestMapping(method = RequestMethod.POST)
    public int insert(@RequestBody TestEntity testEntity) {
        return testService.insert(testEntity);
    }
}