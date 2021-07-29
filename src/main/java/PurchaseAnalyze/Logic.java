package PurchaseAnalyze;

import Bestelling500.Bestelling500Obj;
import DBConnectorFATDB.DBConnectorFATDB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Logic {

    private String beginDate; // "2020-01-01"
    private String endDate;   // "2021-01-01"

    public Logic(String begin, String end) {
        beginDate = begin;
        endDate   = end;
    }

    public void doAnalyze() throws SQLException, IOException {

        //0 List of purchase
        List<bestellingOrdersSummary> bestellingOrdersSummaries = new ArrayList<>();

        // 1.
        List<String> leverancierList = findLeveranciers(beginDate, endDate);

//        // 2. find summary for each of leverancier
        for(String s : leverancierList) {
            bestellingOrdersSummary summary = findSummary(s, beginDate, endDate);// index(0) <- 124057
            bestellingOrdersSummaries.add(summary);
            System.out.println(summary.toString());
        }

        //3 create excel file
        ExcelFIle e = new ExcelFIle("PurchaseAnalyze.xlsx" ,beginDate,endDate);
        e.CreateFile2(bestellingOrdersSummaries);

    }

    private bestellingOrdersSummary findSummary(String s, String beginDate, String endDate) throws SQLException {

        bestellingOrdersSummary bestellingOrdersSummary = new bestellingOrdersSummary();

        Connection connection = DBConnectorFATDB.dbConnector();
        ResultSet rs = null;
        PreparedStatement pstmnt= null;


        String sql = "select concat(b.leverancier,' ', l2.NAAM) as leverancier ,\n" +
                "(select count(*) from  bestelling b \n" +
                "where leverancier  = ?\n" +
                "and b.LEVERDATUM between ? and ? ) as OrdernummerCount,  b.MUNT , sum(b2.SUMA) as sumAllPricesFromOrder from bestelling b\n" +
                "join bestellingdetail b2 \n" +
                "on b.leverancier = b2.leverancier\n" +
                "and b.ORDERNUMMER  = b2.ORDERNUMMER \n" +
                "join leverancier l2 \n" +
                "on b.leverancier  = l2.LEVERANCIERNR \n" +
                "where b.leverancier  = ?\n" +
                "and b.LEVERDATUM between ? and ? ";

        pstmnt = connection.prepareStatement(sql);
        pstmnt.setString(1, s);
        pstmnt.setString(2, beginDate);
        pstmnt.setString(3, endDate);
        pstmnt.setString(4, s);
        pstmnt.setString(5, beginDate);
        pstmnt.setString(6, endDate);

        rs = pstmnt.executeQuery();

        if (rs.next() == false) {
            System.out.println("There is no record: "+ beginDate +" in database");

        } else {
            do {
                String leverancier= rs.getString("leverancier");
                int orderNummerCount = rs.getInt("OrdernummerCount");
                String munt = rs.getString("MUNT");
                double sumAllPrciesFromOrders = rs.getDouble("sumAllPricesFromOrder");

                bestellingOrdersSummary.setLeverancier(leverancier);
                bestellingOrdersSummary.setOrderNummerCount(orderNummerCount);
                bestellingOrdersSummary.setMunt(munt);
                bestellingOrdersSummary.setSumPricesOfAllOrders(sumAllPrciesFromOrders);

            } while (rs.next());
        }

        rs.close();
        pstmnt.close();

        // find data for each of orders for particular Supplier

        List<bestellingOrders> bestellingOrders = new ArrayList<>();

        ResultSet rs2 = null;
        PreparedStatement pstmnt2 = null;


        String sql2 = "select concat(b.leverancier ,'/', b.ORDERNUMMER) as leverancier , sum(b.SUMA) as cashSUmOfOrder from bestellingdetail b \n" +
                "join bestelling b2  \n" +
                "on b.leverancier  = b2.leverancier \n" +
                "and b.ORDERNUMMER  = b2.ORDERNUMMER \n" +
                "where b.leverancier  = ? \n" +
                "and b2.LEVERDATUM between ? and ?\n" +
                "group by b.ORDERNUMMER ";

        pstmnt2 = connection.prepareStatement(sql2);
        pstmnt2.setString(1, s);
        pstmnt2.setString(2, beginDate);
        pstmnt2.setString(3, endDate);

        rs2 = pstmnt2.executeQuery();

        if (rs2.next() == false) {
            System.out.println("There is no record: "+ beginDate +" in database");

        } else {
            do {
                String leverancier= rs2.getString("leverancier");
                double orderNummerCount = rs2.getDouble("cashSUmOfOrder");

                bestellingOrders obj = new bestellingOrders(leverancier, orderNummerCount);
                bestellingOrders.add(obj);

            } while (rs2.next());
        }

        bestellingOrdersSummary.setListOfOrders(bestellingOrders);

        rs2.close();
        pstmnt2.close();



        return bestellingOrdersSummary;
    }


    private List<String> findLeveranciers(String beginDate, String endDate) throws SQLException {

        List<String> leverancierList = new ArrayList<>();

        Connection connection = DBConnectorFATDB.dbConnector();
        ResultSet rs = null;
        PreparedStatement pstmnt= null;


            String sql = "select leverancier from bestelling b \n" +
                    "where b.LEVERDATUM between ? and ? \n" +
                    "and leverancier  > 999\n" +
                    "group by leverancier \n" +
                    "order by leverancier ";

            pstmnt = connection.prepareStatement(sql);
            pstmnt.setString(1, beginDate);
            pstmnt.setString(2, endDate);

            rs = pstmnt.executeQuery();

            String text_of_existance = " ";
            if (rs.next() == false) {
                System.out.println("There is no record: "+ beginDate +" in database");
                text_of_existance = "There is no record: "+ endDate +" in database";

            } else {
                do {
                    String leverancier= rs.getString("leverancier");
                    leverancierList.add(leverancier);
                } while (rs.next());
            }



        rs.close();
        pstmnt.close();




        return leverancierList;
    }


}
