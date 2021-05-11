package ArticleCosts_InventoryPrices;

import artikelCostPrice.ArtikelCostPriceObj;
import DBConnectorFATDB.DBConnectorFATDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetrivePriceFromHS_allSort {

    private List<ArticleCostPriceObjAllSort> listOfCosts;

    public List<ArticleCostPriceObjAllSort> getListOfCosts() {
        return listOfCosts;
    }

    public RetrivePriceFromHS_allSort() {
        this.listOfCosts = new ArrayList<>();
    }

    public void retrivePricesFromHS(List<String> articles) throws SQLException {
        Connection connection = DBConnectorFATDB.dbConnector();
        ResultSet rs = null;
        PreparedStatement pstmnt= null;


        for(String a : articles ) {

            String sql = "select MATERIAAL, LONEN , CFKOSTPRIJS, SOORT  from artikel_kostprijs_allsort aka \n" +
                    " where ARTIKELCODE  =  ? \n" +
                    " and (SOORT  = '4' OR SOORT = '2' OR SOORT = '0' OR  SOORT = '1')";

            pstmnt = connection.prepareStatement(sql);
            pstmnt.setString(1, a);


            rs = pstmnt.executeQuery();

            String text_of_existance = " ";
            if (rs.next() == false) {
                System.out.println("There is no record: "+ a +" in database");
                ArticleCostPriceObjAllSort obj = new ArticleCostPriceObjAllSort(a, "0","0","0", "0");

                listOfCosts.add(obj);

            } else {
                do {
                    String   MATERIAAL=   rs.getString("MATERIAAL");
                    String   LONEN=   rs.getString("LONEN");
                    String   CFKOSTPRIJS=   rs.getString("CFKOSTPRIJS");
                    String   Soort = rs.getString("SOORT");
                    ArticleCostPriceObjAllSort obj = new ArticleCostPriceObjAllSort(a, MATERIAAL,LONEN,CFKOSTPRIJS,Soort);
                    listOfCosts.add(obj);

                } while (rs.next());
            }

        }

        connection.close();
        rs.close();
        pstmnt.close();




    }
}
