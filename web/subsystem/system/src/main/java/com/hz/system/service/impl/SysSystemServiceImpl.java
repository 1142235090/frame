package com.hz.system.service.impl;

import com.hz.system.dao.SysSystemDao;
import com.hz.api.entity.SysSystem;
import com.hz.system.service.SysSystemService;
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
    public SysSystem queryById(Integer id) {
        return sysSystemDao.selectById(id);
    }
}
