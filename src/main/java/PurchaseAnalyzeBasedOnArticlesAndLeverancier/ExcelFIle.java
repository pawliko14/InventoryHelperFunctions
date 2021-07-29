package PurchaseAnalyzeBasedOnArticlesAndLeverancier;

import Parameters.Parameters;
import PurchaseAnalyze.bestellingOrdersSummary;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelFIle {

    private String fileName;
    private String year;


    private static String[] columns = {
            "leverancier", "articelcode","description", "Ordered", "delivered","price/Item", "totalCASH","DeliverDate", "Cur"
    };


    public ExcelFIle(String bestelling500_analyze, String year) {

        this.fileName = bestelling500_analyze;
        this.year = year;


    }


    public void CreateFile2(List<BestellingObject> BestellingObj2015) throws IOException {
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
        // Create a Sheet
        String sheetName = year ;
        Sheet sheet = workbook.createSheet(sheetName);

        createFirstSheetYear2015(workbook ,sheet,BestellingObj2015);
  //      createFirstSheetYear2016(workbook, sheet,BestellingObj2016);

        createFile(workbook);
        workbook.close();
    }

    private void createFirstSheetYear2016(Workbook workbook,Sheet sheet, List<BestellingObject> bestellingObj) {

        CreationHelper createHelper = workbook.getCreationHelper();


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
            Cell cell = dateRow.createCell(i + 9);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }




        // Create a Row
        Row headerRow = sheet.createRow(2);

        // Create cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i + 9);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // Create Other rows and cells with employees data
        int rowNum = 1;

        // create another row for YEAR annoration
        Row row = sheet.createRow(rowNum++);
        sheet.addMergedRegion(CellRangeAddress.valueOf("J2:O2"));
        Cell YearCell = row.createCell(0);
        YearCell.setCellValue("2021");

        CellStyle cellStyle = row.getSheet().getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        YearCell.setCellStyle(cellStyle);

        for(BestellingObject obj: bestellingObj) {
            row = sheet.createRow(rowNum++);



            row.createCell(0)
                    .setCellValue(obj.getLeverancier());

            row.createCell(1)
                    .setCellValue(obj.getArticelcode());

            row.createCell(2)
                    .setCellValue(obj.getArtikelomschirjing());

            row.createCell(3)
                    .setCellValue(obj.getBesteld() );

            row.createCell(4)
                    .setCellValue(obj.getGelerved() );

            row.createCell(5)
                    .setCellValue(obj.getPricePerItem());

            row.createCell(6)
                    .setCellValue(String.valueOf(obj.getTotal()));

            row.createCell(7)
                    .setCellValue(obj.getLeveringsdatumReceptie() );

            row.createCell(8)
                    .setCellValue(obj.getMunt() );


        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

    }


    private void createFirstSheetYear2015(Workbook workbook,Sheet sheet, List<BestellingObject> bestellingObj) {

        CreationHelper createHelper = workbook.getCreationHelper();


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

        // create another row for YEAR annoration
          Row row = sheet.createRow(rowNum++);
          sheet.addMergedRegion(CellRangeAddress.valueOf("C2:I2"));
          Cell YearCell = row.createCell(0);
          YearCell.setCellValue("2021");

                CellStyle cellStyle = row.getSheet().getWorkbook().createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);

          YearCell.setCellStyle(cellStyle);

        for(BestellingObject obj: bestellingObj) {
             row = sheet.createRow(rowNum++);



            row.createCell(0)
                    .setCellValue(obj.getLeverancier());

            row.createCell(1)
                    .setCellValue(obj.getArticelcode());

            row.createCell(2)
                    .setCellValue(obj.getArtikelomschirjing());

            row.createCell(3)
                    .setCellValue(obj.getBesteld() );

            row.createCell(4)
                    .setCellValue(obj.getGelerved() );

            row.createCell(5)
                    .setCellValue(obj.getPricePerItem());

            row.createCell(6)
                    .setCellValue(String.valueOf(obj.getTotal()));

            row.createCell(7)
                    .setCellValue(obj.getLeveringsdatumReceptie() );

            row.createCell(8)
                    .setCellValue(obj.getMunt() );


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


