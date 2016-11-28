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
package com.yqboots.initializer.core;

import com.yqboots.Application;
import com.yqboots.initializer.core.theme.Theme;
import com.yqboots.initializer.core.theme.ThemeColor;
import com.yqboots.initializer.core.theme.ThemeSkin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class ProjectInitializerTest {
    private static final String TEMPLATE_PATH = "com/yqboots/initializer/core/workbook.xlsx";

    @Autowired
    private ProjectInitializer initializer;

    @Test
    public void testStartup() throws Exception {
        ProjectMetadata metadata = new ProjectMetadata();
        metadata.setGroupId("com.yqboots.test");
        metadata.setArtifactId("test-core");
        metadata.setName("Test Project");
        metadata.setDescription("Test Description");

        Theme theme = new Theme();
        theme.setSkin(ThemeSkin.LIGHT);
        theme.setColor(ThemeColor.GREEN);

        Resource resource = new ClassPathResource(TEMPLATE_PATH);
        try (InputStream inputStream = resource.getInputStream()) {
            initializer.startup(metadata, theme, inputStream);
        }
    }
}