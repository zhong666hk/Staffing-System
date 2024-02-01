package com.wbu.staff.config;

import com.wbu.staff.common.interceptor.EmployeeInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {
    // 必需要注入到容器中的。
    @Resource
    EmployeeInterceptor employeeInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(employeeInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/employee/login");
    }
}
