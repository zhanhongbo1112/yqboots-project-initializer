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
package com.yqboots.project.initializer.core.builder.excel.factory;

import com.yqboots.project.initializer.core.DomainMetadata;
import com.yqboots.project.initializer.core.DomainMetadataProperty;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

/**
 * The factory, which creates domain metadata based on the content in the sheet of an Excel.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class DomainMetadataFactory {
    /**
     * Creates domain metadatas.
     *
     * @param sheet the excel sheet
     * @return list of DomainMetadata
     */
    public static List<DomainMetadata> create(final Sheet sheet) {
        final List<DomainMetadata> results = new ArrayList<>();

        final List<Integer> startingPoints = getStartingPoints(sheet);

        final int total = startingPoints.size();
        // no need to handle the last point
        for (int i = 0; i < total - 1; i++) {
            int point = startingPoints.get(i);

            Row row = sheet.getRow(point);

            final String module = row.getCell(0).getStringCellValue();
            final String domain = row.getCell(1).getStringCellValue();

            final List<DomainMetadataProperty> metadataProperties = new ArrayList<>();
            while ((i + 1) <= total && point < startingPoints.get(i + 1)) {
                row = sheet.getRow(point);
                point++;
                if (row == null) {
                    continue;
                }
                // DB Column and Class Field should not be empty
                if (row.getCell(2) != null && row.getCell(3) != null) {
                    metadataProperties.add(getMetadataProperty(row));
                }
            }

            results.add(new DomainMetadata(module, domain, metadataProperties));
        }

        return results;
    }

    /**
     * Creates relative java file path of a domain.
     *
     * @param metadata the domain metadata
     * @return the relative file path
     */
    public static String createDomainPath(DomainMetadata metadata) {
        return "/core/" + metadata.getName() + ".java";
    }

    /**
     * Creates relative java repository file path of a domain.
     *
     * @param metadata the domain metadata
     * @return the relative file path
     */
    public static String createRepositoryPath(DomainMetadata metadata) {
        return "/core/repository/" + metadata.getName() + "Repository.java";
    }

    /**
     * Creates relative java controller file path of a domain.
     *
     * @param metadata the domain metadata
     * @return the relative file path
     */
    public static String createControllerPath(DomainMetadata metadata) {
        return "/web/controller/" + metadata.getName() + "Controller.java";
    }

    /**
     * Creates relative javascript file path of a domain.
     *
     * @param metadata the domain metadata
     * @param name     the javascript file name (not contain the suffix)
     * @return the relative file path
     */
    public static String createDomainJsPath(DomainMetadata metadata, String name) {
        return "/META-INF/resources/dojo/app/" + metadata.getModule() + "/" + metadata.getName() + "/" + name + ".js";
    }

    /**
     * Creates relative html file path of a domain.
     *
     * @param metadata the domain metadata
     * @param name     the javascript file name (not contain the suffix)
     * @return the relative file path
     */
    public static String createDomainHtmlPath(DomainMetadata metadata, String name) {
        return "/templates/" + metadata.getModule() + "/" + metadata.getName() + "/" + name + ".html";
    }

    /**
     * Gets the starting points of each domain.
     *
     * @param sheet the Excel sheet
     * @return all starting points
     */
    private static List<Integer> getStartingPoints(final Sheet sheet) {
        List<Integer> results = new ArrayList<>();
        for (Row row : sheet) {
            // ignore the header
            if (row.getRowNum() < 2) {
                continue;
            }

            Cell module = row.getCell(0);
            Cell domain = row.getCell(1);
            if (module != null && domain != null) {
                results.add(row.getRowNum());
            }
        }

        // ending flag
        results.add(sheet.getLastRowNum() + 1);

        return results;
    }

    /**
     * Gets the properties.
     *
     * @param row the row of one sheet in the Excel
     * @return one DomainMetadataProperty
     */
    private static DomainMetadataProperty getMetadataProperty(final Row row) {
        final DomainMetadataProperty result = new DomainMetadataProperty();

        Cell cell = row.getCell(2);
        if (cell != null) {
            result.setDbColumn(cell.getStringCellValue());
        }
        cell = row.getCell(3);
        if (cell != null) {
            result.setClassField(cell.getStringCellValue());
        }
        cell = row.getCell(4);
        if (cell != null) {
            result.setFieldType(cell.getStringCellValue());
        }
        cell = row.getCell(5);
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            result.setNullable(cell.getBooleanCellValue());
        }
        cell = row.getCell(6);
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            result.setUnique(cell.getBooleanCellValue());
        }
        cell = row.getCell(7);
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    result.setDefaultValue(Double.toString(cell.getNumericCellValue()));
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result.setDefaultValue(Boolean.toString(cell.getBooleanCellValue()));
                    break;
                default:
                    result.setDefaultValue(cell.getStringCellValue());
            }
        }

        return result;
    }
}
