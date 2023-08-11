package com.pravyuha.learn;

import java.util.List;
import com.pravyuha.utilities.ExcelProcessor;
import com.pravyuha.utilities.JSONProcessor;


public class IOTConfigTool {
    public static void main(String[] args) {
        String filePath = "D:\\EXCELFILE.xlsx"; // path to Excel file
        String heading = "STUDENTID"; // The heading to extract data from

        // Read data from the Excel file using ExcelReader module
        List<ExcelProcessor.RowData> rowDataList = ExcelProcessor.readExcelData(filePath, heading);
        // Convert data to hex in IOTconfigTool module
        for (ExcelProcessor.RowData rowData : rowDataList) {
            String value = convertToHex(rowData.getValue1());
            rowData.setValue1(value);
        }
        // Generate individual JSON files for each cell value
        for (ExcelProcessor.RowData rowData : rowDataList) {
            String value = rowData.getValue1();
            JSONProcessor.generateJsonFile(value); // Generate JSON file with value as data
        }
    }
    private static String convertToHex(String asciiText) {

        StringBuilder hexValue = new StringBuilder();
        for (char c : asciiText.toCharArray()) {
            hexValue.append(Integer.toHexString(c));
        }
        return hexValue.toString();
    }
}

