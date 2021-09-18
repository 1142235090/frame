package com.hz.gateway.filter;

import com.hz.gateway.feign.system.SystemFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * @explain
 * @Classname Gatewayfilter
 * @Date 2021/9/16 16:50
 * @Created by hanzhao
 */
@Component
public class GatewayFilter implements GlobalFilter {

    @Autowired
    SystemFeignService systemFeignService;

    // 白名单，用于跳过某些请求
    private static final String[] whiteList = {
            "/system/config"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();
        System.out.println("网关:" + url);
        //验证测试路径如果为system/1，则执行远程调用
        if (url.equals("/test/system")) {
            throw new RuntimeException("这个不是错误！子系统为："+systemFeignService.getSystemById(1L).toString());
        }
        // 这里可以用来做用户登录验证
        // 跳过不需要验证的路径
        if (Arrays.asList(whiteList).contains(url)) {
            // 传递给下一个过滤器
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
//        throw  new RuntimeException("鉴权错误");
    }
}
