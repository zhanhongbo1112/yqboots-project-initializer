package com.yqboots.project.initializer.core.builder.excel.factory;

import com.yqboots.project.initializer.core.DomainMetadata;
import com.yqboots.project.initializer.core.DomainMetadataProperty;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-08-18.
 */
public class DomainMetadataFactory {
    public static List<DomainMetadata> create(final Sheet sheet) {
        List<DomainMetadata> results = new ArrayList<>();

        List<Integer> startingPoints = getStartingPoints(sheet);

        int total = startingPoints.size();
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

    private static DomainMetadataProperty getMetadataProperty(final Row row) {
        DomainMetadataProperty result = new DomainMetadataProperty();

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
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            result.setLength(Double.valueOf(cell.getNumericCellValue()).intValue());
        }
        cell = row.getCell(6);
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            result.setNullable(cell.getBooleanCellValue());
        }
        cell = row.getCell(7);
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            result.setUnique(cell.getBooleanCellValue());
        }
        cell = row.getCell(8);
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
