package com.hz.system.service.impl;

import com.hz.api.entity.SysSystem;
import com.hz.system.dao.SysSystemDao;
import com.hz.system.service.SysSystemService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (SysSystem)表服务实现类
 *
 * @author makejava
 * @since 2021-09-10 15:18:34
 */
@Service
public class SysSystemServiceImpl implements SysSystemService {

    @Resource
    private SysSystemDao sysSystemDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysSystem queryById(Long id) {
        return sysSystemDao.selectById(id);
    }

    @Override
    @Cacheable(value = "userCache", key = "#id")
    public SysSystem testCache(Integer id) {
        SysSystem system = new SysSystem();
        system.setName("子系统1");
        return system;
    }

    @Override
    @CachePut(value = "userCache", key = "#id")
    public SysSystem updateCache(Integer id) {
        SysSystem system = new SysSystem();
        system.setName("子系统10086");
        return system;
    }

    @Override
    public int insert(SysSystem system) {
        return sysSystemDao.insert(system);
    }

    @Override
    public void deleteById(Long id) {
        sysSystemDao.deleteById(id);
    }
}
