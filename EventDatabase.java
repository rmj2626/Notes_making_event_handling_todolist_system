package com.example.noteworthy;

import java.sql.*;

public class EventDatabase {
    PreparedStatement statement;
    Connection con;
    ResultSet resultSet;

    EventDatabase() throws ClassNotFoundException, SQLException{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","hr");
    }

    void insert(String eTitle,String eDate, String eDesc, String eURL) throws SQLException {
        statement=con.prepareStatement("insert into noteworthyevent(eventTitle,eventDate,eventDesc,eventURL) values(?,?,?,?)");
        statement.setString(1,eTitle);
        statement.setString(2,eDate);
        statement.setString(3,eDesc);
        statement.setString(4,eURL);
        statement.executeUpdate();
        System.out.println("Inserted successfully");
    }

    void deleteEvent(String title) throws SQLException{
        statement=con.prepareStatement("delete from noteworthyevent where eventTitle=?");
        statement.setString(1,title);
        statement.executeUpdate();
        System.out.println("Deleted  successfully");
    }

    String viewDate(String str) throws SQLException{
        statement = con.prepareStatement("select eventDate from noteworthyevent where eventTitle=?");
        statement.setString(1,str);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            str = resultSet.getString("eventDate");
        }
        return str;
    }

    String viewDesc(String str) throws SQLException{
        statement = con.prepareStatement("select eventDesc from noteworthyevent where eventTitle=?");
        statement.setString(1,str);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            str = resultSet.getString("eventDesc");
        }
        return str;
    }


    public String viewURL(String str) throws SQLException {
        statement = con.prepareStatement("select eventURL from noteworthyevent where eventTitle=?");
        statement.setString(1,str);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            str = resultSet.getString("eventURL");
        }
        return str;
    }
}

/*
create table noteworthyevent(
eventTitle varchar(20) primary key,
eventDate varchar(10) ,
eventDesc varchar(50)
);

*/