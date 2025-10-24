package com.chenyi.safe;

import com.chenyi.safe.config.CorsConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(CorsConfig.class)
@MapperScan("com.chenyi.safe.mapper")
//@ComponentScan(basePackages = {"com.chenyi.safe.mapper"})
public class SafeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SafeApplication.class, args);
    }

}
