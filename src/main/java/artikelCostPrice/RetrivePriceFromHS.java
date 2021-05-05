package artikelCostPrice;

import Bestelling500.Bestelling500Obj;
import DBConnectorFATDB.DBConnectorFATDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetrivePriceFromHS {

    private List<ArtikelCostPriceObj> listOfCosts;

    public RetrivePriceFromHS() {
        this.listOfCosts = new ArrayList<>();
    }

    public List<ArtikelCostPriceObj> getListOfCosts() {
        return  listOfCosts;
    }


    public void retrivePricesFromHS(List<String> articles) throws SQLException {
        Connection connection = DBConnectorFATDB.dbConnector();
        ResultSet rs = null;
        PreparedStatement pstmnt= null;


        for(String a : articles ) {
            String sql = "select MATERIAAL ,LONEN ,CFKOSTPRIJS from artikel_kostprijs ak \n" +
                    "where  ak.ARTIKELCODE  =  ? \n" +
                    "and SOORT  = '4'";

            pstmnt = connection.prepareStatement(sql);
            pstmnt.setString(1, a);


            rs = pstmnt.executeQuery();

            String text_of_existance = " ";
            if (rs.next() == false) {
                System.out.println("There is no record: "+ a +" in database");
                ArtikelCostPriceObj obj = new ArtikelCostPriceObj(a, "0","0","0");

                listOfCosts.add(obj);

            } else {
                do {
                    String   MATERIAAL=   rs.getString("MATERIAAL");
                    String   LONEN=   rs.getString("LONEN");
                    String   CFKOSTPRIJS=   rs.getString("CFKOSTPRIJS");
                    ArtikelCostPriceObj obj = new ArtikelCostPriceObj(a, MATERIAAL,LONEN,CFKOSTPRIJS);
                    listOfCosts.add(obj);

                } while (rs.next());
            }

        }

        rs.close();;
        pstmnt.close();




    }

}
