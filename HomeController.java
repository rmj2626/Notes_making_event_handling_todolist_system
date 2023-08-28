package com.example.noteworthy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Label usernamelabel;

    @FXML
    private Button Eventsbtn;

    @FXML
    private Button Notebtn;

    @FXML
    private Button ToDobtn;

    public void initialize() {
        String username = LoginSignUpController.getUsername();
        usernamelabel.setText("Welcome, " + username + "!");
    }
    @FXML
    void gotoEvents(ActionEvent event)throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Event.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 750);
        Stage stage = new Stage();
        stage.setTitle("Events");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void gotoNotes(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Note.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 750);
        Stage stage = new Stage();
        stage.setTitle("Notes");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void gotoToDo(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ToDo.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 750);
        Stage stage = new Stage();
        stage.setTitle("ToDo");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
