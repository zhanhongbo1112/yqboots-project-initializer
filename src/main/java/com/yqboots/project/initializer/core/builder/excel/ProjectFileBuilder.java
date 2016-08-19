package com.yqboots.project.initializer.core.builder.excel;

import com.yqboots.project.initializer.core.DomainMetadata;
import com.yqboots.project.initializer.core.ProjectMetadata;
import com.yqboots.project.initializer.core.builder.FileBuilder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
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
    public Path getFile(final Path root, final ProjectMetadata metadata) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Path getFile(final Path root, final ProjectMetadata metadata, final DomainMetadata domainMetadata) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Path> getFiles(final Path root, final ProjectMetadata metadata, InputStream inputStream) throws IOException {
        final List<Path> results = new ArrayList<>();
        try (
                XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(inputStream))
        ) {
            for (final Sheet sheet : workbook) {
                // one sheet can be processed by more than one builder
                for (SheetBuilder builder : sheetBuilders) {
                    if (builder.supports(sheet)) {
                        builder.build(root, metadata, sheet);
                    }
                }
            }

            return results;
        } catch (InvalidFormatException e) {
            throw new IOException(e);
        }
    }
}
