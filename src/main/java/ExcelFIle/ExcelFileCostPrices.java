package ExcelFIle;

import Bestelling500.Bestelling500Obj;
import Parameters.Parameters;
import Werkuren.WerkurenObj;
import artikelCostPrice.ArtikelCostPriceObj;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelFileCostPrices {

    private String fileName;

    private static String[] columns = {
            "artikelcode", "Material","Work","Summary"};


    public ExcelFileCostPrices(String s) {
        fileName = s;

    }


    public void CreateFile2(List<ArtikelCostPriceObj> ArticleList) throws IOException {
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("sheet1");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

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
        for(ArtikelCostPriceObj obj: ArticleList) {
            Row row = sheet.createRow(rowNum++);



            row.createCell(0)
                    .setCellValue(obj.getArtykul());

            row.createCell(1)
                    .setCellValue(obj.getCenaSredniaWazona());

            row.createCell(2)
                    .setCellValue(obj.getCenaPracy());

            row.createCell(3)
                    .setCellValue(obj.getSumaObu());

        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }


        createFile(workbook);
        workbook.close();
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


