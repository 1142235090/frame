package com.hz.task.lock;

import com.hz.task.lock.smile.VersionLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @explain
 * @Classname SmileLock
 * @Date 2022/1/13 16:13
 * @Created by hanzhao
 */
public class LockDemo {

    public static void main(String[] args) {
        // 版本号乐观锁
        test1();
    }

    /**
     * 版本号乐观锁
     */
    private static void test1(){
        //1.创建可固定长度的线程池
        ExecutorService newExecutorService = Executors.newFixedThreadPool(12);
        //创建了10个线程
        for (int i = 0; i < 100000; i++) {
            newExecutorService.execute(new VersionLock());
        }
    }
}
