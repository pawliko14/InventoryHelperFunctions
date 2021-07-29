package ArticleStock;


import DBConnectorFATDB.DBConnectorFATDB;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class RetriveArticleStock {

    Map<String, String> ArticlecodeVerschaffingscode;
    private List<String> ArticlesThatDontExistInFATDB;

    public RetriveArticleStock(){
        ArticlecodeVerschaffingscode = new HashMap<>();
        ArticlesThatDontExistInFATDB = new ArrayList<>();

    }

    public List<String> getArticlesThatDontExistInFATDB() {

        if(this.ArticlesThatDontExistInFATDB.size() == 0)
        {
            System.out.println("there is no Article that dont exists");
            return new ArrayList<>();
        }
        return ArticlesThatDontExistInFATDB;
    }

    public Map<String, String> getArticlecodeVerschaffingscode() {
        return ArticlecodeVerschaffingscode;
    }

    public Map<String,String> getOnlyByTyperticles(VerschaffingsCode type) throws Exception {
        if(ArticlecodeVerschaffingscode == null || ArticlecodeVerschaffingscode.isEmpty() )
        {
            throw new Exception("List of Articles is empty");
        }
        Map<String, String> filteredByType = new HashMap<>();
        boolean isPresent = false;

        Optional<Map.Entry<String, String>> any = ArticlecodeVerschaffingscode.entrySet()
                .stream()
                .filter(a -> a.getValue().equals(type.toString()))
                .findAny();

        if(any.isPresent()) {
            System.out.println("isPresent");
        }else {
            System.out.println("NOT present");
        }


        if(isPresent) {
           filteredByType =
                  ArticlecodeVerschaffingscode.entrySet()
                          .stream()
                          .filter(a -> a.getValue().equals(type.toString()))
                          .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

          if (filteredByType.isEmpty()) {
              System.out.println("Result of filtration looking for " + type + " is empty");
              return new HashMap<>();
          }
      }


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
               System.out.println("Articles, There is no record: "+ a +" in database");
               ArticlesThatDontExistInFATDB.add(a);
           } else {
               do {

                   ArticlecodeVerschaffingscode.put(rs.getString("ARTIKELCODE"),
                                                    rs.getString("VERSCHAFFINGSCODE")
                           );


               } while (rs.next());
           }

       }

        rs.close();
        pstmnt.close();


    }




}
