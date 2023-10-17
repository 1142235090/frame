package com.hz.system.controller;

import com.hz.api.entity.SysSystem;
import com.hz.system.annotation.RedisLockAnnotation;
import com.hz.system.service.SysSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @explain
 * @Classname test
 * @Date 2021/9/10 14:40
 * @Created by hanzhao
 */
@RestController
@RefreshScope
@RequestMapping("test")
public class SystemController {

    @Value("${useLocalCache}")
    private String useLocalCache;

    @Autowired
    private SysSystemService sysSystemService;

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public String getNacosProperties() {
        return useLocalCache;
    }

    @RedisLockAnnotation(name = "getLock")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get() {
        return "hello world";
    }

    @RequestMapping(value = "/system", method = RequestMethod.GET)
    public SysSystem getSystem() {
        return sysSystemService.queryById(0L);
    }

    @RequestMapping(value = "/system", method = RequestMethod.POST)
    public int insert(@RequestBody SysSystem system) {
        return sysSystemService.insert(system);
    }

    @RequestMapping(value = "/test/cache", method = RequestMethod.GET)
    public SysSystem testCache() {
        return sysSystemService.queryById(1L);
    }
}
