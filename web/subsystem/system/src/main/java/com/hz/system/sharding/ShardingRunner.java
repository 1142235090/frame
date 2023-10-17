package com.hz.system.sharding;

import com.hz.api.entity.SysSystem;
import com.hz.system.service.SysSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @explain
 * @Classname ShardingRunner
 * @Date 2023/10/17 15:34
 * @Created by chrise warner
 */
@Component
public class ShardingRunner implements CommandLineRunner {

    @Autowired
    private SysSystemService sysSystemService;

    /**
     * 因为shrading使用了自定义的分片算法，
     * 就是不指定时间范围，按照业务需求插入如果没对应的那一片则创建表
     * 这样做不符合sharding要求，会导致项目启动没有写入则不会正确缓存表结构
     * 在这里写入一条语句并删除让数据缓存下来
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        // 1.system表
        SysSystem system = new SysSystem();
        system.setCreateTime(new Date());
        sysSystemService.insert(system);
        sysSystemService.deleteById(system.getId());
    }
}
