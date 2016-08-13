package com.yqboots.project.initializer.core.builder.excel;

import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;

/**
 * Created by Administrator on 2016-08-11.
 */
public interface SheetBuilder {
    boolean supports(Sheet sheet);

    void build(Sheet sheet) throws IOException;
}
