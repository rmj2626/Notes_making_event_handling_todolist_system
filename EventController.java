package com.example.noteworthy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private AnchorPane anchorPane1;

    @FXML
    private AnchorPane anchorPane2;

    @FXML
    private AnchorPane anchorPane3;

    @FXML
    private Button clearButton;

    @FXML
    private TextField urlField;

    @FXML
    private TextField urlField1;

    @FXML
    private TextField dateField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea descField;

    @FXML
    private TextArea eventDesc;

    @FXML
    private ListView<String> eventList;

    @FXML
    private Label holderLabel;

    @FXML
    private Button homeButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField titleField;

    @FXML
    private Button viewButton;


    String title, desc, date, url;
    LocalDate myDate;

    public void getEvent(ActionEvent event) throws SQLException, ClassNotFoundException {
        title = titleField.getText();
        myDate = datePicker.getValue();
        date = myDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy") );
        desc = descField.getText();
        url = urlField.getText();
        EventDatabase con = new EventDatabase();
        con.insert(title,date,desc,url);
        addToList();
        descField.clear();
        titleField.clear();
        datePicker.setValue(null);
        urlField.clear();
    }

    public void addToList(){

        eventList.getItems().add(title);
    }

    public void removeFromList(ActionEvent event) throws SQLException, ClassNotFoundException {
        int selectedID = eventList.getSelectionModel().getSelectedIndex();
        EventDatabase con = new EventDatabase();
        title = (String) eventList.getSelectionModel().getSelectedItem();
        con.deleteEvent(title);
        eventList.getItems().remove(selectedID);
        titleField.setText("");
        descField.setText("");
        dateField.setText("");
        eventDesc.setText("");
        holderLabel.setText("");
        urlField.setText("");
        urlField1.setText("");
    }


    public void viewEvent(ActionEvent event) throws SQLException, ClassNotFoundException {
        int selectedID = eventList.getSelectionModel().getSelectedIndex();
        title = (String) eventList.getItems().get(selectedID);
        holderLabel.setText(title);
        EventDatabase con = new EventDatabase();
        String mydate = con.viewDate(title);
        dateField.setText(mydate);
        String desc = con.viewDesc(title);
        eventDesc.setText(desc);
        String url1 = con.viewURL(title);
        urlField1.setText(url1);

    }


    @FXML
    void ExitEvents(ActionEvent event) {
        Stage window = (Stage) homeButton.getScene().getWindow();
        window.close();
    }


    public void initialize() throws Exception {
        retrieveDataFromDatabase();
    }


    private void retrieveDataFromDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","hr")){
            ObservableList<String> todoItems = FXCollections.observableArrayList();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM noteworthyevent");
            while (resultSet.next()) {
                String item = resultSet.getString("eventtitle");
                todoItems.add(item);
            }
            eventList.setItems(todoItems);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
