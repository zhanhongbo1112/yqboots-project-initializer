package com.yqboots.project.initializer.core.builder.excel;

import com.yqboots.project.initializer.core.DomainMetadata;
import com.yqboots.project.initializer.core.ProjectMetadata;
import com.yqboots.project.initializer.core.builder.FileTemplateBuilder;
import com.yqboots.project.initializer.core.builder.excel.factory.DomainMetadataFactory;
import org.apache.commons.lang3.StringUtils;
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
 * Created by Administrator on 2016-08-18.
 */
public class DomainSheetBuilder extends AbstractSheetBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(DomainSheetBuilder.class);

    private final VelocityEngine velocityEngine;

    public DomainSheetBuilder(final VelocityEngine velocityEngine, final DomainProperties properties) {
        super(properties.getSheetName());
        this.velocityEngine = velocityEngine;
    }

    @Override
    protected void formatChecking(final Sheet sheet) {
        // get the title row
        Row row = sheet.getRow(1);
        Assert.isTrue(StringUtils.equalsIgnoreCase(row.getCell(0).getStringCellValue(), "Module"),
                "Column 'Module' is required");
        Assert.isTrue(StringUtils.equalsIgnoreCase(row.getCell(1).getStringCellValue(), "Domain Name"),
                "Column 'Domain Name' is required");
        Assert.isTrue(StringUtils.equalsIgnoreCase(row.getCell(2).getStringCellValue(), "DB Column"),
                "Column 'DB Column' is required");
        Assert.isTrue(StringUtils.equalsIgnoreCase(row.getCell(3).getStringCellValue(), "Class Field"),
                "Column 'Class Field' is required");
        Assert.isTrue(StringUtils.equalsIgnoreCase(row.getCell(4).getStringCellValue(), "Field Type"),
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

    private List<FileTemplateBuilder> getBuilders(DomainMetadata domainMetadata) {
        // TODO: generate builders based on domain metadata
        List<FileTemplateBuilder> results = new ArrayList<>();

        return results;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }
}
