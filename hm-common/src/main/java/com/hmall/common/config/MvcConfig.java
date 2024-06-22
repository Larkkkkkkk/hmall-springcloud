package com.hmall.common.config;
import com.hmall.common.interceptors.UserInfoInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration  //内部有component注入bean
@ConditionalOnClass(DispatcherServlet.class)  //只要是有SpringMVC的微服务才可以生效【网关就不会有】
public class MvcConfig implements WebMvcConfigurer {
    //将自定义的拦截器添加进来
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInfoInterceptor());
    }
}