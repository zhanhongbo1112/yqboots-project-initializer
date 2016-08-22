package com.yqboots.project.initializer.core.builder.excel;

import com.yqboots.project.initializer.core.ProjectMetadata;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by Administrator on 2016-08-11.
 */
public abstract class AbstractSheetBuilder implements SheetBuilder {
    private final String sheetName;

    public AbstractSheetBuilder(final String sheetName) {
        this.sheetName = sheetName;
    }

    @Override
    public boolean supports(final Sheet sheet) {
        final String name = StringUtils.trim(sheet.getSheetName());
        return StringUtils.startsWithIgnoreCase(name, this.sheetName);
    }

    @Override
    public void build(final Path root, final ProjectMetadata metadata, final Sheet sheet) throws IOException {
        formatChecking(sheet);

        doBuild(root, metadata, sheet);
    }

    protected abstract void formatChecking(final Sheet sheet);

    protected abstract void doBuild(final Path root, final ProjectMetadata metadata, final Sheet sheet) throws IOException;
}
