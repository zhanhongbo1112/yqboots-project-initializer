/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The entrance of a project.
 * it contains a main method to bootstrap the whole project.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Controller
@SpringBootApplication(scanBasePackages = {"com.yqboots.initializer", "com.yqboots.menu", "com.yqboots.dict", "com.yqboots.fss", "com.yqboots.web"})
@EnableJpaRepositories(basePackages = {"com.yqboots.v", "com.yqboots.menu", "com.yqboots.dict", "com.yqboots.fss", "com.yqboots.web"})
@EntityScan(basePackages = {"com.yqboots.initializer", "com.yqboots.menu", "com.yqboots.dict", "com.yqboots.fss", "com.yqboots.web"})
@ComponentScan(basePackages = {"com.yqboots.initializer", "com.yqboots.menu", "com.yqboots.dict", "com.yqboots.fss", "com.yqboots.web"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/")
    public String home() {
        return "redirect:projects/initializer";
    }
}
