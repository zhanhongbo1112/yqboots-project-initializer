package com.yqboots.project.initializer.core.builder.excel;

import com.yqboots.project.initializer.Application;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class DataDictSheetBuilderTest {
    private static final String TEMPLATE_PATH = "com/yqboots/project/initializer/core/builder/excel/workbook.xlsx";

    @Autowired
    private DataDictSheetBuilder dataDictSheetBuilder;

    @Test
    public void testBuild() throws Exception {
        InputStream is = new ClassPathResource(TEMPLATE_PATH).getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(is));

        XSSFSheet sheet = workbook.getSheet("Dicts");

        dataDictSheetBuilder.build(sheet);

        is.close();
    }
}