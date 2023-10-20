package com.hz.system.annotation;

import java.lang.annotation.*;

/**
 * @explain 只能标注在controller层方法上边，因为会获取所有请求参数并拼接成为key
 * @Classname ControllerCache
 * @Date 2023/10/17 16:44
 * @Created by chrise warner
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // 由JVM 加载，包含在类文件中，在运行时可以被获取到
@Inherited // 注解继承
@Documented
public @interface RedisControllerCache {

    /**
     * 缓存时效，单位是分钟
     *
     * @return
     */
    int cacheTime() default 60;
}
