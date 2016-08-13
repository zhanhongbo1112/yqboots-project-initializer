package com.yqboots.project.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016-05-18.
 */
@Controller
@SpringBootApplication(scanBasePackages = {"com.yqboots"})
@EnableJpaRepositories(basePackages = {"com.yqboots"})
@EntityScan(basePackages = {"com.yqboots"})
@ComponentScan(basePackages = {"com.yqboots"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/")
    public String home() {
        return "redirect:project/initializer";
    }
}
