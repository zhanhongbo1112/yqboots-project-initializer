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
package com.yqboots.project.initializer.core.builder;

import com.yqboots.project.initializer.core.DomainMetadata;
import com.yqboots.project.initializer.core.ProjectMetadata;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * It builds the template to a resource file.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class ResourcesFileBuilder extends FileTemplateBuilder {
    /**
     * The relative path for resource files.
     */
    public static final String ROOT = "/src/main/resources";

    /**
     * Constructs the ResourcesFileBuilder.
     *
     * @param template the velocity template
     * @param path     the target path of the built file
     */
    public ResourcesFileBuilder(final String template, final String path) {
        super(template, path);
    }

    @Override
    public Path getFile(final Path root, final ProjectMetadata metadata) throws IOException {
        final Path _root = Paths.get(root.toAbsolutePath() + ROOT);

        return super.getFile(_root, metadata);
    }

    @Override
    public Path getFile(final Path root, final ProjectMetadata metadata, final DomainMetadata domainMetadata)
            throws IOException {
        return this.getFile(root, metadata);
    }
}
