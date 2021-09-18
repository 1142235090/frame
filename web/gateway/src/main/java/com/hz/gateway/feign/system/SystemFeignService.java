package com.hz.gateway.feign.system;

import com.hz.api.entity.SysSystem;
import com.hz.core.feign.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @explain
 * @Classname SystemFeignServices
 * @Date 2021/9/18 9:23
 * @Created by hanzhao
 */
@FeignClient(name = ServiceNameConstants.SYSTEM_SERVICE , fallbackFactory = RemoteSystemFactory.class)
public interface SystemFeignService  {
    @PostMapping("commonAct/startActBusiness")
    SysSystem startActBusiness(@RequestParam("systemId") Long systemId);
}
