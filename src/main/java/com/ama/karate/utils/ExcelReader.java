package com.ama.karate.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Service
public class ExcelReader {

    public JsonObject readExcelFile(String filePath) {
        JsonObject json = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Assuming the first row is the header
            Row headerRow = rowIterator.next();
            Iterator<Cell> headerCellIterator = headerRow.cellIterator();

            // Store headers
            String[] headers = new String[headerRow.getPhysicalNumberOfCells()];
            int headerIndex = 0;
            while (headerCellIterator.hasNext()) {
                Cell cell = headerCellIterator.next();
                headers[headerIndex++] = cell.getStringCellValue();
            }

            // Read the data rows
            while (rowIterator.hasNext()) {
                Row dataRow = rowIterator.next();
                JsonObject rowJson = new JsonObject();
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = dataRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    rowJson.addProperty(headers[i], getCellValueAsString(cell));
                }
                jsonArray.add(rowJson);
            }
            json.add("data", jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

private String getCellValueAsString(Cell cell) {
    if (cell == null) {
        return "";
    }

    // Handle string cells directly
    if (cell.getCellType() == CellType.STRING) {
        return cell.getStringCellValue();
    }

    // Handle numeric cells
    if (cell.getCellType() == CellType.NUMERIC) {
        System.out.println("NUMBER::::::"+ cell.getNumericCellValue());
        if (DateUtil.isCellDateFormatted(cell)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(cell.getDateCellValue());
        } else {
            cell.setCellType(CellType.STRING);
            return String.valueOf(cell.getStringCellValue());
        }
    }

    // Handle boolean cells
    if (cell.getCellType() == CellType.BOOLEAN) {
        return String.valueOf(cell.getBooleanCellValue());
    }

    // Handle formula cells
    if (cell.getCellType() == CellType.FORMULA) {
        return cell.getCellFormula();
    }

    // For blank cells or unknown types, return an empty string
    return "";
}
}
