package com.example.TexasHamburgComp.config;

import com.example.TexasHamburgComp.interceptor.ApiExecTimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ProductRegistryConfig implements WebMvcConfigurer {

    @Autowired
    ApiExecTimeInterceptor apiExecTimeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiExecTimeInterceptor);
    }
}
