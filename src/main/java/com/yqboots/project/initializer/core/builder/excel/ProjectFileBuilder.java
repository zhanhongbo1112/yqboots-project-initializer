package com.yqboots.project.initializer.core.builder.excel;

import com.yqboots.project.initializer.core.ProjectContext;
import com.yqboots.project.initializer.core.builder.FileBuilder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by Administrator on 2016-08-11.
 */
public class ProjectFileBuilder implements FileBuilder {
    private List<SheetBuilder> sheetBuilders;

    public ProjectFileBuilder(final List<SheetBuilder> sheetBuilders) {
        this.sheetBuilders = sheetBuilders;
    }

    @Override
    public Path getFile(final Path root, final ProjectContext context) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Path> getFiles(final Path root, final ProjectContext context) throws IOException {
        InputStream inputStream = context.getFile();
        // do nothing when no file
        if (inputStream == null) {
            return null;
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(inputStream))) {
            for (final Sheet sheet : workbook) {
                // one sheet can be processed by more than one builder
                for (SheetBuilder builder : sheetBuilders) {
                    if (builder.supports(sheet)) {
                        builder.build(sheet);
                    }
                }
            }

            return null;
        } catch (InvalidFormatException e) {
            throw new IOException(e);
        }
    }
}
