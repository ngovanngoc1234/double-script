package service;


import model.DataID;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

public class ReadListID implements Serializable {
    public static final int COLUMN_INDEX_ID = 0;
    public static final int COLUMN_NAME_CLASS = 1;
    public static final int COLUMN_STATUS = 2;
    public static final int COLUMN_WORK_NAME = 3;
    public static final int COLUMN_LANDMARK = 4;
    public static final int COLUMN_BUG = 5;
    public static final int COLUMN_NAME_BUG = 6;



    private static CellStyle cellStyleFormatNumber = null;


    public void writeExcel(List<DataID> readCSVS, String excelFilePath) throws IOException {
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);

        // Create sheet
        Sheet sheet = workbook.createSheet("sheep3"); // Create sheet with sheet name

        int rowIndex = 0;

        // Write header
        writeHeader(sheet, rowIndex);

        // Write data
        rowIndex++;
        for (DataID ReadCSV : readCSVS) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            writeBook(ReadCSV, row);
            rowIndex++;
        }

        // Write footer
//        writeFooter(sheet, rowIndex);

        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, excelFilePath);
        System.out.println("Done!!!");
    }

    // Create workbook
    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Write header with format
    private static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("id");

        cell = row.createCell(COLUMN_NAME_CLASS);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("name_class");

        cell = row.createCell(COLUMN_STATUS);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("data_status");

        cell = row.createCell(COLUMN_WORK_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("work_name");

        cell = row.createCell(COLUMN_LANDMARK);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("landmark");

        cell = row.createCell(COLUMN_BUG);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("status");

        cell = row.createCell(COLUMN_NAME_BUG);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("error");


    }

    // Write data
    private static void writeBook(DataID ReadCSV, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }

        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellValue(ReadCSV.getId());

        cell = row.createCell(COLUMN_NAME_CLASS);
        cell.setCellValue(ReadCSV.getNameClassBox());

        cell = row.createCell(COLUMN_STATUS);
        cell.setCellValue(ReadCSV.getDataStatus());

        cell = row.createCell(COLUMN_WORK_NAME);
        cell.setCellValue(ReadCSV.getWorkName());

        cell = row.createCell(COLUMN_LANDMARK);
        cell.setCellValue(ReadCSV.getLandmark());






    }

    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    // Auto resize column width
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }

}
