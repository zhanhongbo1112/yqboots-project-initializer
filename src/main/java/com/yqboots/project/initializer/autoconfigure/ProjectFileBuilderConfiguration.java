package com.yqboots.project.initializer.autoconfigure;

import com.yqboots.project.dict.core.DataDict;
import com.yqboots.project.dict.core.DataDicts;
import com.yqboots.project.initializer.core.builder.excel.*;
import com.yqboots.project.menu.core.MenuItem;
import com.yqboots.project.menu.core.MenuItems;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
 * The configurations for project file builders.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties({MenuItemProperties.class, DataDictProperties.class, MessageProperties.class,
        DomainProperties.class, SystemProperties.class})
public class ProjectFileBuilderConfiguration {
    @Bean
    @ConditionalOnMissingBean(ProjectFileBuilder.class)
    public ProjectFileBuilder projectFileBuilder() throws IOException {
        List<SheetBuilder> builders = new ArrayList<>();
        builders.add(menuSheetBuilder());
        builders.add(dataDictSheetBuilder());
        builders.add(messageSheetBuilder());
        builders.add(systemPropertiesSheetBuilder());
        builders.add(domainSheetBuilder());

        return new ProjectFileBuilder(builders);
    }

    @Autowired
    private MenuItemProperties menuItemProperties;

    @Bean
    @ConditionalOnMissingBean(MenuItemSheetBuilder.class)
    public MenuItemSheetBuilder menuSheetBuilder() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(MenuItems.class, MenuItem.class);
        return new MenuItemSheetBuilder(marshaller, menuItemProperties);
    }

    @Autowired
    private DataDictProperties dataDictProperties;

    @Bean
    @ConditionalOnMissingBean(DataDictSheetBuilder.class)
    public DataDictSheetBuilder dataDictSheetBuilder() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(DataDicts.class, DataDict.class);
        return new DataDictSheetBuilder(marshaller, dataDictProperties);
    }

    @Autowired
    private MessageProperties messageProperties;

    @Bean
    @ConditionalOnMissingBean(MessageSheetBuilder.class)
    public MessageSheetBuilder messageSheetBuilder() {
        return new MessageSheetBuilder(messageProperties);
    }

    @Autowired
    private SystemProperties systemProperties;

    @Bean
    @ConditionalOnMissingBean(SystemPropertiesSheetBuilder.class)
    public SystemPropertiesSheetBuilder systemPropertiesSheetBuilder() {
        return new SystemPropertiesSheetBuilder(systemProperties);
    }

    @Autowired
    private DomainProperties domainProperties;

    @Bean
    @ConditionalOnMissingBean(DomainSheetBuilder.class)
    public DomainSheetBuilder domainSheetBuilder() throws IOException {
        return new DomainSheetBuilder(velocityEngine(), domainProperties);
    }

    private VelocityEngine velocityEngine() throws IOException {
        VelocityEngineFactoryBean factoryBean = new VelocityEngineFactoryBean();
        factoryBean.setResourceLoaderPath("classpath:/vm/domain/");
        factoryBean.setPreferFileSystemAccess(false);

        Properties velocityProperties = new Properties();
        velocityProperties.setProperty("input.encoding", Charset.forName("UTF-8").name());
        factoryBean.setVelocityProperties(velocityProperties);

        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }
}
