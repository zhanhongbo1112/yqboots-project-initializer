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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * The file builder is used to build the project file to the specified path.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public interface FileBuilder {
    /**
     * Gets the built file.
     *
     * @param root     the root path of file storage
     * @param metadata project metadata
     * @return the path of the built file
     * @throws IOException
     */
    Path getFile(Path root, ProjectMetadata metadata) throws IOException;

    /**
     * Gets the built file.
     *
     * @param root           the root path of file storage
     * @param metadata       project metadata
     * @param domainMetadata domain metadata
     * @return the path of the built file
     * @throws IOException
     */
    Path getFile(Path root, ProjectMetadata metadata, DomainMetadata domainMetadata) throws IOException;

    /**
     * Gets the built files.
     *
     * @param root        the root path of file storage
     * @param metadata    project metadata
     * @param inputStream the input stream for the Excel file
     * @return the paths of the built files
     * @throws IOException
     */
    List<Path> getFiles(Path root, ProjectMetadata metadata, InputStream inputStream) throws IOException;
}
