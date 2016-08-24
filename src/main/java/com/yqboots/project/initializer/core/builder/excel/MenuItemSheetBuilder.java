package com.yqboots.project.initializer.core.builder.excel;

import com.yqboots.project.fss.core.support.FileType;
import com.yqboots.project.initializer.core.ProjectMetadata;
import com.yqboots.project.menu.core.MenuItem;
import com.yqboots.project.menu.core.MenuItems;
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
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-08-11.
 */
public class MenuItemSheetBuilder extends AbstractSheetBuilder {
    private final Marshaller marshaller;

    private final MenuItemProperties properties;

    public MenuItemSheetBuilder(final Marshaller marshaller, MenuItemProperties properties) {
        super(properties.getSheetName());
        this.marshaller = marshaller;
        this.properties = properties;
    }

    @Override
    protected void formatChecking(final Sheet sheet) {
        // get the title row
        Row row = sheet.getRow(0);

        Cell nameCell = row.getCell(0);
        Cell urlCell = row.getCell(1);
        Cell menuGroupCell = row.getCell(2);
        Cell menuItemGroupCell = row.getCell(3);

        Assert.isTrue(StringUtils.equalsIgnoreCase(nameCell.getStringCellValue(), "name"),
                "Column 'name' is required");
        Assert.isTrue(StringUtils.equalsIgnoreCase(urlCell.getStringCellValue(), "url"),
                "Column 'URL' is required");
        Assert.isTrue(StringUtils.equalsIgnoreCase(menuGroupCell.getStringCellValue(), "Menu Group"),
                "Column 'Menu Group' is required");
        Assert.isTrue(StringUtils.equalsIgnoreCase(menuItemGroupCell.getStringCellValue(), "Menu Item Group"),
                "Column 'Menu Item Group' is required");
    }

    @Override
    protected void doBuild(final Path root, final ProjectMetadata metadata, final Sheet sheet) throws IOException {
        final List<MenuItem> items = new ArrayList<>();

        for (Row row : sheet) {
            // ignore the header
            if (row.getRowNum() < 1) {
                continue;
            }

            items.add(getMenuItem(row));
        }

        // generate an XML for the application importing into Database
        Path targetPath = Paths.get(root + File.separator + properties.getExportRelativePath());
        if (!Files.exists(targetPath)) {
            Files.createDirectories(targetPath);
        }

        Path file = Paths.get(targetPath + File.separator + properties.getExportName() + FileType.DOT_XML);
        try (FileWriter writer = new FileWriter(file.toFile())) {
            marshaller.marshal(new MenuItems(items), new StreamResult(writer));
        }
    }

    private MenuItem getMenuItem(final Row row) {
        MenuItem result = new MenuItem();
        result.setName(row.getCell(0).getStringCellValue());
        result.setUrl(row.getCell(1).getStringCellValue());
        result.setMenuGroup(row.getCell(2).getStringCellValue());
        result.setMenuItemGroup(row.getCell(3).getStringCellValue());
        return result;
    }
}
