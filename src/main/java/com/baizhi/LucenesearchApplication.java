package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.baizhi.dao")
public class LucenesearchApplication {


    public static void main(String[] args) {

        SpringApplication.run(LucenesearchApplication.class, args);
    }

}
