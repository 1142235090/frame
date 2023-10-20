package com.hz.system.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @explain 只能标注在controller层方法上边，因为会获取所有请求参数并拼接成为key
 * @Classname ControllerCache
 * @Date 2023/10/17 16:44
 * @Created by chrise warner
 */
@Aspect
@Component
@Slf4j
public class RedisControllerCacheAspect {

    @Autowired
    private Redisson redisson;

    @Around("@annotation(com.hz.system.annotation.RedisControllerCache)")
    public Object around(ProceedingJoinPoint joinPoint) {
        String redisKey = "";
        // 获取类名
        String className = joinPoint.getTarget().getClass().getName();
        redisKey += className;
        // 获取方法名称
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();
        redisKey += "_" + methodName;
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            // 获取参数对应的值
            redisKey += "_" + args[i];
        }
        // 验证缓存中是否存在,不存在则执行方法
        RBucket<Object> controllerCache = redisson.getBucket(redisKey);
        Object cache = controllerCache.get();
        if (cache != null) {
            return cache;
        }
        // 执行逻辑
        Object returnStr;
        try {
            returnStr = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        // 计算数据需要缓存的时间
        RedisControllerCache timesValidate = method.getAnnotation(RedisControllerCache.class);
        int cacheTime = timesValidate.cacheTime();
        controllerCache.set(returnStr, cacheTime, TimeUnit.MINUTES);
        return returnStr;
    }
}
