package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    private static Workbook workbook;
    private static Sheet sheet;
    private static FileInputStream fis;

    public static void openExcelFile(String filePath, String sheetName) throws IOException {
        fis = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fis);
        if (sheetName == null || sheetName.isEmpty()) {
            sheet = workbook.getSheetAt(0); // Use first sheet
        } else {
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in Excel file");
            }
        }
    }

    public static int getRowCount() {
        if (sheet == null) {
            throw new RuntimeException("Excel sheet is not initialized");
        }
        return sheet.getLastRowNum() + 1;
    }

    public static int getColumnCount() {
        if (sheet == null) {
            throw new RuntimeException("Excel sheet is not initialized");
        }
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            return 0;
        }
        return headerRow.getLastCellNum();
    }

    public static String getCellData(int rowNum, int colNum) {
        if (sheet == null) {
            throw new RuntimeException("Excel sheet is not initialized");
        }
        
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return "";
        }
        
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public static List<String[]> getTestData(String filePath, String sheetName) {
        List<String[]> testData = new ArrayList<>();
        
        try {
            openExcelFile(filePath, sheetName);
            
            int rowCount = getRowCount();
            int colCount = getColumnCount();
            
            // Skip header row, start from row 1
            for (int i = 1; i < rowCount; i++) {
                String[] rowData = new String[colCount];
                for (int j = 0; j < colCount; j++) {
                    rowData[j] = getCellData(i, j);
                }
                testData.add(rowData);
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        } finally {
            closeExcelFile();
        }
        
        return testData;
    }

    public static void closeExcelFile() {
        try {
            if (workbook != null) {
                workbook.close();
            }
            if (fis != null) {
                fis.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to close Excel file", e);
        }
    }
}
