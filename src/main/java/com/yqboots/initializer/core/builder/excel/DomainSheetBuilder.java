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

import com.yqboots.initializer.core.DomainMetadata;
import com.yqboots.initializer.core.ProjectMetadata;
import com.yqboots.initializer.core.builder.FileTemplateBuilder;
import com.yqboots.initializer.core.builder.JavaFileBuilder;
import com.yqboots.initializer.core.builder.ResourcesFileBuilder;
import com.yqboots.initializer.core.builder.excel.factory.DomainMetadataFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * It builds the Excel sheet to retrieve domains.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class DomainSheetBuilder extends AbstractSheetBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(DomainSheetBuilder.class);

    private final VelocityEngine velocityEngine;

    /**
     * Constructs the DomainSheetBuilder.
     *
     * @param velocityEngine the velocity engine
     * @param properties     properties for MenuItem
     */
    public DomainSheetBuilder(final VelocityEngine velocityEngine, final DomainProperties properties) {
        super(properties.getSheetName());
        this.velocityEngine = velocityEngine;
    }

    @Override
    protected void formatChecking(final Sheet sheet) {
        // get the title row
        Row mergedRow = sheet.getRow(0);
        Cell mergedCell = mergedRow.getCell(0);
        Assert.isTrue(StringUtils.equalsIgnoreCase(mergedCell.getStringCellValue(), "Module"),
                "Column 'Module' is required");

        mergedCell = mergedRow.getCell(1);
        Assert.isTrue(StringUtils.equalsIgnoreCase(mergedCell.getStringCellValue(), "Domain Name"),
                "Column 'Domain Name' is required");

        mergedCell = mergedRow.getCell(2);
        Assert.isTrue(StringUtils.equalsIgnoreCase(mergedCell.getStringCellValue(), "Generated"),
                "Column 'Generated' is required");

        Row row = sheet.getRow(1);
        Assert.isTrue(StringUtils.equalsIgnoreCase(row.getCell(3).getStringCellValue(), "DB Column"),
                "Column 'DB Column' is required");
        Assert.isTrue(StringUtils.equalsIgnoreCase(row.getCell(4).getStringCellValue(), "Class Field"),
                "Column 'Class Field' is required");
        Assert.isTrue(StringUtils.equalsIgnoreCase(row.getCell(5).getStringCellValue(), "Field Type"),
                "Column 'Field Type' is required");
    }

    @Override
    protected void doBuild(final Path targetPath, final ProjectMetadata metadata, final Sheet sheet) throws IOException {
        // get all domain metadata
        List<DomainMetadata> metadatas = DomainMetadataFactory.create(sheet);
        // merge them into vm templates
        for (DomainMetadata domainMetadata : metadatas) {
            final VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("metadata", metadata);
            velocityContext.put("domain", domainMetadata);
            velocityContext.put("StringUtils", StringUtils.class);

            final List<FileTemplateBuilder> builders = getBuilders(domainMetadata);
            for (FileTemplateBuilder builder : builders) {
                if (!getVelocityEngine().resourceExists(builder.getTemplate())) {
                    LOG.warn("Template {} not found, ignore...", builder.getTemplate());
                    continue;
                }

                Template template = getVelocityEngine().getTemplate(builder.getTemplate());
                try (Writer writer = new FileWriter(builder.getFile(targetPath, metadata, domainMetadata).toFile())) {
                    // retrieve the root path of the target project
                    template.merge(velocityContext, writer);
                    writer.flush();
                }
            }
        }
    }

    /**
     * Gets builders from metadata.
     *
     * @param metadata the domain metadata
     * @return list of builders
     */
    private List<FileTemplateBuilder> getBuilders(DomainMetadata metadata) {
        final List<FileTemplateBuilder> results = new ArrayList<>();

        results.add(new JavaFileBuilder("Domain.java.vm", DomainMetadataFactory.createDomainPath(metadata)));
        results.add(new JavaFileBuilder("Repository.java.vm", DomainMetadataFactory.createRepositoryPath(metadata)));
        results.add(new JavaFileBuilder("Controller.java.vm", DomainMetadataFactory.createControllerPath(metadata)));
        results.add(new ResourcesFileBuilder("index.js.vm", DomainMetadataFactory.createDomainJsPath(metadata, "index")));
        results.add(new ResourcesFileBuilder("form.js.vm", DomainMetadataFactory.createDomainJsPath(metadata, "form")));
        results.add(new ResourcesFileBuilder("index.html.vm", DomainMetadataFactory.createDomainHtmlPath(metadata, "index")));
        results.add(new ResourcesFileBuilder("form.html.vm", DomainMetadataFactory.createDomainHtmlPath(metadata, "form")));

        return results;
    }

    protected VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }
}
