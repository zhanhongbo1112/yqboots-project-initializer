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
 * Created by Administrator on 2016-08-18.
 */
public class SystemPropertiesSheetBuilder extends AbstractSheetBuilder {
    private static final String FILE_NAME = "application.properties";

    private final SystemProperties properties;

    public SystemPropertiesSheetBuilder(final SystemProperties properties) {
        super(properties.getSheetName());
        this.properties = properties;
    }

    @Override
    protected void formatChecking(final Sheet sheet) {
        // get the title row
        Row row = sheet.getRow(0);
        Cell keyCell = row.getCell(0);
        Assert.isTrue(StringUtils.equalsIgnoreCase(keyCell.getStringCellValue(), "name"),
                "Column 'name' is required");
        Cell valueCell = row.getCell(1);
        Assert.isTrue(StringUtils.equalsIgnoreCase(valueCell.getStringCellValue(), "value"),
                "Column 'value' is required");
    }

    @Override
    protected void doBuild(final Path root, final ProjectMetadata metadata, final Sheet sheet) throws IOException {
        Path path = Paths.get(root + File.separator + properties.getExportRelativePath());
        try (FileWriter writer = new FileWriter(Paths.get(path + File.separator + FILE_NAME).toFile())) {
            for (Row row : sheet) {
                // ignore the first row
                if (row.getRowNum() < 1) {
                    continue;
                }

                // set comment
                String description = row.getCell(2).getStringCellValue();
                writer.write("#" + description + "\r\n");

                String key = row.getCell(0).getStringCellValue();
                String value = row.getCell(1).getStringCellValue();

                writer.write(key + "=" + value + "\r\n");
            }
        }
    }
}
