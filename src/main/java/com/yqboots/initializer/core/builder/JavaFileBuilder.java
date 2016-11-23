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
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * It builds the template to a Java file.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class JavaFileBuilder extends FileTemplateBuilder {
    /**
     * The relative path for Java files.
     */
    public static final String ROOT = "/src/main/java";

    /**
     * Constructs the JavaFileBuilder.
     *
     * @param template the velocity template
     * @param path     the target path of the built file
     */
    public JavaFileBuilder(final String template, final String path) {
        super(template, path);
    }

    @Override
    public Path getFile(final Path root, final ProjectMetadata metadata) throws IOException {
        // dynamic calculate the java file path based on groupId
        final String groupPath = metadata.getGroupPath();
        final Path _root = Paths.get(root.toAbsolutePath() + ROOT + File.separator + groupPath);

        return super.getFile(_root, metadata);
    }

    @Override
    public Path getFile(final Path root, final ProjectMetadata metadata, final DomainMetadata domainMetadata)
            throws IOException {
        // dynamic calculate the java file path based on groupId
        final String groupPath = metadata.getGroupPath();
        final Path _root = Paths.get(root.toAbsolutePath() + ROOT + File.separator + groupPath + File.separator
                + domainMetadata.getModule());

        return super.getFile(_root, metadata);
    }
}
