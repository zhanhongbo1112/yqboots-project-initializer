package com.yqboots.initializer.core.builder.excel;

import com.yqboots.initializer.Application;
import com.yqboots.initializer.autoconfigure.ProjectInitializerProperties;
import com.yqboots.initializer.core.ProjectMetadata;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class DomainSheetBuilderTest {
    private static final String TEMPLATE_PATH = "com/yqboots/initializer/core/workbook.xlsx";

    @Autowired
    private DomainSheetBuilder domainSheetBuilder;

    @Autowired
    private ProjectInitializerProperties properties;

    private ProjectMetadata metadata;

    @Before
    public void setUp() {
        metadata = new ProjectMetadata();
        metadata.setGroupId("com.company.project");
        metadata.setArtifactId("company-projects");
        metadata.setName("Test Project");
        metadata.setDescription("Test Description");
    }

    @Test
    public void testDoBuild() throws Exception {
        try (InputStream inputStream = new ClassPathResource(TEMPLATE_PATH).getInputStream()) {
            XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(inputStream));

            XSSFSheet sheet = workbook.getSheet("Domains");

            domainSheetBuilder.build(properties.getTargetPath(), metadata, sheet);
        }
    }
}