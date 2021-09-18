package com.hz.system.service;


import com.hz.api.entity.SysSystem;

/**
 * (SysSystem)表服务接口
 *
 * @author makejava
 * @since 2021-09-10 15:18:34
 */
public interface SysSystemService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysSystem queryById(Integer id);

}
