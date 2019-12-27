package net.xdclass.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 */
@Configuration
public class CustomWebMvcConfigure implements WebMvcConfigurer {
    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //注册拦截器，拦截/v2/*/** 表示，/v2路径后的所有请求
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/v2/*/**");

        //添加第二拦截器，安装先后顺序（一层层包围）方式执行
        //interceptor1---interceptor2---controller---interceptor2---interceptor1
        //过滤器---拦截器---action方法---拦截器---过滤器
        registry.addInterceptor(new TwoInterceptor()).addPathPatterns("/v2/*/**");

        //
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
