package com.hz.system.service;

import com.hz.api.entity.TestEntity;

/**
 * @explain
 * @Classname TestService
 * @Date 2023/10/20 14:42
 * @Created by chrise warner
 */
public interface TestService {
    TestEntity get(int id);
    int insert(TestEntity entity);
}
