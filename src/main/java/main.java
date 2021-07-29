import ArticleStock.ObjectMubeaArticlesManyColumns;
import Bestelling500.Retrive500FromHS;
import ExcelFIle.ExcelFIle;
import ExcelFIle.ExcelFileCostPrices;
import ArticleStock.RetriveArticleStock;
import InputExcel.InputExcelFileProcess;
import ArticleStock.VerschaffingsCode;
import InputExcel.InputExcelFileProcess_manyolumns;
import Parameters.Parameters;
import PurchaseAnalyzeBasedOnArticlesAndLeverancier.BestellingObject;
import PurchaseAnalyzeBasedOnArticlesAndLeverancier.Logic;
import artikelCostPrice.RetrivePriceFromHS;
import lombok.SneakyThrows;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class main {

    public static void main(String[] args) throws Exception {
      //   redirectOutputToFile();

        //PURCHASE ANALYZE
         //    doPurchaseAnalyze();


        // PURCHASE ANALYZE BASED ON ARTICLES AND LEVERANCIRR
          //  doPurchaseAnalyzeBasedOnArticlesAndLeverancier()


        //   ArticleAnalyze ();
        //   bestelling500Analyze();
        //    articleCostPrice();


   //       ArticleCostsWithInventory articleCostsWithInventory = new ArticleCostsWithInventory();
   //           articleCostsWithInventory.doAnalyze();


    }

    private  static void doPurchaseAnalyzeBasedOnArticlesAndLeverancier() throws SQLException, IOException {
        Logic logic2015 = new Logic("2021", "1190031");
        List<BestellingObject> bestellingObjects2015 = logic2015.doAnalyze();

        PurchaseAnalyzeBasedOnArticlesAndLeverancier.ExcelFIle e = new PurchaseAnalyzeBasedOnArticlesAndLeverancier.ExcelFIle("complexAnalyze2021.xlsx", "2021");
        try {
            e.CreateFile2(bestellingObjects2015);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     *
     * purchase analyze -
     * analzye is based directly on supplier
     *
     * generates: excel file
     *
     * @throws SQLException
     */
    private static  void doPurchaseAnalyze() throws SQLException, IOException {
        System.out.println("doPurchaseAnalyze");
                    PurchaseAnalyze.Logic logic = new PurchaseAnalyze.Logic("2021-01-01", "2021-07-28");
                       logic.doAnalyze();
    }

    private static void redirectOutputToFile() throws FileNotFoundException {
        // Creating a File object that represents the disk file.
        PrintStream o = new PrintStream(new File(Parameters.getPathToLogFile() + "log.txt"));
        // Store current System.out before assigning a new value
        PrintStream console = System.out;
        // Assign o to output stream
        System.setOut(o);

    }

    /*
        function that check if provided articles exist in HacoSoft,
        it some particular records dont exist, in terminal will appear warning :
        "There is no record: {article} in database"

        Input:
            function needs excel file to process with only ONE sheet and data in first column
            In first column there should be list of articles.

         Output:
            Function creates excel file with articles(which exist) and its prices( material, work, mat+work)
            (if there are only purchase articles(A type), there is no work price ( 0 in each row)

          Careful!
            function takes in data process only weighted average price ( there could be more)
            and this price is taken from the last one calculation(it means there could be more than one
            average price in time peroid).


     */
    private static void articleCostPrice() throws IOException, InvalidFormatException, SQLException {

        InputExcelFileProcess inputExcelFileProcess = new InputExcelFileProcess();
        inputExcelFileProcess.readFromExcelFile("articleToPriceCalculate.xlsx");

        RetrivePriceFromHS s = new RetrivePriceFromHS();
        s.retrivePricesFromHS(inputExcelFileProcess.getArticleList());

        ExcelFileCostPrices e = new ExcelFileCostPrices("ArticleCostPrice.xlsx");
        e.CreateFile2(s.getListOfCosts());

    }

/*

    function checks if articles passed in .xlsx file exist in hacoSoft.
    If Record does not exist, in terminal will be printed warning :
    "There is no record: {record} in database"

    Function also filters over article type finding which one is 'A' type or 'P' type

    Here file is not generated.

 */
    public static void ArticleAnalyze() throws Exception {



        InputExcelFileProcess inputExcelFileProcess = new InputExcelFileProcess();
        inputExcelFileProcess.readFromExcelFile("MubeaArticles.xlsx");

        RetriveArticleStock s = new RetriveArticleStock();
        s.retriveArticleType(inputExcelFileProcess.getArticleList());

      //  System.out.println( s.getArticlecodeVerschaffingscode());
        s.getArticlecodeVerschaffingscode().entrySet().stream().forEach((System.out::println));

        System.out.println("filtered by A");
        System.out.println(s.getOnlyByTyperticles(VerschaffingsCode.A));

        System.out.println("filtered by P");
        System.out.println(s.getOnlyByTyperticles(VerschaffingsCode.P));

        s.getOnlyByTyperticles(VerschaffingsCode.P).entrySet().stream().forEach(System.out::println);

            // call for function if you need to compare list of element and sheet of elements
        //articleAnalyzeWIthMoreDataInExcelSheet(inputExcelFileProcess, s);


    }

    private static void articleAnalyzeWIthMoreDataInExcelSheet(InputExcelFileProcess inputExcelFileProcess, RetriveArticleStock s) throws IOException, InvalidFormatException {

        // final list
        List<ObjectMubeaArticlesManyColumns> finalManyColumns = new ArrayList<>();

        // comparator list of articles, and map of articles and its descriptions
        List<ObjectMubeaArticlesManyColumns> manyColumns = retreiveMubeaAriclesManyColumns(inputExcelFileProcess);

        //article that dont exist in HSDATFAT
        List<String> articlesThatDontExistInFATDB = s.getArticlesThatDontExistInFATDB();





        // extremely time consuming process n^2, need to refactor
        // try to find occurance of elements
        for(int i = 0 ; i < articlesThatDontExistInFATDB.size() ;i++) {
            for(int j = 0 ; j < manyColumns.size(); j++) {
                if(articlesThatDontExistInFATDB.get(i).equals(manyColumns.get(j).getArticleNumber())) {
                    finalManyColumns.add(manyColumns.get(j));
                }
            }
        }

        System.out.print("done\n");
        long countBeforeRemoveRepitition = finalManyColumns.stream().count();
        System.out.print("countBeforeRemoveRepitition " + countBeforeRemoveRepitition);

        List<ObjectMubeaArticlesManyColumns> collectionWithoutRepititions = finalManyColumns.stream().distinct().collect(Collectors.toList());

        System.out.print("countAfterRemovingRepitisiont " + collectionWithoutRepititions.stream().count());

        System.out.print("no repititions\n");

        collectionWithoutRepititions.stream()
                .sorted(Comparator.comparing(ObjectMubeaArticlesManyColumns::getArticleNumber))
                .forEach(System.out::println);
    }

    private static List<ObjectMubeaArticlesManyColumns> retreiveMubeaAriclesManyColumns(InputExcelFileProcess inputExcelFileProcess) throws IOException, InvalidFormatException {
        InputExcelFileProcess_manyolumns inputExcelFileProcess_manyolumns = new InputExcelFileProcess_manyolumns();
        inputExcelFileProcess_manyolumns.readFromExcelFile("MubeaArticles_PriceDescription.xlsx");

        return inputExcelFileProcess_manyolumns.getArticleList();

//        System.out.println("List of articles,desc and unit price from xlsx file");
//        inputExcelFileProcess.getArticleList().forEach(System.out::println);
    }


    /*
        Purchase analyze, finding 500/ (production) orders , finding if these 500/ exists
        and checking for ordered and delivered amount

        Input: Bestelling500.xlsx

            file MUST contain one column in first sheet, only this column will be processed.



        Function generates excel file
     */
    public static void bestelling500Analyze() throws SQLException, IOException, InvalidFormatException {
        InputExcelFileProcess inputExcelFileProcess = new InputExcelFileProcess();
        inputExcelFileProcess.readFromExcelFile("Bestelling500.xlsx");

        Retrive500FromHS s = new Retrive500FromHS();
        s.retriveArticleType(inputExcelFileProcess.getArticleList());

        ExcelFIle e =  new ExcelFIle("bestelling500_analyze.xlsx");
        e.CreateFile2(s.getListof500());


    }



}
