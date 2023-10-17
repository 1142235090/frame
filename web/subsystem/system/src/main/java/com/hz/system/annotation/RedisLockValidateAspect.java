package com.hz.system.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @explain
 * @Classname RedisLockValidateAspect
 * @Date 2022/5/10 14:18
 * @Created by chrise warnner
 */
@Aspect
@Component
@Slf4j
public class RedisLockValidateAspect {

    @Autowired
    private Redisson redisson;

    @Around("@annotation(com.hz.system.annotation.RedisLockAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint) {
        // 获取接口的参数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 验证参数是否为空
        RedisLockAnnotation timesValidate = method.getAnnotation(RedisLockAnnotation.class);
        String lockName = timesValidate.name();
        try {
            if (lockName.equals("")) {
                throw new RuntimeException("注解参数为null！");
            }
            // 请求锁
            RLock redisLock = redisson.getLock(lockName);//创建锁
            if (!redisLock.isLocked()) {
                redisLock.lock();//加锁,默认10s超时，自动续命，自旋锁
                // 执行逻辑
                try {
                   return joinPoint.proceed();
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable.getMessage());
                } finally {
                    // 执行结束,释放锁
                    redisLock.unlock();
                }
            }else{
                log.info("抢占锁{}失败", lockName);
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
