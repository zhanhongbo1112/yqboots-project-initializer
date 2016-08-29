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
package com.yqboots.project.initializer.core.builder.excel;

import com.yqboots.project.initializer.core.ProjectMetadata;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.nio.file.Path;

/**
 * The sheet builders are used to build the sheets in an Excel, to retrieve domains, messages, menus, dicts, etc..
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public interface SheetBuilder {
    /**
     * Checks if the builder can read the sheet.
     *
     * @param sheet the Excel sheet
     * @return true if matches based on the data in the sheet
     */
    boolean supports(Sheet sheet);

    /**
     * If supports, read the sheet to retrieve metadata.
     *
     * @param root     the root path
     * @param metadata the project metadata
     * @param sheet    the working sheet
     * @throws IOException
     */
    void build(Path root, final ProjectMetadata metadata, Sheet sheet) throws IOException;
}
