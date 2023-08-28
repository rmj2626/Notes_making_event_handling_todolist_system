package com.example.noteworthy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ToDoDatabase {
    PreparedStatement ps;
    Connection con;
    ResultSet result;

    ToDoDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","hr");
        System.out.println("Connected");
    }

    void insertTask(String task) throws SQLException {
        ps=con.prepareStatement("Insert into NoteWorthyToDo(task) values(?)");
        ps.setString(1,task);
        ps.executeUpdate();
        System.out.println("Added Successfully");
    }


    public void RemoveTask(String task) throws SQLException {
        ps=con.prepareStatement("DELETE FROM noteworthyToDo where task=?");
        ps.setString(1,task);
        ps.executeUpdate();
        System.out.println("Deleted successfully");
    }

    public void Completed(String task) throws SQLException{
        String table1Query="Delete from noteworthyTodo where task=?";
        String table2Query="Insert into noteworthyTodoDone values(?)";
        try (PreparedStatement ps1 = con.prepareStatement(table1Query);
             PreparedStatement ps2 = con.prepareStatement(table2Query)) {

            ps2.setString(1, task);
            ps2.executeUpdate();

            ps1.setString(1, task);
            ps1.executeUpdate();
            System.out.println(task + " Marked successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public  void Clear() throws SQLException{
        ps=con.prepareStatement("Truncate table noteworthyToDoDone");
        ps.executeUpdate();
    }


}



/*
create table noteworthyToDo(
        task varchar(20) primary key
);

crete table noteworthyToDoDone(
tasksDone varchar(20)
);

*/
