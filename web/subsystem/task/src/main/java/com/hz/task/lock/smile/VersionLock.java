package com.hz.task.lock.smile;

import com.hz.task.lock.LockTeatBean;
import lombok.SneakyThrows;

/**
 * @explain
 * @Classname VersionLock
 * @Date 2022/1/13 17:00
 * @Created by hanzhao
 */
public class VersionLock implements Runnable {

    @SneakyThrows
    @Override
    public void run() {
        // 获取吴亦凡的变量
        LockTeatBean wyf = LockTeatBean.getBean();
        // 加锁之后能避免此项错误
//        synchronized (wyf) {
            // wyf心情咋样啊，昨天笑今天就该哭了，昨天要是哭，今天赏他一个笑脸
            Boolean smile;
            if (wyf.isSmile()) {
                smile = false;
            } else {
                smile = true;
            }
            // 版本号增加，一旦版本号不一致说明已经被修改过了，本次修改则失败重新修改
            int version = wyf.getVersion();
            // 更新数据
            if (wyf.setBean(smile, version)) {
//            System.out.println("11111更新成功");
            } else {
                System.out.println("更新失败22222");
                // 在这里可以做错误的处理
            }
        }
//    }
}
