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

import com.yqboots.project.initializer.core.theme.Theme;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * The project initializer, which generates an enterprise Java project with metadata input.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public interface ProjectInitializer {
    /**
     * Generates project files by its metadata and theme.
     *
     * @param metadata the project metadata
     * @param theme    the theme
     * @return the path for all generated files
     * @throws IOException
     */
    Path startup(ProjectMetadata metadata, Theme theme) throws IOException;

    /**
     * Generates project files by its metadata and theme.
     *
     * @param metadata the project metadata
     * @param theme    the theme
     * @param file     the input stream which contains domain metadata
     * @return the path for all generated files
     * @throws IOException
     */
    Path startup(ProjectMetadata metadata, Theme theme, InputStream file) throws IOException;
}
