import Test_to_remove.RetriveArticleStock;
import Test_to_remove.Test;


public class main {

    public static void main(String[] args) throws Exception {


        Test test = new Test();
        test.readFromExcelFile("MubeaArticles.xlsx");

        RetriveArticleStock s = new RetriveArticleStock();
        s.retriveArticleType(test.getArticleList());

        System.out.println( s.getArticlecodeVerschaffingscode());

        System.out.println("filtered by A");
        System.out.println(s.getOnlyByTyperticles("A"));

        System.out.println("filtered by P");
        System.out.println(s.getOnlyByTyperticles("P"));

    }

}
