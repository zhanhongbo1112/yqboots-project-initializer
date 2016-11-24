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

import com.yqboots.fss.core.support.FileType;
import com.yqboots.initializer.core.ProjectMetadata;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
    private static final String FILE_NAME_PREFIX = "application";

    private static final String PROFILE_DEFAULT = "default";

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
        Assert.isTrue(StringUtils.equalsIgnoreCase(keyCell.getStringCellValue(), "Name"),
                "Column 'Name' is required");
        final Cell valueCell = row.getCell(1);
        Assert.isTrue(StringUtils.equalsIgnoreCase(valueCell.getStringCellValue(), "Profile"),
                "Column 'Profile' is required");
    }

    @Override
    protected void doBuild(final Path root, final ProjectMetadata metadata, final Sheet sheet) throws IOException {
        final List<String> profiles = getProfiles(sheet.getRow(1));
        for (int i = 1; i <= profiles.size(); i++) {
            String profile = profiles.get(i - 1);
            String fileName = FILE_NAME_PREFIX + FileType.DOT_PROPERTIES;
            if (!PROFILE_DEFAULT.equals(profile)) {
                fileName = FILE_NAME_PREFIX + "-" + profile + FileType.DOT_PROPERTIES;
            }

            final Path targetPath = Paths.get(root + File.separator + properties.getExportRelativePath());
            if (!Files.exists(targetPath)) {
                Files.createDirectories(targetPath);
            }
            try (final FileWriter writer = new FileWriter(Paths.get(targetPath + File.separator + fileName).toFile())) {
                for (final Row row : sheet) {
                    // ignore the first and second rows
                    if (row.getRowNum() < 2) {
                        continue;
                    }

                    // set comment
                    final Cell cell = row.getCell(3);
                    if (cell != null) {
                        writer.write("#" + cell.getStringCellValue() + "\r\n");
                    }

                    final Cell cell0 = row.getCell(0);
                    final Cell celli = row.getCell(i);
                    if (cell0 != null && celli != null) {
                        writer.write(cell0.getStringCellValue() + "=" + celli.getStringCellValue() + "\r\n");
                    }
                }
            }
        }
    }

    private static List<String> getProfiles(Row row) {
        final List<String> results = new ArrayList<>();
        for (final Cell cell : row) {
            if (cell.getColumnIndex() == 0) {
                continue;
            }

            final String profile = cell.getStringCellValue();
            if (StringUtils.isBlank(profile)) {
                break;
            }

            results.add(profile);
        }

        Assert.isTrue(!results.isEmpty());
        return results;
    }
}
