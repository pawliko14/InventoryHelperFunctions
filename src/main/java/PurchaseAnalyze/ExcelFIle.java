package PurchaseAnalyze;

import Bestelling500.Bestelling500Obj;
import Parameters.Parameters;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelFIle {

    private String fileName;
    private String beginDate;
    private String endDate;

    private static String[] columns = {
            "leverancier", "Order count","Currency", "CashSummary"
    };


    public ExcelFIle(String bestelling500_analyze,String begin, String end) {

        fileName = bestelling500_analyze;
        beginDate = begin;
        endDate = end;

    }


    public void CreateFile2(List<bestellingOrdersSummary> BestellingObj) throws IOException {
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file


        createFirstSheet(workbook ,BestellingObj);
        createSecondSheet(workbook,BestellingObj);

        createFile(workbook);
        workbook.close();
    }

    private void createSecondSheet(Workbook workbook, List<bestellingOrdersSummary> bestellingObj) {

        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        String sheetName = beginDate + " to " + endDate + " - Detailed";
        Sheet sheet = workbook.createSheet(sheetName);

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);


        // ROW FOR DATES
        // Create a Row
        Row dateRow = sheet.createRow(0);

        // Create cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = dateRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }




        // Create a Row
        Row headerRow = sheet.createRow(2);

        // Create cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // Create Other rows and cells with employees data
        int rowNum = 1;
        for(bestellingOrdersSummary obj: bestellingObj) {
            Row row = sheet.createRow(rowNum++);



            row.createCell(0)
                    .setCellValue(obj.getLeverancier());

            row.createCell(1)
                    .setCellValue(obj.getOrderNummerCount());

            row.createCell(2)
                    .setCellValue(obj.getMunt());

            row.createCell(3)
                    .setCellValue(obj.getSumPricesOfAllOrders() );


            // another Row of detailed data

//            Row rowDetailes = sheet.createRow(rowNum++);

            for(int i = 0 ; i < obj.getListOfOrders().size(); i++) {
                Row rowX = sheet.createRow(rowNum++);
                rowX.createCell(0).setCellValue(obj.getListOfOrders().get(i).getLeverancierOrdernummer());
                rowX.createCell(1).setCellValue("-");
                rowX.createCell(2).setCellValue("-");
                rowX.createCell(3).setCellValue(obj.getListOfOrders().get(i).getSummaryValue());
            }

//            obj.getListOfOrders().forEach(x-> rowDetailes.createCell(0).setCellValue(x.getLeverancierOrdernummer()));
//       //     rowDetailes.createCell(0).setCellValue(obj.getListOfOrders().get(0).getLeverancierOrdernummer());
//            rowDetailes.createCell(1).setCellValue("-");
//            rowDetailes.createCell(2).setCellValue("-");
//          //  rowDetailes.createCell(3).setCellValue(obj.getListOfOrders().get(0).getSummaryValue());
//            obj.getListOfOrders().forEach(x-> rowDetailes.createCell(3).setCellValue(x.getSummaryValue()));


        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

    }

    private void createFirstSheet(Workbook workbook, List<bestellingOrdersSummary> bestellingObj) {

        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        String sheetName = beginDate + " to " + endDate;
        Sheet sheet = workbook.createSheet(sheetName);

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);


        // ROW FOR DATES
        // Create a Row
        Row dateRow = sheet.createRow(0);

        // Create cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = dateRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }




        // Create a Row
        Row headerRow = sheet.createRow(2);

        // Create cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // Create Other rows and cells with employees data
        int rowNum = 1;
        for(bestellingOrdersSummary obj: bestellingObj) {
            Row row = sheet.createRow(rowNum++);



            row.createCell(0)
                    .setCellValue(obj.getLeverancier());

            row.createCell(1)
                    .setCellValue(obj.getOrderNummerCount());

            row.createCell(2)
                    .setCellValue(obj.getMunt());

            row.createCell(3)
                    .setCellValue(obj.getSumPricesOfAllOrders() );


        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

    }

    private void createFile(Workbook workbook) throws IOException {

         String fN = fileName;


        File f = new File( createDirectory());


        if(!f.exists()) {
            f.createNewFile();
        }else {
            f = new File(createDirectory() +"//" + fN);

            if(f.exists()) {

                StringBuilder sb = new StringBuilder();
                    sb.append(createDirectory());
                    sb.append("//");
                    sb.append(getCurrentTimeFormatted());
                    sb.append("_");
                    sb.append(fN);

                f = new File(String.valueOf(sb));
            }

            f.createNewFile();
        }

        FileOutputStream fileOut = new FileOutputStream(f);



        workbook.write(fileOut);
        fileOut.close();
    }

    private String createDirectory() {

        String pathToSave = Parameters.getPathToDirectory() + getCurrentDate();
        File directory = new File(pathToSave);


        if(directory.exists() ) {
            System.out.println("Dir cannot be created, its probably a normal file");
        }
        else {

            try {
                if(!directory.exists()) {
                    directory.mkdir();
                }
            }
            catch (Exception e ) {
                System.out.println("Cannot create directory : " + e);
            }
        }

         return directory.getAbsolutePath();
    }

    public  String getCurrentDate() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

       return  formatter.format((date));
    }

    private String getCurrentTimeFormatted()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("hh.mm.ss");

        return  formatter.format((date));
    }


}


