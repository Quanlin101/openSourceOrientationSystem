package com.lig.orientationSystem.config;

import com.lig.orientationSystem.until.RefreshAccess;
import com.lig.orientationSystem.until.handler.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor())
//                .excludePathPatterns("/myLogin")
//                .excludePathPatterns("/interviewee/**")
//                .excludePathPatterns("/interviewee")
//                .addPathPatterns("/**")
//                .excludePathPatterns("/myLogin")
//                .excludePathPatterns("/WXLogin")
//                .excludePathPatterns("/interviewee")
//                .excludePathPatterns("/administrator")
                .addPathPatterns("/interviewer/**")
        ;
    }

    @Bean
    public SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }

    //    //设置默认跳转到spring security提供的登录界面
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("redirect:/login-view");
//        registry.addViewController("/login-view").setViewName("myLogin");
//
//    }
    @Autowired
    RestTemplateBuilder templateBuilder;

    @Bean
    public RestTemplate restTemplate() {
        return templateBuilder.build();
    }

    @Autowired
    RefreshAccess refreshAccess;
}
