package com.hz.task.fake_syn_io;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @explain
 * @Classname ServerHandlerExecutePool
 * @Date 2022/1/6 17:32
 * @Created by hanzhao
 */
public class ServerHandlerExecutePool {

    private ExecutorService  executor;

    /**
     * 根据参数创建线程池
     * @param maxPool 最大线程数
     * @param queSize 队列长度
     */
    public ServerHandlerExecutePool(int maxPool ,int queSize) {

        executor = new ThreadPoolExecutor(
                4,// 核心线程
                maxPool,// 最大线程
                120L,// 多少秒回收
                TimeUnit.SECONDS,// 单位
                new ArrayBlockingQueue<>(queSize));// 队列
    }

    public void execute(Runnable task){
        executor.execute(task);
    }
}
