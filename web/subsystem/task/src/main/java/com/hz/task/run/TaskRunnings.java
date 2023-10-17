package com.hz.task.run;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * @explain
 * @Classname TaskRunning
 * @Date 2022/5/18 17:06
 * @Created by chrise warnner
 */
//@Component
public class TaskRunnings {

    @Scheduled(cron = "0/10 * * * * *")
    public void test1() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("!---test1");
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void test2() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("!---test2");
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void test3() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("!---test3");
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void test4() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("!---test4");
    }

//    /**
//     * 使用自定义线程池，
//     * 禁止使用spring自带的线程
//     *
//     * @return
//     */
//    @Bean
//    public TaskScheduler taskScheduler() {
//        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
//        taskScheduler.setPoolSize(16);
//        return taskScheduler;
//    }
}
