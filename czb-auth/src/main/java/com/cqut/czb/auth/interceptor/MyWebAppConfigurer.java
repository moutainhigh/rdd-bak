package com.cqut.czb.auth.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {
    @Autowired
    MyInterceptor interceptor;

    @Autowired
    PrivilegeInterceptor privilegeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 多个拦截器组成一个拦截器链

        // addPathPatterns 用于添加拦截规则

        // excludePathPatterns 用户排除拦截

        registry.addInterceptor(interceptor).addPathPatterns("/**");


//        registry.addInterceptor(privilegeInterceptor).addPathPatterns("/api/**");

        super.addInterceptors(registry);
    }
}
