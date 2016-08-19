package com.yqboots.project.initializer.core.builder.excel;

import com.yqboots.project.initializer.core.ProjectMetadata;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by Administrator on 2016-08-11.
 */
public interface SheetBuilder {
    boolean supports(Sheet sheet);

    void build(Path root, final ProjectMetadata metadata, Sheet sheet) throws IOException;
}
