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
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * It builds the sheet for system properties..
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class SystemPropertiesSheetBuilder extends AbstractSheetBuilder {
    /**
     * The file name for system properties. defaults to "application.properties"
     */
    private static final String FILE_NAME = "application.properties";

    /**
     * The system properties.
     */
    private final SystemProperties properties;

    /**
     * Constructs the SystemPropertiesSheetBuilder.
     *
     * @param properties the system properties
     */
    public SystemPropertiesSheetBuilder(final SystemProperties properties) {
        super(properties.getSheetName());
        this.properties = properties;
    }

    @Override
    protected void formatChecking(final Sheet sheet) {
        // get the title row
        final Row row = sheet.getRow(0);
        final Cell keyCell = row.getCell(0);
        Assert.isTrue(StringUtils.equalsIgnoreCase(keyCell.getStringCellValue(), "name"),
                "Column 'name' is required");
        final Cell valueCell = row.getCell(1);
        Assert.isTrue(StringUtils.equalsIgnoreCase(valueCell.getStringCellValue(), "value"),
                "Column 'value' is required");
    }

    @Override
    protected void doBuild(final Path root, final ProjectMetadata metadata, final Sheet sheet) throws IOException {
        final Path path = Paths.get(root + File.separator + properties.getExportRelativePath());
        try (final FileWriter writer = new FileWriter(Paths.get(path + File.separator + FILE_NAME).toFile())) {
            for (final Row row : sheet) {
                // ignore the first row
                if (row.getRowNum() < 1) {
                    continue;
                }

                // set comment
                final Cell cell = row.getCell(2);
                if (cell != null) {
                    writer.write("#" + cell.getStringCellValue() + "\r\n");
                }

                final Cell cell0 = row.getCell(0);
                final Cell cell1 = row.getCell(1);
                if (cell != null && cell1 != null) {
                    writer.write(cell0.getStringCellValue() + "=" + cell1.getStringCellValue() + "\r\n");
                }
            }
        }
    }
}
