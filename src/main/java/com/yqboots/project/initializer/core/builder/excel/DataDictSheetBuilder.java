package com.yqboots.project.initializer.core.builder.excel;

import com.yqboots.project.dict.core.DataDict;
import com.yqboots.project.dict.core.DataDicts;
import com.yqboots.project.fss.core.support.FileType;
import com.yqboots.project.initializer.core.ProjectMetadata;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.oxm.Marshaller;
import org.springframework.util.Assert;

import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-08-11.
 */
public class DataDictSheetBuilder extends AbstractSheetBuilder {
    private final Marshaller marshaller;

    private final DataDictProperties properties;

    public DataDictSheetBuilder(final Marshaller marshaller, DataDictProperties properties) {
        super(properties.getSheetName());
        this.marshaller = marshaller;
        this.properties = properties;
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
    protected void doBuild(final Path root, final ProjectMetadata metadata, final Sheet sheet) throws IOException {
        final List<DataDict> items = new ArrayList<>();

        for (Row row : sheet) {
            // ignore the header
            if (row.getRowNum() < 1) {
                continue;
            }

            items.add(getDataDicts(row));
        }

        // generate an XML for the application importing into Database
        Path targetPath = Paths.get(root + File.separator + properties.getExportRelativePath());
        if (!Files.exists(targetPath)) {
            Files.createDirectories(targetPath);
        }

        Path file = Paths.get(targetPath + File.separator + properties.getExportName() + FileType.DOT_XML);
        try (FileWriter writer = new FileWriter(file.toFile())) {
            marshaller.marshal(new DataDicts(items), new StreamResult(writer));
        }
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
