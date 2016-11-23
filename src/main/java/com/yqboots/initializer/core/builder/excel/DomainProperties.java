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
 * The configuration properties for domains.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "yqboots.initializer.domain")
public class DomainProperties {
    /**
     * The sheet name. defaults to "domains"
     */
    private String sheetName = "domains";

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
}
