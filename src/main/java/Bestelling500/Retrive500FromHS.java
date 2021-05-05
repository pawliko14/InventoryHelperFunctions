package Bestelling500;

import DBConnectorFATDB.DBConnectorFATDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Retrive500FromHS {

    private List<Bestelling500Obj> listof500;

    public Retrive500FromHS() {
        this.listof500 =  new ArrayList<>();
    }

    public List<Bestelling500Obj> getListof500() {
        return listof500;
    }

    public void retriveArticleType(List<String> articles) throws SQLException {
        Connection connection = DBConnectorFATDB.dbConnector();
        ResultSet rs = null;
        PreparedStatement pstmnt= null;

        for(String a : articles ) {
            String sql = "select leverancier ,ORDERNUMMER ,ARTIKELCODE ,ARTIKELOMSCHRIJVING  , BESTELD ,GELEVERD from bestellingdetail b2b \n" +
                    "where ORDERNUMMER  = ? and leverancier = '500'";

            pstmnt = connection.prepareStatement(sql);
            pstmnt.setString(1, a);


            rs = pstmnt.executeQuery();

            String text_of_existance = " ";
            if (rs.next() == false) {
                System.out.println("There is no record: "+ a +" in database");
                text_of_existance = "There is no record: "+ a +" in database";

                Bestelling500Obj obj = new Bestelling500Obj("-",a,"-","-",text_of_existance, "-", "-");


                listof500.add(obj);

            } else {
                do {
                String leverancier=        rs.getString("leverancier");
                  String   ORDERNUMMER=    rs.getString("ORDERNUMMER");
                    String ARTIKELCODE =    rs.getString("ARTIKELCODE");
                      String ARTIKELOMSCHRIJVING =  rs.getString("ARTIKELOMSCHRIJVING");
                      String BESTELD =  rs.getString("BESTELD");
                       String GELEVERD = rs.getString("GELEVERD");

                       Bestelling500Obj obj = new Bestelling500Obj(leverancier,ORDERNUMMER,ARTIKELCODE,ARTIKELOMSCHRIJVING,text_of_existance, BESTELD, GELEVERD);

                    listof500.add(obj);


                } while (rs.next());
            }

        }

        rs.close();;
        pstmnt.close();


    }

}
