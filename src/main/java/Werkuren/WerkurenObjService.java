package Werkuren;

import DBConnectorFATDB.DBConnectorFATDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WerkurenObjService {

private List<WerkurenObj> werkurenObjetcs;

    public WerkurenObjService() {
        this.werkurenObjetcs =  new ArrayList<>();
    }

    public List<WerkurenObj> getWerkurenObjetcs() {
        return werkurenObjetcs;
    }


    public void getTimeDiffinMinute() throws Exception {

        for(int i = 0; i < werkurenObjetcs.size(); i++) {
            String hour = werkurenObjetcs.get(i).getBEGINTIJDH();
            String minutes = werkurenObjetcs.get(i).getBEGINTIJDM60();

            String hour_2 = werkurenObjetcs.get(i).getEINDTIJDH();
            String minutes_2 = werkurenObjetcs.get(i).getEINDTIJDM60();

            Date beginDate = this.getBeginDate(hour, minutes);
            Date endDate   = this.getEndDate(hour_2, minutes_2);

            long diffInMillies = Math.abs(beginDate.getTime() - endDate.getTime());

            int minutes_from_long = (int) ((diffInMillies / 1000) / 60);

            werkurenObjetcs.get(i).setTimeSpendInMinutes(minutes_from_long);
        }
    }


    public Date getBeginDate(String beginHour, String beginMinutes) throws Exception {

        return createTimeFromString(beginHour,beginMinutes);

    }

    public Date getEndDate(String beginHour, String beginMinutes) throws Exception {

        return createTimeFromString(beginHour,beginMinutes);

    }

    private Date createTimeFromString(String hours, String minutes) throws Exception {

        StringBuilder sb_hours = new StringBuilder(hours);
        StringBuilder sb_minutes = new StringBuilder(minutes);
        int parsed_hours  = Integer.parseInt(hours);

        if(parsed_hours > 24) {
            throw new Exception("parsed hours is over 24h format");
        }

        if(minutes.length() == 1)
        {
            sb_minutes.insert(0,"0");
        }
        if(hours.length() ==1)
        {
            sb_hours.insert(0,"0");

        }

        StringBuilder time = new StringBuilder();
        time.append(sb_hours);
        time.append(":");
        time.append(sb_minutes);

        DateFormat sdf = new SimpleDateFormat("HH:mm"); // or "hh:mm" for 12 hour format
        Date date = sdf.parse(String.valueOf(time));

        return date;
    }


    public void retriveWerkurenList() throws SQLException {
        Connection connection = DBConnectorFATDB.dbConnector();
        ResultSet rs = null;
        PreparedStatement pstmnt= null;

        try {

                String sql = "select WERKNEMER ,WERKBONNUMMER , DATUM , BEGINTIJDH , BEGINTIJDM60 , EINDTIJDH ,EINDTIJDM60 \n" +
                        "from werkuren w \n" +
                        "where STATUS  = ?";

                pstmnt = connection.prepareStatement(sql);
                pstmnt.setString(1, "20"); // not finished workbon


                rs = pstmnt.executeQuery();


                if (rs.next() == false) {
                    System.out.println("There is no record in database");
                } else {
                    do {

                        WerkurenObj ob = new WerkurenObj(
                                0,
                                rs.getString("WERKNEMER"),
                                rs.getString("WERKBONNUMMER"),
                                rs.getString("DATUM"),
                                rs.getString("BEGINTIJDH"),
                                rs.getString("BEGINTIJDM60"),
                                rs.getString("EINDTIJDH"),
                                rs.getString("EINDTIJDM60")
                        );

                        werkurenObjetcs.add(ob);

                    } while (rs.next());
                }
        }
        catch(Exception e)
        {
            System.out.println("Cannot Retrive from DB!");
        }
        finally {

            rs.close();
            pstmnt.close();
            connection.close();
        }

    }
}
