package com.yqboots.initializer.core.builder.excel.factory;

import com.yqboots.initializer.core.DomainMetadata;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class DomainMetadataFactoryTest {
    private static final String TEMPLATE_PATH = "com/yqboots/initializer/core/workbook.xlsx";

    @Test
    public void testCreate() throws Exception {
        try (InputStream inputStream = new ClassPathResource(TEMPLATE_PATH).getInputStream()) {
            XSSFWorkbook workbook = new XSSFWorkbook(OPCPackage.open(inputStream));
            XSSFSheet sheet = workbook.getSheet("Domains");

            List<DomainMetadata> results = DomainMetadataFactory.create(sheet);
            // assertTrue(results.size() == 2);
        }
    }
}