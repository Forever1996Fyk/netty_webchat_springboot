package com.javaweb.michaelkai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @program: netty_webchat
 * @description:
 * @author: YuKai Fan
 * @create: 2019-08-22 10:02
 **/
@SpringBootApplication
@MapperScan(basePackages = {"com.javaweb.michaelkai.dao"})
@ComponentScan(basePackages = {"com.javaweb.michaelkai"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}