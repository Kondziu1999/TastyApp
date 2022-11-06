package com.kondziu.projects.TastyAppBackend;

import com.kondziu.projects.TastyAppBackend.interceptors.LoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration()
public class Configurator implements WebMvcConfigurer {
    private LoggingInterceptor interceptor;
    public Configurator(LoggingInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(interceptor);
    }
}
