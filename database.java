package com.example.noteworthy;

import java.sql.*;

public class database {
    PreparedStatement ps;
    Connection con;
    ResultSet result;

    database() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","hr");
        System.out.println("Connected");
    }

    void insert(String usrname, String psword) throws SQLException {
        ps=con.prepareStatement("Insert into NoteWorthy(name,password) values(?,?)");
        ps.setString(1,usrname);
        ps.setString(2,psword);
        ps.executeUpdate();
    }

    boolean search(String usrname, String psword) throws SQLException {
        ps=con.prepareStatement("select * from NoteWorthy where name=? AND password=?");
        ps.setString(1,usrname);
        ps.setString(2,psword);
        result = ps.executeQuery();
        if(result.next())
            return true;
        else
            return false;
    }


    void InsertTitle(String title) throws SQLException {
        ps=con.prepareStatement("Insert into NoteWorthyTitle(title) values(?)");
        ps.setString(1,title);
        ps.executeUpdate();

        String str="";
        ps=con.prepareStatement("Insert into noteworthyNotes values(?,?)");
        ps.setString(1,title);
        ps.setString(2,str);
        ps.executeUpdate();
    }

    void RemoveTitle(String ttl) throws SQLException {
        System.out.println(ttl);
        String deleteNotesQuery = "DELETE FROM noteworthyNotes WHERE title=?";
        String deleteTitleQuery = "DELETE FROM noteworthyTitle WHERE title=?";

        try (PreparedStatement deleteNotesStatement = con.prepareStatement(deleteNotesQuery);
             PreparedStatement deleteTitleStatement = con.prepareStatement(deleteTitleQuery)) {

            deleteNotesStatement.setString(1, ttl);
            deleteNotesStatement.executeUpdate();

            deleteTitleStatement.setString(1, ttl);
            deleteTitleStatement.executeUpdate();
            System.out.println(ttl + " Deleted successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    boolean InsertNote(String title, String desc) throws SQLException {
        String query = "UPDATE noteworthyNotes SET description = ? WHERE title = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, desc);
            statement.setString(2, title);
            int result = statement.executeUpdate();
            return result > 0;
        }
    }

    String display(String ttl) throws SQLException {
        String desc = "";
        String query = "SELECT description FROM noteworthyNotes WHERE title=?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, ttl);
            result = statement.executeQuery();
            if (result.next()) {
                desc = result.getString("description");
            }
        }
        return desc;
    }

    ResultSet DisplayTitle() throws  SQLException{
        try{
            ps=con.prepareStatement("Select * from noteworthytitle");
            result=ps.executeQuery();
        }catch (Exception e){
            System.out.println(e);
        }
        return result;
    }
}








//create table noteworthyTitle(
//        title varchar(100) not null primary key
//);
//
//        create table noteworthynotes(
//         title varchar(100) not null ,
//         description varchar(500) ,
//        foreign key (title) references noteworthytitle(title)
//         );
//
