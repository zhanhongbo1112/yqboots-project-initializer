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
package com.yqboots.initializer.core.builder;

import com.yqboots.initializer.core.DomainMetadata;
import com.yqboots.initializer.core.ProjectMetadata;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * This builder usually contains a velocity template for the file builder.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class FileTemplateBuilder implements FileBuilder, TemplateBuilder {
    /**
     * the velocity template.
     */
    private final String template;

    /**
     * the target path of the built file.
     */
    private final String path;

    /**
     * Constructs the FileTemplateBuilder.
     *
     * @param template the velocity template
     * @param path     the target path of the built file
     */
    public FileTemplateBuilder(final String template, final String path) {
        this.template = template;
        this.path = path;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    @Override
    public Path getFile(final Path root, final ProjectMetadata metadata) throws IOException {
        final Path result = Paths.get(root + getPath());
        if (Files.exists(result)) {
            Files.deleteIfExists(result);
        } else {
            Files.createDirectories(result.getParent());
        }
        Files.createFile(result);

        return result;
    }

    @Override
    public Path getFile(final Path root, final ProjectMetadata metadata, final DomainMetadata domainMetadata)
            throws IOException {
        final Path result = Paths.get(root + File.separator + domainMetadata.getModule() + getPath());
        if (Files.exists(result)) {
            Files.deleteIfExists(result);
        } else {
            Files.createDirectories(result.getParent());
        }
        Files.createFile(result);

        return result;
    }

    @Override
    public List<Path> getFiles(final Path root, final ProjectMetadata metadata, final InputStream inputStream)
            throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the target path for the built file.
     *
     * @return the target path
     */
    protected String getPath() {
        return path;
    }
}
