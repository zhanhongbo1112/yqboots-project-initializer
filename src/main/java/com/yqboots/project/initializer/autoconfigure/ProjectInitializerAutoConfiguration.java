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
import com.yqboots.project.initializer.core.builder.*;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@EnableConfigurationProperties({ProjectInitializerProperties.class})
public class ProjectInitializerAutoConfiguration {
    @Autowired
    private ProjectInitializerProperties properties;

    @Autowired
    private ProjectFileBuilderConfiguration configuration;

    @Bean
    @ConditionalOnMissingBean(ProjectInitializer.class)
    public ProjectInitializer projectInitializer() throws Exception {
        return new ProjectInitializerImpl(velocityEngine(), fileBuilders(), properties);
    }

    private List<FileBuilder> fileBuilders() throws IOException {
        List<FileBuilder> builders = new ArrayList<>();
        builders.add(new FileTemplateBuilder("pom.xml.vm", "/pom.xml"));
        builders.add(new JavaFileBuilder("Application.java.vm", "/Application.java"));
        builders.add(new ResourcesFileBuilder("layout.html.vm", "/templates/layouts/layout.html"));
        builders.add(configuration.projectFileBuilder());

        return builders;
    }

    private VelocityEngine velocityEngine() throws Exception {
        VelocityEngineFactoryBean factoryBean = new VelocityEngineFactoryBean();
        factoryBean.setResourceLoaderPath("classpath:/vm/initializer/");
        factoryBean.setPreferFileSystemAccess(false);

        Properties velocityProperties = new Properties();
        velocityProperties.setProperty("input.encoding", Charset.forName("UTF-8").name());
        factoryBean.setVelocityProperties(velocityProperties);

        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }
}
