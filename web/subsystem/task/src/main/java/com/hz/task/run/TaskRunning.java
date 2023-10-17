package com.hz.task.run;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @explain
 * @Classname TaskRunning
 * @Date 2022/5/18 17:06
 * @Created by chrise warnner
 */
//@Component
//@EnableAsync
public class TaskRunning {

    @Async
    @Scheduled(cron = "0/10 * * * * *")
    public void test1() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("test1");
    }

    @Async
    @Scheduled(cron = "0/10 * * * * *")
    public void test2() throws InterruptedException {
        Thread.sleep(10000);
        System.out.println("test2");
    }

    @Async
    @Scheduled(cron = "0/10 * * * * *")
    public void test3() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("test3");
    }

    @Async
    @Scheduled(cron = "0/10 * * * * *")
    public void test4() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("test4");
    }

    /**
     * 使用自定义线程池，
     * 禁止使用spring自带的线程
     *
     * @return
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(16);
        return taskScheduler;
    }
}
