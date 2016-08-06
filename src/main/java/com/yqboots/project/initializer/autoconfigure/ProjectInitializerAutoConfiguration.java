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
package com.yqboots.project.initializer.autoconfigure;

import com.yqboots.project.initializer.core.ProjectInitializer;
import com.yqboots.project.initializer.core.ProjectInitializerImpl;
import com.yqboots.project.initializer.core.builder.FileBuilder;
import com.yqboots.project.initializer.core.builder.FileBuilderImpl;
import com.yqboots.project.initializer.core.builder.JavaFileBuilder;
import com.yqboots.project.initializer.core.builder.ResourcesFileBuilder;
import com.yqboots.project.initializer.core.support.ProjectVelocityEngine;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2016-05-28.
 */
@Configuration
@EnableConfigurationProperties(ProjectInitializerProperties.class)
public class ProjectInitializerAutoConfiguration {
    @Autowired
    private ProjectInitializerProperties properties;

    @Bean
    public ProjectInitializer projectInitializer() throws Exception {
        return new ProjectInitializerImpl(velocityEngine(), properties);
    }

    private ProjectVelocityEngine velocityEngine() throws Exception {
        VelocityEngineFactoryBean factoryBean = new VelocityEngineFactoryBean() {
            @Override
            protected VelocityEngine newVelocityEngine() throws IOException, VelocityException {
                // custom velocity engine to include FileBuilders
                List<FileBuilder> builders = new ArrayList<>();
                builders.add(new FileBuilderImpl("pom.xml.vm", "/pom.xml"));
                builders.add(new JavaFileBuilder("Application.java.vm", "/Application.java"));
                builders.add(new ResourcesFileBuilder("layout.html.vm", "/templates/layouts/layout.html"));

                return new ProjectVelocityEngine(builders);
            }
        };
        factoryBean.setResourceLoaderPath("classpath:/vm/initializer/");
        factoryBean.setPreferFileSystemAccess(false);

        Properties velocityProperties = new Properties();
        velocityProperties.setProperty("input.encoding", Charset.forName("UTF-8").name());
        factoryBean.setVelocityProperties(velocityProperties);

        factoryBean.afterPropertiesSet();

        return (ProjectVelocityEngine) factoryBean.getObject();
    }
}
