/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.initializer.core.builder.excel;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The configuration properties for system properties.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "yqboots.initializer.system")
public class SystemProperties {
    /**
     * The sheet name. defaults to "System Properties"
     */
    private String sheetName = "System Properties";

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
