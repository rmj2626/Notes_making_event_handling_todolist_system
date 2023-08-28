package com.example.noteworthy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.*;


public class ToDoController {

    @FXML
    private Button HomeBtn;

    @FXML
    private Button addTaskButton;

    @FXML
    private Button completeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField descriptionText;

    @FXML
    private Label errorLabel;

    @FXML
    private SplitPane mainPane;

    @FXML
    private ListView<String> taskList;

    @FXML
    private ListView<String> taskListDone;

    @FXML
    private CheckBox urgentCheckbox;

    @FXML
    void addNewTask(ActionEvent event) {
        if(addTaskValidate())
        {
            String desc= descriptionText.getText();
            if(urgentCheckbox.isSelected())
            {
                desc= "* "+ desc;
            }
            taskList.getItems().add(desc);
            errorLabel.setText("");
//            descriptionText.clear();
            try {
                ToDoDatabase con=new ToDoDatabase();
                String task=descriptionText.getText();
                con.insertTask(task);
                descriptionText.clear();
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e);
            }

            urgentCheckbox.setSelected(false);
        }
    }


    private boolean addTaskValidate()
    {
        if( descriptionText.getText().equals("") )
        {
            errorLabel.setText("Cannot Create Empty task");
            return false;
        }
        return true;
    }


    @FXML
    void deleteTask(ActionEvent event) {
        int selectedId=taskList.getSelectionModel().getSelectedIndex();
        try{
            if(selectedId<=taskList.getItems().size())
            {
                ToDoDatabase con=new ToDoDatabase();
                String task=taskList.getItems().get(selectedId);
                con.RemoveTask(task);       //my user defined function , performing delete operation
            }
        }catch (Exception e) {
            System.out.println(e);
        }

        taskList.getItems().remove(selectedId);
        taskList.refresh();
        taskListDone.refresh();
    }


    @FXML
    void markAsComplete(ActionEvent event) throws RuntimeException {
        int index = 0;
        if(taskList.isFocused())
        {
            index=taskList.getSelectionModel().getSelectedIndex();
        }
        String task=  taskList.getItems().get(index);
        try{
            ToDoDatabase con= new ToDoDatabase();
            con.Completed(task);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        taskList.getItems().remove(index);
        taskListDone.getItems().add(task);
    }

    @FXML
    void clearTask(ActionEvent event) {
        taskListDone.getItems().clear();
        try {
            ToDoDatabase con=new ToDoDatabase();
            con.Clear();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void gotoHome(ActionEvent event) {
        Stage window = (Stage) HomeBtn.getScene().getWindow();
        window.close();
    }


    public void initialize() throws Exception {
            retrieveDataFromDatabase();
    }


    private void retrieveDataFromDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","hr")){
            ObservableList<String> todoItems = FXCollections.observableArrayList();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM noteworthytodo");
            while (resultSet.next()) {
                String item = resultSet.getString("task");
                todoItems.add(item);
            }
            taskList.setItems(todoItems);

            ObservableList<String> doneItems = FXCollections.observableArrayList();
            ResultSet doneResultSet = statement.executeQuery("SELECT * FROM noteworthytodoDone");
            while (doneResultSet.next()) {
                String item = doneResultSet.getString("taskdone");
                doneItems.add(item);
            }
            taskListDone.setItems(doneItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
