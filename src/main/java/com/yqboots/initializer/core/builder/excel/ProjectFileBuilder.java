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
package com.yqboots.initializer.core.builder.excel;

import com.yqboots.initializer.core.DomainMetadata;
import com.yqboots.initializer.core.ProjectMetadata;
import com.yqboots.initializer.core.builder.FileBuilder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The entrance for all sheet builders.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class ProjectFileBuilder implements FileBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectFileBuilder.class);

    private final List<SheetBuilder> sheetBuilders;

    /**
     * Constructs the ProjectFileBuilder.
     *
     * @param sheetBuilders list of SheetBuilder
     */
    public ProjectFileBuilder(final List<SheetBuilder> sheetBuilders) {
        this.sheetBuilders = sheetBuilders;
    }

    @Override
    public Path getFile(final Path root, final ProjectMetadata metadata) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Path getFile(final Path root, final ProjectMetadata metadata, final DomainMetadata domainMetadata) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Path> getFiles(final Path root, final ProjectMetadata metadata, InputStream inputStream) throws IOException {
        final List<Path> results = new ArrayList<>();
        try (
                XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(inputStream))
        ) {
            for (final Sheet sheet : workbook) {
                // one sheet can be processed by more than one builder
                for (SheetBuilder builder : sheetBuilders) {
                    if (builder.supports(sheet)) {
                        builder.build(root, metadata, sheet);
                    }
                }
            }

            return results;
        } catch (InvalidFormatException e) {
            LOG.error(e.getMessage(), e);
            throw new IOException(e);
        }
    }
}
