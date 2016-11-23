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

import com.yqboots.fss.util.ZipUtils;
import com.yqboots.initializer.autoconfigure.ProjectInitializerProperties;
import com.yqboots.initializer.core.builder.FileBuilder;
import com.yqboots.initializer.core.builder.TemplateBuilder;
import com.yqboots.initializer.core.builder.excel.ProjectFileBuilder;
import com.yqboots.initializer.core.theme.Theme;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

/**
 * The project initializer implementation.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class ProjectInitializerImpl implements ProjectInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectInitializerImpl.class);

    /**
     * The velocity engine.
     */
    private final VelocityEngine velocityEngine;

    /**
     * List of file builders.
     */
    private final List<FileBuilder> fileBuilders;

    /**
     * The properties.
     */
    private final ProjectInitializerProperties properties;

    /**
     * Constructs the ProjectInitializerImpl.
     *
     * @param velocityEngine The velocity engine.
     * @param fileBuilders   List of file builders.
     * @param properties     The properties.
     */
    public ProjectInitializerImpl(final VelocityEngine velocityEngine, final List<FileBuilder> fileBuilders,
                                  final ProjectInitializerProperties properties) {
        this.velocityEngine = velocityEngine;
        this.fileBuilders = fileBuilders;
        this.properties = properties;
    }

    @Override
    public Path startup(final ProjectMetadata metadata, final Theme theme) throws IOException {
        return startup(metadata, theme, null);
    }

    @Override
    public Path startup(final ProjectMetadata metadata, final Theme theme, final InputStream file) throws IOException {
        Path targetPath = Paths.get(properties.getTargetPath() + File.separator + LocalDate.now()
                + File.separator + metadata.getName());
        if (!Files.exists(targetPath)) {
            LOG.info("Creating the target path: {}", targetPath);
            Files.createDirectories(targetPath);
        }

        final VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("metadata", metadata);
        velocityContext.put("theme", theme);
        velocityContext.put("StringUtils", StringUtils.class);

        for (FileBuilder builder : getFileBuilders()) {
            if (builder instanceof TemplateBuilder) {
                TemplateBuilder _builder = (TemplateBuilder) builder;
                if (!getVelocityEngine().resourceExists(_builder.getTemplate())) {
                    LOG.warn("Template {} not found, ignore...", _builder.getTemplate());
                    continue;
                }

                Template template = getVelocityEngine().getTemplate(_builder.getTemplate());
                try (Writer writer = new FileWriter(builder.getFile(targetPath, metadata).toFile())) {
                    // retrieve the root path of the target project
                    template.merge(velocityContext, writer);
                    writer.flush();
                }
            }

            if (file != null && builder instanceof ProjectFileBuilder) {
                ProjectFileBuilder _builder = (ProjectFileBuilder) builder;
                // call getFile to generate files
                _builder.getFiles(targetPath, metadata, file);
            }
        }

        // compress to one file for downloading
        return ZipUtils.compress(targetPath);
    }

    protected VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    protected List<FileBuilder> getFileBuilders() {
        return fileBuilders;
    }
}
