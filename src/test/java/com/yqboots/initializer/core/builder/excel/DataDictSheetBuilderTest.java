package com.yqboots.initializer.core.builder.excel;

import com.yqboots.Application;
import com.yqboots.initializer.autoconfigure.ProjectInitializerProperties;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class DataDictSheetBuilderTest {
    private static final String TEMPLATE_PATH = "com/yqboots/initializer/core/workbook.xlsx";

    @Autowired
    private DataDictSheetBuilder dataDictSheetBuilder;

    @Autowired
    private ProjectInitializerProperties properties;

    @Test
    public void testBuild() throws Exception {
        try (InputStream inputStream = new ClassPathResource(TEMPLATE_PATH).getInputStream()) {
            XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(inputStream));

            XSSFSheet sheet = workbook.getSheet("Dicts");

            dataDictSheetBuilder.build(properties.getTargetPath(), null, sheet);
        }
    }
}