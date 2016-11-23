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

import com.yqboots.initializer.core.ProjectMetadata;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Abstraction for sheet builder.
 * <p>It does the following things when building from a sheet:</p>
 * <ul>
 * <li>supports - Simply checks the sheet name prefix to match different sheet builders</li>
 * <li>formatChecking - Simply checks the header</li>
 * <li>doBuild - do the actual build</li>
 * </ul>
 *
 * @author Eric H B Zhan
 * @since 1.0.0
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
