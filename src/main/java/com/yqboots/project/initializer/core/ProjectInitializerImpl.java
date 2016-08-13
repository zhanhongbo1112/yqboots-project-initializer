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
package com.yqboots.project.initializer.core;

import com.yqboots.fss.util.ZipUtils;
import com.yqboots.project.initializer.autoconfigure.ProjectInitializerProperties;
import com.yqboots.project.initializer.core.builder.FileBuilder;
import com.yqboots.project.initializer.core.builder.excel.ProjectFileBuilder;
import com.yqboots.project.initializer.core.builder.TemplateBuilder;
import com.yqboots.project.initializer.core.support.ProjectVelocityEngine;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Administrator on 2016-05-28.
 */
public class ProjectInitializerImpl implements ProjectInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectInitializerImpl.class);

    private ProjectVelocityEngine velocityEngine;

    private ProjectInitializerProperties properties;

    public ProjectInitializerImpl(ProjectVelocityEngine velocityEngine, ProjectInitializerProperties properties) {
        this.velocityEngine = velocityEngine;
        this.properties = properties;
    }

    @Override
    public Path startup(final ProjectContext context) throws IOException {
        Path sourcePath = Paths.get(properties.getSourcePath());
        if (!Files.exists(sourcePath)) {
            throw new FileNotFoundException("The source path not found, " + properties.getSourcePath());
        }

        Path targetPath = Paths.get(properties.getTargetPath() + File.separator + System.currentTimeMillis()
                + File.separator + context.getMetadata().getName());
        if (!Files.exists(targetPath)) {
            LOG.info("Creating the target path: {}", targetPath);
            targetPath = Files.createDirectories(targetPath);
        }

        // copy shared resources to target path
        FileUtils.copyDirectory(sourcePath.toFile(), targetPath.toFile());

        final VelocityContext velocityContext = new VelocityContext();
        velocityContext.put(ProjectContext.KEY, context);
        velocityContext.put("StringUtils", StringUtils.class);

        final List<FileBuilder> builders = getVelocityEngine().getBuilders();
        for (FileBuilder builder : builders) {
            if (builder instanceof TemplateBuilder) {
                TemplateBuilder _builder = (TemplateBuilder) builder;
                if (!getVelocityEngine().resourceExists(_builder.getTemplate())) {
                    LOG.warn("Template {} not found, ignore...", _builder.getTemplate());
                    continue;
                }

                Template template = getVelocityEngine().getTemplate(_builder.getTemplate());

                try (Writer writer = new FileWriter(builder.getFile(targetPath, context).toFile())) {
                    // retrieve the root path of the target project
                    template.merge(velocityContext, writer);
                    writer.flush();
                }
            }

            if (builder instanceof ProjectFileBuilder) {
                ProjectFileBuilder _builder = (ProjectFileBuilder) builder;
                // call getFile to generate files
                _builder.getFiles(targetPath, context);
            }
        }

        // compress to one file for downloading
        return ZipUtils.compress(targetPath);
    }

    protected ProjectVelocityEngine getVelocityEngine() {
        return velocityEngine;
    }
}
