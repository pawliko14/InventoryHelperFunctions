package PurchaseAnalyzeBasedOnArticlesAndLeverancier;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DBConnectorFATDB.DBConnectorFATDB;


public class Logic {

    private String analyzedYear;  //2015
    private String leverancier; // 119003

    public Logic(String analyzedYear,  String lev) {
        this.analyzedYear = analyzedYear;
        this.leverancier = lev;
    }

    public List<BestellingObject> doAnalyze() throws SQLException {

        // 1. fetch all articles
        List<Artikel> articleList = fetchArticles(leverancier);

        // testing purpose, remove all except first 10 rows
     //   articleList.subList(10,articleList.size()).clear();

        System.out.print("articles fetched");

        // 2. for each of articles find how many were ordered and delivered in particular year
        List<BestellingObject> bestellingObjects = new ArrayList<>();


        Connection connection = DBConnectorFATDB.dbConnector();
        for(int i = 0 ; i < articleList.size(); i++) {
            BestellingObject obj = fetchDataForObject(articleList.get(i), analyzedYear, leverancier,connection);
            bestellingObjects.add(obj);

        }

        // 3. print out
  //      bestellingObjects.forEach(System.out::println);


        return bestellingObjects;
    }

    private BestellingObject fetchDataForObject(Artikel s, String analyzedYear, String leverancier, Connection connection) throws SQLException {

        BestellingObject obj = new BestellingObject();

        if(s.getArtikel().equals("100-010-0201G")){
            boolean debug = true;
        }

        ResultSet rs = null;
        PreparedStatement pstmnt= null;


        // CAREFUL! here might be a problem with wildcard, check during debug!
        String sql = "select leverancier , ARTIKELCODE , ARTIKELOMSCHRIJVING , sum(BESTELD) as besteld ,sum(GELEVERD) as gelerved, sum(SUMA) as totalPrice, eenheidsprijs as priceItem, MUNT , LEVERINGSDATUMINGAVERECEPTIE\n" +
                "from bestellingdetail b \n" +
                "where leverancier  = ? \n" +
                "and LEVERINGSDATUMINGAVERECEPTIE  like ? \n" +
                "and ARTIKELCODE   = ?";

        pstmnt = connection.prepareStatement(sql);
        pstmnt.setString(1, leverancier);
        pstmnt.setString(2, analyzedYear + "%");
        pstmnt.setString(3, s.getArtikel());

        rs = pstmnt.executeQuery();

        if (rs.next() == false ) {
            System.out.println("There is no record:  in database, set Zeros for the object");
                obj.setLeverancier(leverancier);
                obj.setArticelcode(s.getArtikel());
                obj.setArtikelomschirjing(s.getArtikelOmschrijving());
                obj.setBesteld(0);
                obj.setGelerved(0);
                obj.setPricePerItem(0);
                obj.setTotal(BigDecimal.valueOf(0));
                obj.setLeveringsdatumReceptie("-");
                obj.setMunt("-");

            rs.close();
            pstmnt.close();

            return obj;

        } else {
            do {

                String  leverancierSQL = rs.getString("leverancier");
                String artikelcodeSQL = rs.getString("ARTIKELCODE");
                String artikelomschrijvingSQL = rs.getString("ARTIKELOMSCHRIJVING");
                double besteldSQL = rs.getDouble("besteld");
                double gelervedSQL = rs.getDouble("gelerved");
                double pricePerItemSQL = rs.getDouble("priceItem");
                BigDecimal totalSQL = rs.getBigDecimal("totalPrice");
                String leveringsdatumReceptieSQL = rs.getString("LEVERINGSDATUMINGAVERECEPTIE");
                String muntSQL = rs.getString("MUNT");


                if(rs.wasNull()){
                    obj.setLeverancier(leverancier);
                    obj.setArticelcode(s.getArtikel());
                    obj.setArtikelomschirjing(s.getArtikelOmschrijving());
                    obj.setBesteld(0);
                    obj.setGelerved(0);
                    obj.setPricePerItem(0);
                    obj.setTotal(BigDecimal.valueOf(0));
                    obj.setLeveringsdatumReceptie("-");
                    obj.setMunt("-");
                }
                else {
                    obj.setLeverancier(leverancierSQL);
                    obj.setArticelcode(artikelcodeSQL);
                    obj.setArtikelomschirjing(artikelomschrijvingSQL);
                    obj.setBesteld(besteldSQL);
                    obj.setGelerved(gelervedSQL);
                    obj.setPricePerItem(pricePerItemSQL);
                    obj.setTotal(totalSQL);
                    obj.setLeveringsdatumReceptie(leveringsdatumReceptieSQL);
                    obj.setMunt(muntSQL);
                }
            } while (rs.next());
        }

        rs.close();
        pstmnt.close();




        return obj;
    }


    /**
     *  initial filtration, dont need ALL articles that exist, need only those ones, that occur in order
     *  for particular supplier, this will shrink whole list few times, especially when particular time is set
     *  in this case it is taken:
     *  since current date - 6 years till today
     *
     * @param leverancier
     * @return
     * @throws SQLException
     */
    private List<Artikel> fetchArticles( String leverancier) throws SQLException {

        List<Artikel> artikelCodeList = new ArrayList<>();

        Connection connection = DBConnectorFATDB.dbConnector();
        ResultSet rs = null;
        PreparedStatement pstmnt= null;


        String sql = "select ARTIKELCODE, ARTIKELOMSCHRIJVING  from bestellingdetail b \n" +
                "where leverancier  = ? \n" +
                "and LEVERINGSDATUMVOORZIEN  <  current_date() \n" +
                "and LEVERINGSDATUMVOORZIEN  > current_date() - INTERVAL 365*6 day\n" +
                "group by ARTIKELCODE ";

        pstmnt = connection.prepareStatement(sql);
        pstmnt.setString(1, leverancier);

        rs = pstmnt.executeQuery();

        if (rs.next() == false) {
            System.out.println("There is no record: "+ analyzedYear +" in database");

        } else {
            do {
                String  artikelcode = rs.getString("ARTIKELCODE");
                String  artikelcodeOmschrijving = rs.getString("ARTIKELOMSCHRIJVING");

                Artikel art = new Artikel();
                   art.setArtikel(artikelcode);
                   art.setArtikelOmschrijving(artikelcodeOmschrijving);

                artikelCodeList.add(art);

            } while (rs.next());
        }



        rs.close();
        pstmnt.close();

    return artikelCodeList;
    }

}
