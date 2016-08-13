package com.yqboots.project.initializer.core.builder.excel;

import com.yqboots.project.menu.core.MenuItem;
import com.yqboots.project.menu.core.MenuItems;
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
public class MenuSheetBuilder extends AbstractSheetBuilder {
    private static final String SHEET_NAME = "menus";

    private final Marshaller marshaller;

    public MenuSheetBuilder(final Marshaller marshaller) {
        super(SHEET_NAME);
        this.marshaller = marshaller;
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
    protected void doBuild(final Sheet sheet) throws IOException {
        final List<MenuItem> items = new ArrayList<>();

        for (Row row : sheet) {
            // ignore the header
            if (row.getRowNum() < 1) {
                continue;
            }

            items.add(getMenuItem(row));
        }


        // TODO: generate an XML for the application importing into Database
        StringWriter sr = new StringWriter();
        marshaller.marshal(new MenuItems(items), new StreamResult(sr));

        System.out.println(sr.toString());
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
