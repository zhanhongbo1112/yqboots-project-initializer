package com.yqboots.project.initializer.core.builder.excel;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2016-08-18.
 */
@ConfigurationProperties(prefix = "yqboots.project.initializer.domain")
public class DomainProperties {
    private String sheetName = "domains";

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(final String sheetName) {
        this.sheetName = sheetName;
    }
}
