package com.hz.gateway.feign.system;

import com.hz.api.entity.SysSystem;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @explain
 * @Classname RemoteSystemFactory
 * @Date 2021/9/18 13:45
 * @Created by hanzhao
 */
@Component
public class RemoteSystemFactory implements FallbackFactory<SystemFeignService> {
    @Override
    public SystemFeignService create(Throwable throwable) {
        return new SystemFeignService(){
            @Override
            public SysSystem startActBusiness(Long systemId) {
                return null;
            }
        };
    }
}
