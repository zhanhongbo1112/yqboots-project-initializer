package com.yqboots.project.initializer.core.builder.excel;

import com.yqboots.project.initializer.Application;
import com.yqboots.project.initializer.autoconfigure.ProjectInitializerProperties;
import com.yqboots.project.initializer.core.ProjectMetadata;
import com.yqboots.project.initializer.core.theme.Theme;
import com.yqboots.project.initializer.core.theme.ThemeColor;
import com.yqboots.project.initializer.core.theme.ThemeSkin;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class DomainSheetBuilderTest {
    private static final String TEMPLATE_PATH = "com/yqboots/project/initializer/core/workbook.xlsx";

    @Autowired
    private DomainSheetBuilder domainSheetBuilder;

    @Autowired
    private ProjectInitializerProperties properties;

    private ProjectMetadata metadata;

    @Before
    public void setUp() {
        metadata = new ProjectMetadata();
        metadata.setGroupId("com.yqboots.test");
        metadata.setArtifactId("test-core");
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