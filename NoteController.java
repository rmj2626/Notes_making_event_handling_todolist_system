package com.example.noteworthy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteController {

    @FXML
    private AnchorPane addtitle;

    @FXML
    private Button savebtn;

    @FXML
    private TextArea description;

    @FXML
    private TextField title;
    @FXML
    private AnchorPane anchor1;

    @FXML
    private Button Exitbtn;
    @FXML
    private ListView<String> listOfTitle;

    @FXML
    private TextField titleName;

    @FXML
    private Label successfulLabel;

    @FXML
    private Label userlabel;

    @FXML
    private void initialize() throws Exception {
        // Set up listener for selection changes
        MultipleSelectionModel<String> selectionModel = listOfTitle.getSelectionModel();
        displayTitle();
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                title.setText(newValue);
                try {
                    database con = new database();
                    String desc = con.display(newValue);
                    description.setText(desc);
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void displayTitle() throws Exception{
        try{
            database con=new database();
            ResultSet resultSet=null;
            resultSet=con.DisplayTitle();
            while(resultSet.next())
            {
                listOfTitle.getItems().add(resultSet.getString("TITLE"));
            }
        }catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @FXML
    void addtitle(MouseEvent event) {
        if(!titleName.getText().isEmpty()){
            listOfTitle.getItems().add(titleName.getText());
            title.setText(titleName.getText());
            description.setText("");
            try {
                database con=new database();
                String ttl=titleName.getText();
                con.InsertTitle(ttl);
                titleName.clear();
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    void removetitle(MouseEvent event) {
    int selectedId=listOfTitle.getSelectionModel().getSelectedIndex();

    try{
        if(selectedId<=listOfTitle.getItems().size())
        {
        database con=new database();
        String ttl=listOfTitle.getItems().get(selectedId);
        con.RemoveTitle(ttl);       //my user defined function , performing delete operation
        }
    }catch (Exception e) {
        System.out.println(e);
    }
        listOfTitle.getItems().remove(selectedId);
        title.setText("");
        description.setText("");
    }

    @FXML
    void saveNote(ActionEvent event) {
        int index = listOfTitle.getSelectionModel().getSelectedIndex();
        if (index != -1) { // Check if an item is selected
            String ttl = listOfTitle.getItems().get(index);
            String desc = description.getText();
            System.out.println("Title is: "+ttl+"Description is: "+desc);

            try {
                database con = new database();
                boolean inserted = con.InsertNote(ttl, desc);
                successfulLabel.setText("Note saved successfully.");
               if(!inserted)
                    successfulLabel.setText("Failed to save note.");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            successfulLabel.setText("No note selected."); // Inform the user if no item is selected
        }
    }



    @FXML
    void ExitNotes(ActionEvent event) {
            Stage window = (Stage) Exitbtn.getScene().getWindow();
            window.close();
    }

    }


