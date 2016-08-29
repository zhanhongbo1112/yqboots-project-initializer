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

import com.yqboots.project.fss.core.support.FileType;
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
import java.util.ArrayList;
import java.util.List;

/**
 * It builds the Excel sheet to retrieve messages.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class MessageSheetBuilder extends AbstractSheetBuilder {
    private static final String PREFIX = "messages";

    private final MessageProperties properties;

    /**
     * Constructs the MessageSheetBuilder.
     *
     * @param properties properties for MenuItem
     */
    public MessageSheetBuilder(final MessageProperties properties) {
        super(properties.getSheetName());
        this.properties = properties;
    }

    @Override
    protected void formatChecking(final Sheet sheet) {
        // get the title row
        Row row = sheet.getRow(0);
        Cell keyCell = row.getCell(0);
        Assert.isTrue(StringUtils.equalsIgnoreCase(keyCell.getStringCellValue(), "key"),
                "Column 'key' is required");

        Cell langCell = row.getCell(1);
        Assert.isTrue(StringUtils.isNotBlank(langCell.getStringCellValue()), "one language is required at least");
    }

    @Override
    protected void doBuild(final Path root, final ProjectMetadata metadata, final Sheet sheet) throws IOException {
        Row firstRow = sheet.getRow(0);

        List<String> languages = getLanguages(firstRow);

        Path path = Paths.get(root + File.separator + properties.getExportRelativePath());
        for (int i = 1; i <= languages.size(); i++) {
            String fileName = PREFIX + "_" + languages.get(i - 1) + FileType.DOT_PROPERTIES;
            try (FileWriter writer = new FileWriter(Paths.get(path + File.separator + fileName).toFile())) {
                for (Row row : sheet) {
                    if (row.getRowNum() < 1) {
                        continue;
                    }
                    String key = row.getCell(0).getStringCellValue();
                    String value = row.getCell(i).getStringCellValue();

                    writer.write(key + "=" + value + "\r\n");
                }
            }
        }
    }

    /**
     * Gets the languages from the specified row.
     *
     * @param row the row of a sheet on Excel
     * @return list of languages
     */
    private List<String> getLanguages(Row row) {
        final List<String> results = new ArrayList<>();
        for (Cell cell : row) {
            if (cell.getColumnIndex() < 1) {
                continue;
            }

            results.add(cell.getStringCellValue());
        }

        return results;
    }
}
