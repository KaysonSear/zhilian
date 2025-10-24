package com.chenyi.safe.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * safe
 * 2024/6/13 下午10:25
 * 跨域问题
 *
 * @author chenyi
 * @since
 **/
@Configuration
public class CorsConfig {


    // 当前跨域请求最大有效时长。这里默认1天
    private static final long Max_AGE = 24 * 60 * 60;

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");//设置访问源地址
        corsConfiguration.addAllowedHeader("*");//设置源请求头
        corsConfiguration.addAllowedMethod("*");//设置源请求方法
        corsConfiguration.setMaxAge(Max_AGE);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());//对接口配置跨域设置
        return new CorsFilter(source);
    }
}
