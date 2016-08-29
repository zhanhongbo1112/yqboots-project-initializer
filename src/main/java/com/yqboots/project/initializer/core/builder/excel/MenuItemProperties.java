/**
 * It builds the Excel sheet to retrieve domains.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
package com.yqboots.project.initializer.core.builder.excel;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The configuration properties for menu items.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "yqboots.project.initializer.menu")
public class MenuItemProperties {
    /**
     * The sheet name. defaults to "menus"
     */
    private String sheetName = "menus";

    /**
     * The name for the exported file.
     */
    private String exportName;

    /**
     * The path for the exported file, it is relative path based on root path.
     */
    private String exportRelativePath;

    /**
     * Gets the sheet name.
     *
     * @return sheet name
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * Sets the sheet name.
     *
     * @param sheetName the sheet name
     */
    public void setSheetName(final String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * Gets the export name.
     *
     * @return the name for the exported file
     */
    public String getExportName() {
        return exportName;
    }

    /**
     * Sets the export name.
     *
     * @param exportName the name for the exported file
     */
    public void setExportName(final String exportName) {
        this.exportName = exportName;
    }

    /**
     * Gets the relative path for the exported file.
     *
     * @return The path for the exported file
     */
    public String getExportRelativePath() {
        return exportRelativePath;
    }

    /**
     * Sets the relative path for the exported file.
     *
     * @param exportRelativePath The path for the exported file
     */
    public void setExportRelativePath(final String exportRelativePath) {
        this.exportRelativePath = exportRelativePath;
    }
}
