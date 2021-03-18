package Test_to_remove;


import DBConnectorFATDB.DBConnectorFATDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RetriveArticleStock {

    Map<String, String> ArticlecodeVerschaffingscode;

    public RetriveArticleStock(){
        ArticlecodeVerschaffingscode = new HashMap<>();

    }

    public Map<String, String> getArticlecodeVerschaffingscode() {
        return ArticlecodeVerschaffingscode;
    }

    public Map<String,String> getOnlyByTyperticles(String type) throws Exception {
        if(ArticlecodeVerschaffingscode == null || ArticlecodeVerschaffingscode.isEmpty())
        {
            throw new Exception("List of Articles is empty");
        }

        Map<String,String> filteredByType =
                      ArticlecodeVerschaffingscode.entrySet()
                        .stream()
                        .filter(a->a.getValue().equals(type))
                        .collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));

        return filteredByType;

    }

    public void retriveArticleType(List<String> articles) throws SQLException {
        Connection connection = DBConnectorFATDB.dbConnector();
        ResultSet rs = null;
        PreparedStatement pstmnt= null;

       for(String a : articles ) {
           String sql = "select ARTIKELCODE ,  VERSCHAFFINGSCODE from artikel_algemeen aa \n" +
                   "where ARTIKELCODE  = ?";

           pstmnt = connection.prepareStatement(sql);
           pstmnt.setString(1, a);


            rs = pstmnt.executeQuery();


           if (rs.next() == false) {
               System.out.println("There is no record"+ a +" in database");
           } else {
               do {

                   ArticlecodeVerschaffingscode.put(rs.getString("ARTIKELCODE"),
                                                    rs.getString("VERSCHAFFINGSCODE")
                           );


               } while (rs.next());
           }

       }

        rs.close();;
        pstmnt.close();


    }


}
