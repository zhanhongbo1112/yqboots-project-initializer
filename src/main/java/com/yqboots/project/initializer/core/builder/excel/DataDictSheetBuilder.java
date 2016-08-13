package com.yqboots.project.initializer.core.builder.excel;

import com.yqboots.project.dict.core.DataDict;
import com.yqboots.project.dict.core.DataDicts;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.oxm.Marshaller;
import org.springframework.util.Assert;

import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-08-11.
 */
public class DataDictSheetBuilder extends AbstractSheetBuilder {
    private static final String SHEET_NAME = "dicts";

    private final Marshaller marshaller;

    public DataDictSheetBuilder(final Marshaller marshaller) {
        super(SHEET_NAME);
        this.marshaller = marshaller;
    }

    @Override
    protected void formatChecking(final Sheet sheet) {
        // get the title row
        Row row = sheet.getRow(0);

        Cell nameCell = row.getCell(0);
        Cell textCell = row.getCell(1);
        Cell valueCell = row.getCell(2);

        Assert.isTrue(StringUtils.equalsIgnoreCase(nameCell.getStringCellValue(), "name"),
                "Column 'name' is required");
        Assert.isTrue(StringUtils.equalsIgnoreCase(textCell.getStringCellValue(), "text"),
                "Column 'text' is required");
        Assert.isTrue(StringUtils.equalsIgnoreCase(valueCell.getStringCellValue(), "value"),
                "Column 'value' is required");
    }

    @Override
    protected void doBuild(final Sheet sheet) throws IOException {
        final List<DataDict> items = new ArrayList<>();

        for (Row row : sheet) {
            // ignore the header
            if (row.getRowNum() < 1) {
                continue;
            }

            items.add(getDataDicts(row));
        }

        // TODO: generate an XML for the application importing into Database
        StringWriter sr = new StringWriter();
        marshaller.marshal(new DataDicts(items), new StreamResult(sr));

        System.out.println(sr.toString());
    }

    private DataDict getDataDicts(final Row row) {
        DataDict result = new DataDict();
        result.setName(row.getCell(0).getStringCellValue());
        result.setText(row.getCell(1).getStringCellValue());
        result.setValue(row.getCell(2).getStringCellValue());
        result.setDescription(row.getCell(3).getStringCellValue());
        return result;
    }
}
