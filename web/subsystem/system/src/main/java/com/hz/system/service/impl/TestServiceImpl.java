package com.hz.system.service.impl;

import com.hz.api.entity.TestEntity;
import com.hz.system.dao.TestDao;
import com.hz.system.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @explain
 * @Classname TestServiceImpl
 * @Date 2023/10/20 14:43
 * @Created by chrise warner
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestDao testDao;

    @Override
    public TestEntity get(int id) {
        return testDao.selectById(id);
    }

    @Override
    public int insert(TestEntity entity) {
        return testDao.insert(entity);
    }
}
