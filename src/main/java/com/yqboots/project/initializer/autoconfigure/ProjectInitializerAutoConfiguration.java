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

import com.yqboots.project.dict.core.DataDict;
import com.yqboots.project.dict.core.DataDicts;
import com.yqboots.project.initializer.core.ProjectInitializer;
import com.yqboots.project.initializer.core.ProjectInitializerImpl;
import com.yqboots.project.initializer.core.builder.*;
import com.yqboots.project.initializer.core.builder.excel.*;
import com.yqboots.project.initializer.core.support.ProjectVelocityEngine;
import com.yqboots.project.menu.core.MenuItem;
import com.yqboots.project.menu.core.MenuItems;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
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

    @Bean
    public ProjectFileBuilder projectFileBuilder() {
        List<SheetBuilder> builders = new ArrayList<>();
        builders.add(menuSheetBuilder());
        builders.add(dataDictSheetBuilder());
        builders.add(new MessageSheetBuilder());

        return new ProjectFileBuilder(builders);
    }

    @Bean
    public MenuSheetBuilder menuSheetBuilder() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(MenuItems.class, MenuItem.class);
        return new MenuSheetBuilder(marshaller);
    }

    @Bean
    public DataDictSheetBuilder dataDictSheetBuilder() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(DataDicts.class, DataDict.class);
        return new DataDictSheetBuilder(marshaller);
    }

    private ProjectVelocityEngine velocityEngine() throws Exception {
        VelocityEngineFactoryBean factoryBean = new VelocityEngineFactoryBean() {
            @Override
            protected VelocityEngine newVelocityEngine() throws IOException, VelocityException {
                // custom velocity engine to include FileBuilders
                List<FileBuilder> builders = new ArrayList<>();
                builders.add(new FileTemplateBuilder("pom.xml.vm", "/pom.xml"));
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
