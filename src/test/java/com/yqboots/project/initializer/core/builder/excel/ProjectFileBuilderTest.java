package com.yqboots.project.initializer.core.builder.excel;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

/**
 * Created by Administrator on 2016-08-11.
 */
public class ProjectFileBuilderTest {
    private static final String TEMPLATE_PATH = "com/yqboots/project/initializer/core/builder/excel/workbook.xlsx";

//    @Autowired
//    private ProjectFileBuilder projectFileBuilder;

    @Test
    public void testGetFile() throws Exception {
        InputStream is = new ClassPathResource(TEMPLATE_PATH).getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(is));
        for (final Sheet sheet : workbook) {
            System.out.println(sheet.getSheetName());
        }
    }
}
