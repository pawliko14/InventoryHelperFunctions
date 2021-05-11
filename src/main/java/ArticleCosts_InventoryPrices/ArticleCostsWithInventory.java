package ArticleCosts_InventoryPrices;

import ExcelFIle.ExcelFileAllSoort;
import ExcelFIle.ExcelFileCostPrices;
import InputExcel.InputExcelFileProcess;
import artikelCostPrice.RetrivePriceFromHS;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.sql.SQLException;

public class ArticleCostsWithInventory {

    public ArticleCostsWithInventory() {
    }

    public void doAnalyze() throws IOException, InvalidFormatException, SQLException {

        InputExcelFileProcess inputExcelFileProcess = new InputExcelFileProcess();
        inputExcelFileProcess.readFromExcelFile("articlePriceToCalculate_allSort.xlsx");

        RetrivePriceFromHS_allSort s = new RetrivePriceFromHS_allSort();
        s.retrivePricesFromHS(inputExcelFileProcess.getArticleList());

        ExcelFileAllSoort e = new ExcelFileAllSoort("ArticlePrice_allSoort.xlsx");
        e.CreateFile2(s.getListOfCosts());
    }
}
