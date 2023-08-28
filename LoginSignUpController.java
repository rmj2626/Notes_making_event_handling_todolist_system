package com.example.noteworthy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginSignUpController {
    private static String Username;
    @FXML
    private Button CreateAccbtn;

    @FXML
    private AnchorPane LoginAnchorpane;
    @FXML
    private AnchorPane CreateAccPane;

    @FXML
    private Button back;

    @FXML
    private Button logInbutton;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private PasswordField passwordfield1;

    @FXML
    private Label passwordlabel;

    @FXML
    private Label passwordlabel1;

    @FXML
    private Button signInbutton1;

    @FXML
    private Label signInlabel;

    @FXML
    private Label successfulLabel;

    @FXML
    private TextField usernamefield;

    @FXML
    private TextField usernamefield1;

    @FXML
    private Label usernamelabel;

    @FXML
    private Label usernamelabel1;


    @FXML
    void goback(ActionEvent event) {
        LoginAnchorpane.setVisible(true);
        CreateAccPane.setVisible(false);
    }

    @FXML
    public void login(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        boolean match = false;
        String uname = "";
        String psword = "";

        String username = usernamefield.getText();
        Username=username;
        String password = passwordfield.getText();
        try {
            database con = new database();
            if (username.isEmpty() || password.isEmpty()) {
                signInlabel.setText("Please enter username and password");
            } else {
                if (con.search(username, password)) {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Home.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    stage.setTitle("Home");
                    stage.setScene(scene);
                    stage.show();

                    Stage window = (Stage) logInbutton.getScene().getWindow();
                    window.close();
                } else {
                    signInlabel.setText("              Invalid Credentials!");
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    void signin(ActionEvent event) throws IOException {
        String username = usernamefield1.getText();
        String password = passwordfield1.getText();
        try {
            database con = new database();
            con.insert(username, password);
            successfulLabel.setText("Account Created!");

//            navigateToNoteScene();
        } catch (Exception e) {
            System.out.println(e);
        }
    }




    @FXML
    void signup(ActionEvent event) throws IOException {
        LoginAnchorpane.setVisible(false);
        CreateAccPane.setVisible(true);
        String username = usernamefield1.getText();
        String password = passwordfield1.getText();
        try {
            database con = new database();
            con.insert(username, password);
            successfulLabel.setText("Account Created!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getUsername() {
        return Username;
    }
}
