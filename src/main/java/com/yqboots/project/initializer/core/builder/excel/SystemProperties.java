package com.yqboots.project.initializer.core.builder.excel;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2016-08-16.
 */
@ConfigurationProperties(prefix = "yqboots.project.initializer.system")
public class SystemProperties {
    private String sheetName = "System Properties";

    private String exportRelativePath;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(final String sheetName) {
        this.sheetName = sheetName;
    }

    public String getExportRelativePath() {
        return exportRelativePath;
    }

    public void setExportRelativePath(final String exportRelativePath) {
        this.exportRelativePath = exportRelativePath;
    }
}
