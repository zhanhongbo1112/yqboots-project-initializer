package com.yqboots.project.initializer.core.builder.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-08-11.
 */
public class MessageSheetBuilder extends AbstractSheetBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(MessageSheetBuilder.class);

    private static final String PREFIX = "messages";

    private static final String SHEET_NAME = "messages";

    public MessageSheetBuilder() {
        super(SHEET_NAME);
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
    protected void doBuild(final Sheet sheet) throws IOException {
        Row firstRow = sheet.getRow(0);

        List<String> languages = getLanguages(firstRow);

        for (int i = 1; i <= languages.size(); i++) {
            String fileName = PREFIX + "_" + languages.get(i - 1) + ".properties";
            for (Row row : sheet) {
                if (row.getRowNum() < 1) {
                    continue;
                }
                String key = row.getCell(i - 1).getStringCellValue();
                String value = row.getCell(i).getStringCellValue();

                LOG.info("{}={}", key, value);
                // TODO: write the key + value to properties file
            }
        }
    }

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
