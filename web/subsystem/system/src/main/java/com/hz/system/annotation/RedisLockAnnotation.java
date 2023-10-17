package com.hz.system.annotation;

import java.lang.annotation.*;

/**
 * @explain
 * @Classname RedisLockAnnotation
 * @Date 2022/5/10 13:50
 * @Created by chrise warnner
 */
@Target(ElementType.METHOD) // 应用于方法之中
@Retention(RetentionPolicy.RUNTIME) // 由JVM 加载，包含在类文件中，在运行时可以被获取到
@Inherited // 注解继承
@Documented
public @interface RedisLockAnnotation {

    /**
     * 锁名称
     * 如果有值则去redis中请求锁
     * 一旦请求失败则不继续往下执行
     *
     * @return
     */
    String name() default "";
}
